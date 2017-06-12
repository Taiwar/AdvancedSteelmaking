package net.muellersites.advancedsteelmaking.init;

import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModTileEntities {

    private static final List<TileEntityBase> TILE_ENTITIES = new ArrayList<>();

    public static final TileEntityBase ARC_FURNACE = new TileEntityArcFurnace();

    private ModTileEntities() {}

    public static Collection<TileEntityBase> getTileEntities() {
        return TILE_ENTITIES;
    }

    public static void register(TileEntityBase tileEntity) {
        TILE_ENTITIES.add(tileEntity);
    }

}
