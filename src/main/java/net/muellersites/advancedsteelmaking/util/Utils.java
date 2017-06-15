package net.muellersites.advancedsteelmaking.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class Utils {

    public static ItemStack copyStackWithAmount(ItemStack stack, int amount) {
        if (stack == null)
            return null;
        ItemStack s2 = stack.copy();
        s2.stackSize = amount;
        return s2;
    }

    public static ItemStack[] readInventory(NBTTagList nbt, int size) {
        ItemStack[] inv = new ItemStack[size];
        int max = nbt.tagCount();
        for (int i = 0; i < max; i++) {
            NBTTagCompound itemTag = nbt.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < size)
                inv[slot] = ItemStack.loadItemStackFromNBT(itemTag);
        }
        return inv;
    }

    public static NBTTagList writeInventory(ItemStack[] inv) {
        NBTTagList invList = new NBTTagList();
        for (int i = 0; i < inv.length; i++)
            if (inv[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                inv[i].writeToNBT(itemTag);
                invList.appendTag(itemTag);
            }
        return invList;
    }

    public static ItemStack insertStackIntoInventory(TileEntity inventory, ItemStack stack, EnumFacing side) {
        if (stack != null && inventory != null && inventory.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
            IItemHandler handler = inventory.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            ItemStack temp = ItemHandlerHelper.insertItem(handler, stack.copy(), true);
            if (temp == null || temp.stackSize < stack.stackSize)
                return ItemHandlerHelper.insertItem(handler, stack, false);
        }
        return stack;
    }

    public static boolean compareItemNBT(ItemStack stack1, ItemStack stack2) {
        if((stack1==null) != (stack2==null))
            return false;
        boolean empty1 = (stack1.getTagCompound()==null||stack1.getTagCompound().hasNoTags());
        boolean empty2 = (stack2.getTagCompound()==null||stack2.getTagCompound().hasNoTags());
        if(empty1!=empty2)
            return false;
        if(!empty1 && !stack1.getTagCompound().equals(stack2.getTagCompound()))
            return false;
        return stack1.areCapsCompatible(stack2);
    }

    public static void modifyInvStackSize(ItemStack[] inv, int slot, int amount) {
        if(slot>=0&&slot<inv.length && inv[slot]!=null)
        {
            inv[slot].stackSize += amount;
            if(inv[slot].stackSize<=0)
                inv[slot] = null;
        }
    }

}