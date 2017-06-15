package net.muellersites.advancedsteelmaking.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.OreDictionary;
import net.muellersites.advancedsteelmaking.util.Utils;

import java.util.Collection;
import java.util.List;

import static net.muellersites.advancedsteelmaking.util.Utils.copyStackWithAmount;

public class ApiUtils {

    public static boolean compareToOreName(ItemStack stack, String oreName) {
        if(!isExistingOreName(oreName))
            return false;
        ItemStack comp = copyStackWithAmount(stack, 1);
        List<ItemStack> s = OreDictionary.getOres(oreName);
        for(ItemStack st:s)
            if(OreDictionary.itemMatches(st, stack, false))
                return true;
        return false;
    }

    public static boolean isExistingOreName(String name) {
        if(!OreDictionary.doesOreNameExist(name))
            return false;
        else
            return !OreDictionary.getOres(name).isEmpty();
    }

    public static Object convertToValidRecipeInput(Object input) {
        if(input instanceof ItemStack)
            return input;
        else if(input instanceof Item)
            return new ItemStack((Item)input);
        else if(input instanceof Block)
            return new ItemStack((Block)input);
        else if(input instanceof List)
            return input;
        else if(input instanceof String) {
            if(!ApiUtils.isExistingOreName((String)input))
                return null;
            List<ItemStack> l = OreDictionary.getOres((String)input);
            if(!l.isEmpty())
                return l;
            else
                return null;
        }
        else
            throw new RuntimeException("Recipe Inputs must always be ItemStack, Item, Block or String (OreDictionary name), "+input+" is invalid");
    }

    public static boolean stackMatchesObject(ItemStack stack, Object o)
    {
        return stackMatchesObject(stack, o, false);
    }

    public static boolean stackMatchesObject(ItemStack stack, Object o, boolean checkNBT) {
        if(o instanceof ItemStack)
            return OreDictionary.itemMatches((ItemStack)o, stack, false) && (!checkNBT || ((ItemStack)o).getItemDamage() == OreDictionary.WILDCARD_VALUE || Utils.compareItemNBT((ItemStack)o, stack));
        else if(o instanceof Collection) {
            for(Object io : (Collection)o)
                if(io instanceof ItemStack && OreDictionary.itemMatches((ItemStack)io, stack, false) && (!checkNBT || ((ItemStack)io).getItemDamage() == OreDictionary.WILDCARD_VALUE || Utils.compareItemNBT((ItemStack)io, stack)))
                    return true;
        } else if(o instanceof IngredientStack)
            return ((IngredientStack)o).matchesItemStack(stack);
        else if(o instanceof ItemStack[]) {
            for(ItemStack io : (ItemStack[])o)
                if(OreDictionary.itemMatches(io, stack, false) && (!checkNBT || io.getItemDamage() == OreDictionary.WILDCARD_VALUE || Utils.compareItemNBT(io, stack)))
                    return true;
        } else if(o instanceof FluidStack) {
            FluidStack fs = FluidUtil.getFluidContained(stack);
            return fs != null && fs.containsFluid((FluidStack)o);
        } else if(o instanceof String)
            return compareToOreName(stack, (String)o);
        return false;
    }

}
