package net.muellersites.advancedsteelmaking.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;

public class TileRenderArcFurnace extends TileEntitySpecialRenderer<TileEntityArcFurnace> {

    @Override
    public void renderTileEntityAt(TileEntityArcFurnace te, double x, double y, double z, float partialTicks, int destroyStage) {
        if(!te.getWorld().isBlockLoaded(te.getPos(), false))
            return;

        final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        BlockPos blockPos = te.getPos();
    }
}
