package net.muellersites.advancedsteelmaking.utility;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

public class ResourceLocationHelper {

    private ResourceLocationHelper() {}

    public static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(AdvancedSteelmaking.MOD_ID, path);
    }

    public static ModelResourceLocation getModelResourceLocation(String path) {
        return new ModelResourceLocation(AdvancedSteelmaking.MOD_ID + ":" + path);
    }

}
