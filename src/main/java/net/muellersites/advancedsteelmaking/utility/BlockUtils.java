package net.muellersites.advancedsteelmaking.utility;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.muellersites.advancedsteelmaking.block.block.BlockEnum;
import net.muellersites.advancedsteelmaking.item.base.ItemBlockEnum;

public class BlockUtils {

    private BlockUtils() {}

    public static ItemBlock getItemBlockFor(Block block) {
        return block instanceof BlockEnum ? new ItemBlockEnum((BlockEnum) block) : new ItemBlock(block);
    }

}
