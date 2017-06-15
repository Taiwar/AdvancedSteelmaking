package net.muellersites.advancedsteelmaking.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.muellersites.advancedsteelmaking.client.gui.GuiArcFurnace;
import net.muellersites.advancedsteelmaking.common.gui.container.ContainerArcFurnace;
import net.muellersites.advancedsteelmaking.reference.GuiIds;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiIds.ARC_FURNACE)
            return new ContainerArcFurnace(player.inventory, (TileEntityArcFurnace) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiIds.ARC_FURNACE)
            return new GuiArcFurnace(player.inventory, (TileEntityArcFurnace) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }

}
