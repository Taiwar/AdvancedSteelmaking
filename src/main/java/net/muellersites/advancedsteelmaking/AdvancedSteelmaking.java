package net.muellersites.advancedsteelmaking;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.muellersites.advancedsteelmaking.proxy.IProxy;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import static net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_ID;

@Mod(modid = MOD_ID,
        name = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_NAME,
        version = "@MOD_VERSION@",
        guiFactory = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.GUI_FACTORY_CLASS)
public class AdvancedSteelmaking {

    @Mod.Instance(MOD_ID)
    public static AdvancedSteelmaking instance;

    @SidedProxy(clientSide = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.CLIENT_PROXY_CLASS, serverSide = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Hello World!");
        LogHelper.info("Preinit started");

        proxy.onPreInit(event);

        LogHelper.info("Preinit finished");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.info("Init started");

        proxy.onInit(event);

        LogHelper.info("Init finished");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info("Postinit started");

        proxy.onPostInit(event);

        LogHelper.info("Postinit finished");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }

    public static <T extends IForgeRegistryEntry<?>> T register(T object, String name) {
        return registerByFullName(object, MOD_ID+":"+name);
    }

    public static <T extends IForgeRegistryEntry<?>> T registerByFullName(T object, String name) {
        object.setRegistryName(new ResourceLocation(name));
        return GameRegistry.register(object);
    }
    public static Block registerBlockByFullName(Block block, ItemBlock itemBlock, String name)
    {
        block = registerByFullName(block, name);
        registerByFullName(itemBlock, name);
        return block;
    }

    public static Block registerBlockByFullName(Block block, Class<? extends ItemBlock> itemBlock, String name) {
        try {
            return registerBlockByFullName(block, itemBlock.getConstructor(Block.class).newInstance(block), name);
        } catch(Exception e){e.printStackTrace();}
        return null;
    }

    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name) {
        try {
            return registerBlockByFullName(block, itemBlock.getConstructor(Block.class).newInstance(block), MOD_ID+":"+name);
        } catch(Exception e){e.printStackTrace();}
        return null;
    }

}
