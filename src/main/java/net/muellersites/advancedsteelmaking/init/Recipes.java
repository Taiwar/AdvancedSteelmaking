package net.muellersites.advancedsteelmaking.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {

    public static void init() {
        initModRecipes();
    }

    private static void initModRecipes() {

        // Register ModItems to OreDict
        OreDictionary.registerOre("ingotSteel", new ItemStack(ModItems.STEEL_INGOT));
        OreDictionary.registerOre("blockSteel", new ItemStack(ModBlocks.STEEL_BLOCK));

        // Iron Ingot --> Steel Ingot
        GameRegistry.addSmelting(Items.IRON_INGOT, new ItemStack(ModItems.STEEL_INGOT, 1, 0), 5f);

        // Iron Block --> Steel Block
        GameRegistry.addSmelting(Blocks.IRON_BLOCK, new ItemStack((ModBlocks.STEEL_BLOCK), 1, 0), 5f);

        // Steel Ingots --> Steel Block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.STEEL_BLOCK, 1, 0), "fff", "fff", "fff", 'f', "ingotSteel"));

        // Steel Block --> Steel Ingots
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.STEEL_INGOT, 9, 0), "blockSteel"));

        // Coal Blocks --> Graphite Electrode
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.GRAPHITE_ELECTRODE)," f ", " f ", " f ", 'f', new ItemStack(Blocks.COAL_BLOCK));

        // Steel Ingots, Iron Blocks, Redstone Dust --> Arc Furnace
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ARC_FURNACE),"fff", "ghg", "ggg", 'f', "ingotSteel", 'g', "blockIron", 'h', "dustRedstone"));
    }

}
