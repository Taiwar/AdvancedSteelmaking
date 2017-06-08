package net.muellersites.advancedsteelmaking;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.muellersites.advancedsteelmaking.proxy.IProxy;
import net.muellersites.advancedsteelmaking.utility.LogHelper;

@Mod(modid = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_ID,
        name = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_NAME,
        version = "@MOD_VERSION@",
        guiFactory = net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.GUI_FACTORY_CLASS)
public class AdvancedSteelmaking {

    @Mod.Instance(net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_ID)
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

        LogHelper.info("Postinit finish");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }

}
