package net.muellersites.advancedsteelmaking.block.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.muellersites.advancedsteelmaking.creativetab.CreativeTab;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking;

public abstract class BlockBase extends Block {

    private final String BASE_NAME;

    public BlockBase(String name) {
        this(name, Material.ROCK);
    }

    public BlockBase(String name, Material material) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTab.AS_TAB);
        BASE_NAME = name;
        ModBlocks.register(this);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s:%s", AdvancedSteelmaking.MOD_ID, BASE_NAME);
    }

    @SideOnly(Side.CLIENT)
    public void initModelsAndVariants() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString()));
    }

}
