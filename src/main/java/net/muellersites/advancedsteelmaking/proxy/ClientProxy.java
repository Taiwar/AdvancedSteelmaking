package net.muellersites.advancedsteelmaking.proxy;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.init.ModItems;
import net.muellersites.advancedsteelmaking.item.base.ItemBase;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

public class ClientProxy extends CommonProxy {

    @Override
    public ClientProxy getClientProxy()
    {
        return this;
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

        super.onPreInit(event);

        // Initialize models and textures
        ModItems.getItems().forEach(ItemBase::initModelsAndVariants);
        ModBlocks.getBlocks().forEach(BlockBase::initModelsAndVariants);
        OBJLoader.INSTANCE.addDomain(AdvancedSteelmaking.MOD_ID);

        // Register keybindings
        /*ClientRegistry.registerKeyBinding(Keybindings.CHARGE);
        ClientRegistry.registerKeyBinding(Keybindings.EXTRA);
        ClientRegistry.registerKeyBinding(Keybindings.RELEASE);
        ClientRegistry.registerKeyBinding(Keybindings.TOGGLE);*/
    }

    @Override
    public void onInit(FMLInitializationEvent event) {

        super.onInit(event);

        // Register custom item color handler
        ModItems.getItems().stream()
                .filter(itemAS -> itemAS instanceof IItemColor)
                .forEach(itemAS -> FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(((IItemColor) itemAS), itemAS));

        // Register client specific event handlers
        //MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
        //MinecraftForge.EVENT_BUS.register(new ItemTooltipEventHandler());
    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Override
    public void onServerStopping(FMLServerStoppingEvent event) {

    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xVelocity, double yVelocity, double zVelocity) {
        //ClientParticleHelper.spawnParticleAtLocation(particleType, xCoord, yCoord, zCoord, xVelocity, yVelocity, zVelocity);
    }

}
