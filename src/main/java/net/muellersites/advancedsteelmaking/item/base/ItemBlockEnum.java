package net.muellersites.advancedsteelmaking.item.base;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.muellersites.advancedsteelmaking.block.block.BlockEnum;

public class ItemBlockEnum extends ItemBlock {

    protected final BlockEnum block;

    public ItemBlockEnum(BlockEnum block) {
        super(block);
        this.block = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return (block != null) ? this.block.getUnlocalizedName(itemStack) : super.getUnlocalizedName(itemStack);
    }

}
