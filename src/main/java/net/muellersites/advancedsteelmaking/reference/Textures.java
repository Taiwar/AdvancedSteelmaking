package net.muellersites.advancedsteelmaking.reference;

import net.minecraft.util.ResourceLocation;
import net.muellersites.advancedsteelmaking.util.ResourceLocationHelper;

public class Textures {

    public static final String RESOURCE_PREFIX = AdvancedSteelmaking.MOD_ID + ":";

    public static final class Model
    {
        private static final String MODEL_TEXTURE_LOCATION = "textures/models/";
        public static final ResourceLocation ARC_FURNACE_IDLE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "arc_furnace.png");
    }

    public static final class Gui
    {
        protected static final String GUI_TEXTURE_LOCATION = "textures/gui/";
        public static final ResourceLocation ARC_FURNACE = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "arc_furnace.png");
    }

}
