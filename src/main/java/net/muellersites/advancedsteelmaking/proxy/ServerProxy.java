package net.muellersites.advancedsteelmaking.proxy;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class ServerProxy extends CommonProxy {

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Override
    public void onServerStopping(FMLServerStoppingEvent event) {

    }

    @Override
    public ClientProxy getClientProxy() {
        return null;
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xVelocity, double yVelocity, double zVelocity) {

    }

}
