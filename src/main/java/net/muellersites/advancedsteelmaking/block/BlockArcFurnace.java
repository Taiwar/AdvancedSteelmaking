package net.muellersites.advancedsteelmaking.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.muellersites.advancedsteelmaking.api.ASProperties;
import net.muellersites.advancedsteelmaking.block.base.BlockTileEntityProvider;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.item.base.ItemBlockBase;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

public class BlockArcFurnace extends BlockTileEntityProvider {

    private static final AxisAlignedBB AABB_ARC_FURNACE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockArcFurnace() {
        super("arc_furnace", Material.IRON, PropertyEnum.create("type", BlockTypeMetalDevice.class), ItemBlockBase.class, ASProperties.FACING_HORIZONTAL);
        this.setHardness(3.0F);
        this.setResistance(15.0F);

        lightOpacity = 0;
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.ARC_FURNACE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        BlockStateContainer base = super.createBlockState();
        IUnlistedProperty[] unlisted = (base instanceof ExtendedBlockState)?((ExtendedBlockState)base).getUnlistedProperties().toArray(new IUnlistedProperty[0]):new IUnlistedProperty[0];
        unlisted = Arrays.copyOf(unlisted, unlisted.length+1);
        unlisted[unlisted.length-1] = ASProperties.CONNECTIONS;
        return new ExtendedBlockState(this, base.getProperties().toArray(new IProperty[0]), unlisted);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        LogHelper.info("Created new ArcFurnace Entity");
        return new TileEntityArcFurnace();
    }

    @Override
    public String getASBlockName() {
        return null;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
