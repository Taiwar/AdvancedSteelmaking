package net.muellersites.advancedsteelmaking.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.muellersites.advancedsteelmaking.api.ArcFurnaceRecipe;
import net.muellersites.advancedsteelmaking.init.ModItems;

public class ASSlot extends Slot {

    final Container container;
    public ASSlot(Container container, IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
        this.container=container;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }

    public static class Output extends ASSlot {
        public Output(Container container, IInventory inv, int id, int x, int y) {
            super(container, inv, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return false;
        }
        @Override
        public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
            super.onPickupFromSlot(player, stack);
        }
    }

    public static class ArcInput extends ASSlot {
        public ArcInput(Container container, IInventory inv, int id, int x, int y)
        {
            super(container, inv, id, x, y);
        }
        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return itemStack!=null && ArcFurnaceRecipe.isValidRecipeInput(itemStack);
        }
    }

    public static class ArcElectrode extends ASSlot {

        public ArcElectrode(Container container, IInventory inv, int id, int x, int y) {
            super(container, inv, id, x, y);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return itemStack!=null && ModItems.GRAPHITE_ELECTRODE.equals(itemStack.getItem());
        }
    }

    public static class ArcAdditive extends ASSlot {
        public ArcAdditive(Container container, IInventory inv, int id, int x, int y) {
            super(container, inv, id, x, y);
        }
        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return itemStack!=null && ArcFurnaceRecipe.isValidRecipeAdditive(itemStack);
        }
    }

}
