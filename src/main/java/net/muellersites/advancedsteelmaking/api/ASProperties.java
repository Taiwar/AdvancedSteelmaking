package net.muellersites.advancedsteelmaking.api;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;

import java.util.Collection;
import java.util.Set;

public class ASProperties {

    public static final PropertyDirection FACING_ALL = PropertyDirection.create("facing");
    public static final PropertyDirection FACING_HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertySet CONNECTIONS = new PropertySet("conns");

    public static final ProperySideConfig[] SIDECONFIG = {
            new ProperySideConfig("sideconfig_down"),
            new ProperySideConfig("sideconfig_up"),
            new ProperySideConfig("sideconfig_north"),
            new ProperySideConfig("sideconfig_south"),
            new ProperySideConfig("sideconfig_west"),
            new ProperySideConfig("sideconfig_east")
    };
    public static final PropertyBoolInverted[] SIDECONNECTION = {
            PropertyBoolInverted.create("sideconnection_down"),
            PropertyBoolInverted.create("sideconnection_up"),
            PropertyBoolInverted.create("sideconnection_north"),
            PropertyBoolInverted.create("sideconnection_south"),
            PropertyBoolInverted.create("sideconnection_west"),
            PropertyBoolInverted.create("sideconnection_east")
    };

    public static class PropertyBoolInverted extends PropertyHelper<Boolean> {
        private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.valueOf(false), Boolean.valueOf(true));

        protected PropertyBoolInverted(String name) {
            super(name, Boolean.class);
        }

        @Override
        public Collection<Boolean> getAllowedValues() {
            return this.allowedValues;
        }

        @Override
        public Optional<Boolean> parseValue(String value) {
            return Optional.of(Boolean.getBoolean(value));
        }

        public static PropertyBoolInverted create(String name) {
            return new PropertyBoolInverted(name);
        }

        @Override
        public String getName(Boolean value) {
            return value.toString();
        }
    }

    public static class ProperySideConfig implements IUnlistedProperty<Enums.SideConfig> {
        final String name;
        public ProperySideConfig(String name)
        {
            this.name = name;
        }
        @Override
        public String getName()
        {
            return name;
        }
        @Override
        public boolean isValid(Enums.SideConfig value)
        {
            return true;
        }
        @Override
        public Class<Enums.SideConfig> getType()
        {
            return Enums.SideConfig.class;
        }
        @Override
        public String valueToString(Enums.SideConfig value)
        {
            return value.toString();
        }
    }

    @SuppressWarnings("rawtypes")
    public static class PropertySet implements IUnlistedProperty<Set> {
        String name;

        public PropertySet(String n)
        {
            name = n;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public boolean isValid(Set value)
        {
            return value != null;
        }

        @Override
        public Class<Set> getType()
        {
            return Set.class;
        }

        @Override
        public String valueToString(Set value)
        {
            return value.toString();
        }
    }

}
