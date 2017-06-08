package net.muellersites.advancedsteelmaking.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.muellersites.advancedsteelmaking.block.block.BlockBase;
import net.muellersites.advancedsteelmaking.block.BlockSteel;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@GameRegistry.ObjectHolder(AdvancedSteelmaking.MOD_ID)
public class ModBlocks {

    private static final List<BlockBase> BLOCKS = new ArrayList<>();

    public static final BlockBase STEEL_BLOCK = new BlockSteel();

    private ModBlocks() {}

    public static Collection<BlockBase> getBlocks() {
        return BLOCKS;
    }

    public static void register(BlockBase block) {
        BLOCKS.add(block);
    }
}
