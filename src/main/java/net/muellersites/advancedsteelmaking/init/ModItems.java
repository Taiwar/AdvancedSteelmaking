package net.muellersites.advancedsteelmaking.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.muellersites.advancedsteelmaking.item.base.ItemBase;
import net.muellersites.advancedsteelmaking.item.ItemSteel;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@GameRegistry.ObjectHolder(AdvancedSteelmaking.MOD_ID)
public class ModItems {

    private static final List<ItemBase> ITEMS = new ArrayList<>();

    public static final ItemBase STEEL_INGOT = new ItemSteel();

    private ModItems() {}

    public static Collection<ItemBase> getItems() {
        return ITEMS;
    }

    public static void register(ItemBase item) {
        ITEMS.add(item);
    }
}