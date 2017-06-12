package net.muellersites.advancedsteelmaking.item.base;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.muellersites.advancedsteelmaking.creativetab.CreativeTab;
import net.muellersites.advancedsteelmaking.init.ModItems;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.util.ResourceLocationHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemBase extends Item implements IItemVariantHolder<ItemBase> {

    private final String BASE_NAME;
    private final String[] VARIANTS;

    public ItemBase(String name, String ... variants) {

        super();
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTab.AS_TAB);
        setMaxStackSize(1);
        setNoRepair();

        BASE_NAME = name;
        if (variants.length > 0) {
            VARIANTS = variants;
        }
        else {
            VARIANTS = new String[0];
        }

        ModItems.register(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {

        if (hasSubtypes && itemStack.getMetadata() < VARIANTS.length) {
            return String.format("item.%s:%s", AdvancedSteelmaking.MOD_ID, VARIANTS[Math.abs(itemStack.getMetadata() % VARIANTS.length)]);
        }
        else {
            return String.format("item.%s:%s", AdvancedSteelmaking.MOD_ID, BASE_NAME);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list) {

        if (getHasSubtypes() && VARIANTS.length > 0) {
            for (int meta = 0; meta < VARIANTS.length; ++meta) {
                list.add(new ItemStack(this, 1, meta));
            }
        }
        else {
            super.getSubItems(item, creativeTab, list);
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModelsAndVariants() {

        if (getCustomMeshDefinition() != null) {

            for (String VARIANT : VARIANTS) {
                ModelBakery.registerItemVariants(this, ResourceLocationHelper.getModelResourceLocation(VARIANT));
            }

            ModelLoader.setCustomMeshDefinition(this, getCustomMeshDefinition());
        }
        else {

            if (getHasSubtypes() && VARIANTS.length > 0) {
                List<ModelResourceLocation> modelResources = new ArrayList<>();

                for (String VARIANT : VARIANTS) {
                    modelResources.add(ResourceLocationHelper.getModelResourceLocation(VARIANT));
                }

                ModelBakery.registerItemVariants(this, modelResources.toArray(new ModelResourceLocation[0]));
                ModelLoader.setCustomMeshDefinition(this, itemStack -> modelResources.get(itemStack.getMetadata()));
            }
            else {
                ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
            }
        }
    }

    @Override
    public ItemBase getItem() {
        return this;
    }

    @Override
    public String[] getVariants() {
        return VARIANTS;
    }

}
