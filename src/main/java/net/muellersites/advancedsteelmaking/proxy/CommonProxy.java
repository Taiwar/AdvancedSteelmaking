package net.muellersites.advancedsteelmaking.proxy;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.muellersites.advancedsteelmaking.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.handler.ConfigurationHandler;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.init.ModItems;
import net.muellersites.advancedsteelmaking.init.Recipes;
import net.muellersites.advancedsteelmaking.utility.BlockUtils;

public abstract class CommonProxy implements IProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        ModItems.getItems().forEach(GameRegistry::register);

        for (Block block : ModBlocks.getBlocks()) {
            GameRegistry.register(block);
            GameRegistry.register(BlockUtils.getItemBlockFor(block), block.getRegistryName());
        }
    }

    @Override
    public void onInit(FMLInitializationEvent event) {

        // Register the GUI Handler
        //NetworkRegistry.INSTANCE.registerGuiHandler(AdvancedSteelmaking.instance, new GuiHandler());

        // Initialize the blacklist registry
        //BlacklistRegistry.INSTANCE.load();

        // Initialize mod tile entities
        //TileEntities.init();

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

}
