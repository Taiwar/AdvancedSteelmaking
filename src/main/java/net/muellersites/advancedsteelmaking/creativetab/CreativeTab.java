package net.muellersites.advancedsteelmaking.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.muellersites.advancedsteelmaking.init.ModItems;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

public class CreativeTab {

    public static final CreativeTabs AS_TAB = new CreativeTabs(AdvancedSteelmaking.MOD_ID) {

        @Override
        public Item getTabIconItem() {
            return ModItems.STEEL_INGOT;
        }
    };

}
