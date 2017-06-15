package net.muellersites.advancedsteelmaking.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.muellersites.advancedsteelmaking.item.base.ItemBase;
import net.muellersites.advancedsteelmaking.util.NBTUtils;

public class ItemGraphiteElectrode extends ItemBase {

    public static int electrodeMaxDamage = 96000;

    public ItemGraphiteElectrode() {
        super("graphite_electrode");
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity ent, int slot, boolean hand) {
        if(ent instanceof EntityPlayer)
            if(super.getDamage(stack)!=0) {
                ItemStack fixed = new ItemStack(this);
                NBTUtils.setInteger(fixed, "graphDmg", stack.getItemDamage());
                ((EntityPlayer)ent).inventory.setInventorySlotContents(slot, fixed);
            }
    }

    @Override
    public boolean isItemTool(ItemStack stack)
    {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return NBTUtils.getInteger(stack, "graphDmg") / (double)electrodeMaxDamage;
    }
    @Override
    public int getMaxDamage(ItemStack stack) {
        return electrodeMaxDamage;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return NBTUtils.getInteger(stack, "graphDmg") > 0;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return NBTUtils.getInteger(stack, "graphDmg");
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        NBTUtils.setInteger(stack, "graphDmg", damage);
    }

}
