package net.muellersites.advancedsteelmaking.block;


import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.muellersites.advancedsteelmaking.api.ASProperties;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;
import net.muellersites.advancedsteelmaking.item.base.ItemBlockBase;

public class BlockSteel extends BlockBase {

    public BlockSteel() {
        super("steel_block", Material.IRON, PropertyEnum.create("type", BlockTypeMetalDecoration.class), ItemBlockBase.class);
        this.setHardness(3.0F);
        this.setResistance(15.0F);

        lightOpacity = 0;
    }
}
