package net.muellersites.advancedsteelmaking.api;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class Enums {

    public enum SideConfig implements IStringSerializable {
        NONE("none"),
        INPUT("in"),
        OUTPUT("out");

        final String texture;

        SideConfig(String texture)
        {
            this.texture = texture;
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.ENGLISH);
        }

        public String getTextureName()
        {
            return texture;
        }

        public static SideConfig next(SideConfig current)
        {
            return current==INPUT?OUTPUT: current==OUTPUT?NONE: INPUT;
        }
    }

}
