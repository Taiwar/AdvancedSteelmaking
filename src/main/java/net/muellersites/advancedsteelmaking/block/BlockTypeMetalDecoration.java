package net.muellersites.advancedsteelmaking.block;

import net.minecraft.util.IStringSerializable;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;

import java.util.Locale;

public enum BlockTypeMetalDecoration implements IStringSerializable, BlockBase.IBlockEnum {
    STEEL_BLOCK;

    @Override
    public String getName() {
        return this.toString().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public int getMeta()
    {
        return ordinal();
    }

    @Override
    public boolean listForCreative()
    {
        return true;
    }
}