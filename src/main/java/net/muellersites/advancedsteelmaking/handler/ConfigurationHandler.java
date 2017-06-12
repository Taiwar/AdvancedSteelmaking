package net.muellersites.advancedsteelmaking.handler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration configuration;
    public static boolean duplicateSteel = true;

    public static void init(File configFile) {
        // Create config obj from given file obj
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(AdvancedSteelmaking.MOD_ID)) {
            // Resync configs
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {

        duplicateSteel = configuration.get(Configuration.CATEGORY_GENERAL, "duplicateSteel" ,true).getBoolean(true);

        if (configuration.hasChanged()) {
            configuration.save();
        }

        LogHelper.info("Dupe steel?: " + duplicateSteel);
    }
}
