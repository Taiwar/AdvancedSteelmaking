package net.muellersites.advancedsteelmaking.util;

import net.minecraft.item.ItemStack;

public interface IASInventory {

    ItemStack[] getInventory();
    boolean isStackValid(int slot, ItemStack stack);
    int getSlotLimit(int slot);
    void doGraphicalUpdates(int slot);
    default ItemStack[] getDroppedItems(){return getInventory();}

}
