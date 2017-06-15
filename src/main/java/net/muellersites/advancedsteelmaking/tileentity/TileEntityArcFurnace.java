package net.muellersites.advancedsteelmaking.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import net.muellersites.advancedsteelmaking.api.ArcFurnaceRecipe;
import net.muellersites.advancedsteelmaking.block.base.BlockInterfaces;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.reference.GuiIds;
import net.muellersites.advancedsteelmaking.util.ASInventoryHandler;
import net.muellersites.advancedsteelmaking.util.IASInventory;
import net.muellersites.advancedsteelmaking.util.LogHelper;
import net.muellersites.advancedsteelmaking.util.Utils;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class TileEntityArcFurnace extends TileEntityBase implements IASInventory, BlockInterfaces.IGuiTile, BlockInterfaces.IDirectionalTile, BlockInterfaces.IHasObjProperty, BlockInterfaces.ITileDrop, BlockInterfaces.IProcessTile {

    ItemStack[] inventory = new ItemStack[6];
    public String customName = "arc_furnace";
    public EnumFacing facing = EnumFacing.NORTH;
    public int process = 0;
    public int processMax = 0;
    public boolean active = false;

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket) {
        process = nbt.getInteger("process");
        processMax = nbt.getInteger("processMax");
        active = nbt.getBoolean("active");
        facing = EnumFacing.getFront(nbt.getInteger("facing"));

        if(!descPacket) {
            inventory = Utils.readInventory(nbt.getTagList("inventory", 10), 4);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket) {
        nbt.setInteger("process", process);
        nbt.setInteger("processMax", processMax);
        nbt.setBoolean("active", active);
        nbt.setInteger("facing", facing.ordinal());

        if(!descPacket) {
            nbt.setTag("inventory", Utils.writeInventory(inventory));
        }
    }

    @Override
    public String getCustomName() {
        return super.getCustomName();
    }

    @Override
    public void setCustomName(String customName) {
        super.setCustomName(customName);
    }

    @Override
    @Nullable
    public ITextComponent getDisplayName() {
        return new TextComponentString(customName);
    }

    @Override
    public boolean canOpenGui() {
        return true;
    }

    @Override
    public int getGuiID() {
        return GuiIds.ARC_FURNACE;
    }

    @Override
    public TileEntity getGuiMaster() {
        return this;
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public boolean isStackValid(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot > 2)
            return 64;
        else
            return 1;
    }

    @Override
    public void doGraphicalUpdates(int slot) {
        this.markDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    IItemHandler insertionHandler = new ASInventoryHandler(5,this);
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T)insertionHandler;
        return super.getCapability(capability, facing);
    }

    @Override
    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    public int getFacingLimitation() {
        return 2;
    }

    @Override
    public boolean mirrorFacingOnPlacement(EntityLivingBase placer) {
        return false;
    }

    @Override
    public boolean canHammerRotate(EnumFacing side, float hitX, float hitY, float hitZ, EntityLivingBase entity) {
        return false;
    }

    @Override
    public boolean canRotate(EnumFacing axis) {
        return false;
    }

    private static ArrayList<String> emptyDisplayList = new ArrayList();
    @Override
    public ArrayList<String> compileDisplayList() {
        return emptyDisplayList;
    }

    public void writeInv(NBTTagCompound nbt, boolean toItem) {
        boolean write = false;
        NBTTagList invList = new NBTTagList();
        for(int i=0; i<this.inventory.length; i++)
            if(this.inventory[i] != null)
            {
                if(toItem)
                    write = true;
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(itemTag);
                invList.appendTag(itemTag);
            }
        if(!toItem || write)
            nbt.setTag("inventory", invList);
    }

    @Override
    public ItemStack getTileDrop(EntityPlayer player, IBlockState state) {
        ItemStack stack = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
        NBTTagCompound tag = new NBTTagCompound();
        writeInv(tag, true);
        if(!tag.hasNoTags())
            stack.setTagCompound(tag);
        if(this.customName!=null)
            stack.setStackDisplayName(this.customName);
        return stack;
    }

    @Override
    public void readOnPlacement(EntityLivingBase placer, ItemStack stack) {
        if(stack.hasTagCompound())
        {
            readCustomNBT(stack.getTagCompound(), false);
            if(stack.hasDisplayName())
                this.customName = stack.getDisplayName();
        }
    }

    @Override
    public void update() {
        super.update();

        if(!worldObj.isRemote) {
            boolean a = active;
            boolean b = false;
            if(process>0) {
                // Check if input, additive and electrodes aren't empty
                LogHelper.info("Arc Process ongoing");
                if(inventory[3]==null || inventory[4]==null && !hasElectrodes()) {
                    process=0;
                    processMax=0;
                } else {
                    // Check if input correlates to a recipe
                    ArcFurnaceRecipe recipe = getRecipe();
                    if(recipe==null || recipe.time!=processMax) {
                        process=0;
                        processMax=0;
                        active=false;
                    } else
                        process--;
                }
                this.markContainingBlockForUpdate(null);
            } else {
                if(active) {
                    LogHelper.info("Arc furnace active, trying to do stuff to inv according to recipe");
                    ArcFurnaceRecipe recipe = getRecipe();
                    if(recipe!=null) {
                        LogHelper.info("Woo, stuffs ready!");
                        Utils.modifyInvStackSize(inventory, 3, -1);
                        Utils.modifyInvStackSize(inventory, 4, -1);
                        if(inventory[5]!=null)
                            inventory[5].stackSize+=recipe.output.copy().stackSize;
                        else
                            inventory[5] = recipe.output.copy();
                    }
                    processMax=0;
                    active=false;
                }
                ArcFurnaceRecipe recipe = getRecipe();
                if(recipe!=null && hasElectrodes()) {
                    LogHelper.info("Stuff isn't ready but its working");
                    for(int i = 0; i < 3; i++)
                        if(this.inventory[i].attemptDamageItem(1, worldObj.rand)) {
                            this.inventory[i] = null;
                        }
                    this.process=recipe.time;
                    this.processMax=process;
                    this.active=true;
                }
            }

            if(a!=active || b) {
                this.markDirty();
                this.markBlockForUpdate(getPos(), null);
                worldObj.addBlockEvent(getPos(), ModBlocks.ARC_FURNACE, 1,active?1:0);
            }
        }
    }

    public boolean hasElectrodes() {
        for(int i = 0; i < 3; i++)
            if(inventory[i] == null)
                return false;
        return true;
    }

    public ArcFurnaceRecipe getRecipe() {
        ArcFurnaceRecipe recipe;
        if (inventory[3] != null && inventory[4] != null)
            recipe = ArcFurnaceRecipe.findRecipe(inventory[3], inventory[4]);
        else
            recipe = null;

        if(recipe==null)
            return null;

        LogHelper.info("Got recipe for input: " + inventory[3] + " and additive: " + inventory[4]);
        if(inventory[5]==null || (OreDictionary.itemMatches(inventory[5],recipe.output,false) && inventory[5].stackSize+recipe.output.stackSize<=getSlotLimit(5)) ) {
            return recipe;
        }

        return null;
    }

    public boolean shouldRenderAsActive() {
        return hasElectrodes();
    }

    @Override
    public int[] getCurrentProcessesStep() {
        return new int[]{processMax-process};
    }

    @Override
    public int[] getCurrentProcessesMax() {
        return new int[]{processMax};
    }

}
