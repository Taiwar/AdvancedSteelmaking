package net.muellersites.advancedsteelmaking.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.muellersites.advancedsteelmaking.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.block.base.BlockInterfaces;
import net.muellersites.advancedsteelmaking.client.render.TileRenderArcFurnace;
import net.muellersites.advancedsteelmaking.handler.ConfigurationHandler;
import net.muellersites.advancedsteelmaking.handler.GuiHandler;
import net.muellersites.advancedsteelmaking.init.ModItems;
import net.muellersites.advancedsteelmaking.init.ModTileEntities;
import net.muellersites.advancedsteelmaking.init.Recipes;
import net.muellersites.advancedsteelmaking.item.base.ItemInterfaces.IGuiItem;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import javax.annotation.Nonnull;

public abstract class CommonProxy implements IProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        ModItems.getItems().forEach(GameRegistry::register);

        for (TileEntity tileEntity : ModTileEntities.getTileEntities()) {
            LogHelper.info("Registered tileEntity: " + tileEntity.getClass().getSimpleName());
            GameRegistry.registerTileEntity(tileEntity.getClass(), net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_ID + tileEntity.getClass().getSimpleName());
        }
    }

    @Override
    public void onInit(FMLInitializationEvent event) {

        // Register the GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedSteelmaking.instance, new GuiHandler());

        // Register Special Renderer(s)
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArcFurnace.class, new TileRenderArcFurnace());

        // Initialize the blacklist registry
        //BlacklistRegistry.INSTANCE.load();

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        //MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
        //MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
        //MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        //MinecraftForge.EVENT_BUS.register(new CraftingHandler());

        Recipes.init();
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event){

    }

    public static <T extends TileEntity & BlockInterfaces.IGuiTile> void openGuiForTile(@Nonnull EntityPlayer player, @Nonnull T tile) {
        player.openGui(AdvancedSteelmaking.instance, tile.getGuiID(), tile.getWorld(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
    }

    public static void openGuiForItem(@Nonnull EntityPlayer player, @Nonnull EntityEquipmentSlot slot) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        if(stack==null || !(stack.getItem() instanceof IGuiItem))
            return;
        IGuiItem gui = (IGuiItem)stack.getItem();
        player.openGui(AdvancedSteelmaking.instance, 100*slot.ordinal() + gui.getGuiID(stack), player.worldObj, (int)player.posX,(int)player.posY,(int)player.posZ);
    }

}
