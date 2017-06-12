package net.muellersites.advancedsteelmaking.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Utils {

    public static ItemStack copyStackWithAmount(ItemStack stack, int amount) {
        if(stack==null)
            return null;
        ItemStack s2 = stack.copy();
        s2.stackSize=amount;
        return s2;
    }

    public static ItemStack[] readInventory(NBTTagList nbt, int size) {
        ItemStack[] inv = new ItemStack[size];
        int max = nbt.tagCount();
        for (int i = 0;i<max;i++) {
            NBTTagCompound itemTag = nbt.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if(slot>=0 && slot<size)
                inv[slot] = ItemStack.loadItemStackFromNBT(itemTag);
        }
        return inv;
    }

    public static NBTTagList writeInventory(ItemStack[] inv) {
        NBTTagList invList = new NBTTagList();
        for(int i=0; i<inv.length; i++)
            if(inv[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                inv[i].writeToNBT(itemTag);
                invList.appendTag(itemTag);
            }
        return invList;
    }

}
