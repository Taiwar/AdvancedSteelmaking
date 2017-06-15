package net.muellersites.advancedsteelmaking.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArcFurnaceRecipe {

	public final Object input;
	public final ItemStack output;
	public final ItemStack additive;
	public final int time;

	public ArcFurnaceRecipe(ItemStack output, Object input, ItemStack additive, int time) {
		this.output=output;
		this.additive=additive;
		this.input=ApiUtils.convertToValidRecipeInput(input);
		this.time=time;

	}

	public static ArrayList<ArcFurnaceRecipe> recipeList = new ArrayList<ArcFurnaceRecipe>();
	public static void addRecipe(ItemStack output, ItemStack additive, Object input, int time) {
		ArcFurnaceRecipe recipe = new ArcFurnaceRecipe(output, input, additive, time);
		if(recipe.input!=null)
			LogHelper.info("Added arc recipe with input: " + input + " and additive: " + additive);
			recipeList.add(recipe);
	}

	public static ArcFurnaceRecipe findRecipe(ItemStack input, ItemStack additive) {
		for(ArcFurnaceRecipe recipe : recipeList)
			if(ApiUtils.stackMatchesObject(input, recipe.input) && ApiUtils.stackMatchesObject(additive, recipe.additive))
				return recipe;
		return null;
	}

	public static List<ArcFurnaceRecipe> removeRecipes(ItemStack stack) {
		List<ArcFurnaceRecipe> list = new ArrayList();
		Iterator<ArcFurnaceRecipe> it = recipeList.iterator();
		while(it.hasNext()) {
			ArcFurnaceRecipe ir = it.next();
			if(OreDictionary.itemMatches(ir.output, stack, true)) {
				list.add(ir);
				it.remove();
			}
		}
		return list;
	}

    public static boolean isValidRecipeInput(ItemStack input) {
		for (ArcFurnaceRecipe recipe : recipeList)
			if (ApiUtils.stackMatchesObject(input, recipe.input))
				return true;
        return false;
    }

    public static boolean isValidRecipeAdditive(ItemStack additive) {
		for (ArcFurnaceRecipe recipe : recipeList)
			if (ApiUtils.stackMatchesObject(additive, recipe.additive))
				return true;
		return false;
    }

}
