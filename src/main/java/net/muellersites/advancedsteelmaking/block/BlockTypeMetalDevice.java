package net.muellersites.advancedsteelmaking.block;

import net.minecraft.util.IStringSerializable;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;

import java.util.Locale;

public enum BlockTypeMetalDevice implements IStringSerializable, BlockBase.IBlockEnum {
    ARC_FURNACE;

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
        return ordinal()!=12;
    }
}
