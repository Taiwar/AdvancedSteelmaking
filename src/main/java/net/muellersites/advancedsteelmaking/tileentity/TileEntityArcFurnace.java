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
import net.muellersites.advancedsteelmaking.block.base.BlockInterfaces;
import net.muellersites.advancedsteelmaking.reference.GuiIds;
import net.muellersites.advancedsteelmaking.util.ASInventoryHandler;
import net.muellersites.advancedsteelmaking.util.IASInventory;
import net.muellersites.advancedsteelmaking.util.Utils;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class TileEntityArcFurnace extends TileEntityBase implements IASInventory, BlockInterfaces.IGuiTile, BlockInterfaces.IDirectionalTile, BlockInterfaces.IHasObjProperty, BlockInterfaces.ITileDrop {

    ItemStack[] inventory = new ItemStack[5];
    public String customName = "arc_furnace";
    public EnumFacing facing = EnumFacing.NORTH;

    @Override
    public void readCustomNBT(NBTTagCompound nbt, boolean descPacket) {
        facing = EnumFacing.getFront(nbt.getInteger("facing"));
        inventory = Utils.readInventory(nbt.getTagList("inventory", 10), 5);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket) {
        nbt.setInteger("facing", facing.ordinal());
        nbt.setTag("inventory", Utils.writeInventory(inventory));
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
        return 64;
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
}
