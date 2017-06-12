package net.muellersites.advancedsteelmaking.block.base;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.muellersites.advancedsteelmaking.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.creativetab.CreativeTab;
import net.muellersites.advancedsteelmaking.init.ModBlocks;
import net.muellersites.advancedsteelmaking.item.base.ItemBlockBase;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import java.util.ArrayList;
import java.util.Set;

import static net.muellersites.advancedsteelmaking.reference.AdvancedSteelmaking.MOD_ID;

public abstract class BlockBase<E extends Enum<E> & BlockBase.IBlockEnum> extends Block implements BlockInterfaces.IASMetaBlock {

    protected static IProperty[] tempProperties;
    protected static IUnlistedProperty[] tempUnlistedProperties;

    public final String name;
    public final PropertyEnum<E> property;
    public final IProperty[] additionalProperties;
    public final IUnlistedProperty[] additionalUnlistedProperties;
    public final E[] enumValues;
    boolean[] isMetaHidden;
    boolean[] hasFlavour;
    protected Set<BlockRenderLayer> renderLayers = Sets.newHashSet(BlockRenderLayer.SOLID);
    protected Set<BlockRenderLayer>[] metaRenderLayers;
    private boolean opaqueCube = false;

    public BlockBase(String name, Material material, PropertyEnum<E> mainProperty, Class<? extends ItemBlockBase> itemBlock, Object... additionalProperties) {
        super(setTempProperties(material, mainProperty, additionalProperties));
        this.name = name;
        this.property = mainProperty;
        this.enumValues = mainProperty.getValueClass().getEnumConstants();
        this.isMetaHidden = new boolean[this.enumValues.length];
        this.hasFlavour = new boolean[this.enumValues.length];
        this.metaRenderLayers = new Set[this.enumValues.length];
        ArrayList<IProperty> propList = new ArrayList<IProperty>();
        ArrayList<IUnlistedProperty> unlistedPropList = new ArrayList<IUnlistedProperty>();
        for(Object o : additionalProperties)
        {
            if(o instanceof IProperty)
                propList.add((IProperty)o);
            if(o instanceof IProperty[])
                for(IProperty p : ((IProperty[])o))
                    propList.add(p);
            if(o instanceof IUnlistedProperty)
                unlistedPropList.add((IUnlistedProperty)o);
            if(o instanceof IUnlistedProperty[])
                for(IUnlistedProperty p : ((IUnlistedProperty[])o))
                    unlistedPropList.add(p);
        }
        this.additionalProperties = propList.toArray(new IProperty[propList.size()]);
        this.additionalUnlistedProperties = unlistedPropList.toArray(new IUnlistedProperty[unlistedPropList.size()]);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTab.AS_TAB);
        this.adjustSound();

        lightOpacity = 255;
        ModBlocks.register(this);
        String registryName = createRegistryName();
        AdvancedSteelmaking.registerBlockByFullName(this, itemBlock, registryName);
    }

    public String createRegistryName() {
        return MOD_ID+":"+name;
    }

    protected static Material setTempProperties(Material material, PropertyEnum<?> property, Object... additionalProperties) {
        ArrayList<IProperty> propList = new ArrayList<IProperty>();
        ArrayList<IUnlistedProperty> unlistedPropList = new ArrayList<IUnlistedProperty>();
        propList.add(property);
        for(Object o : additionalProperties)
        {
            if(o instanceof IProperty)
                propList.add((IProperty)o);
            if(o instanceof IProperty[])
                for(IProperty p : ((IProperty[])o))
                    propList.add(p);
            if(o instanceof IUnlistedProperty)
                unlistedPropList.add((IUnlistedProperty)o);
            if(o instanceof IUnlistedProperty[])
                for(IUnlistedProperty p : ((IUnlistedProperty[])o))
                    unlistedPropList.add(p);
        }
        tempProperties = propList.toArray(new IProperty[propList.size()]);
        tempUnlistedProperties = unlistedPropList.toArray(new IUnlistedProperty[unlistedPropList.size()]);
        return material;
    }

    void adjustSound() {
        if(this.blockMaterial==Material.ANVIL)
            this.blockSoundType = SoundType.ANVIL;
        else if(this.blockMaterial==Material.CARPET||this.blockMaterial==Material.CLOTH)
            this.blockSoundType = SoundType.CLOTH;
        else if(this.blockMaterial==Material.GLASS||this.blockMaterial==Material.ICE)
            this.blockSoundType = SoundType.GLASS;
        else if(this.blockMaterial==Material.GRASS||this.blockMaterial==Material.TNT||this.blockMaterial==Material.PLANTS||this.blockMaterial==Material.VINE)
            this.blockSoundType = SoundType.PLANT;
        else if(this.blockMaterial==Material.GROUND)
            this.blockSoundType = SoundType.GROUND;
        else if(this.blockMaterial==Material.IRON)
            this.blockSoundType = SoundType.METAL;
        else if(this.blockMaterial==Material.SAND)
            this.blockSoundType = SoundType.SAND;
        else if(this.blockMaterial==Material.SNOW)
            this.blockSoundType = SoundType.SNOW;
        else if(this.blockMaterial==Material.ROCK)
            this.blockSoundType = SoundType.STONE;
        else if(this.blockMaterial==Material.WOOD||this.blockMaterial==Material.CACTUS)
            this.blockSoundType = SoundType.WOOD;
    }

    @Override
    public String getASBlockName() {
        return this.name;
    }

    public interface IBlockEnum extends IStringSerializable {
        int getMeta();
        boolean listForCreative();
    }

    public boolean isOpaqueCube()
    {
        return opaqueCube;
    }
    public BlockBase<E> setOpaque(boolean isOpaque) {
        opaqueCube = isOpaque;
        fullBlock = isOpaque;
        return this;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("tile.%s:%s", MOD_ID, name);
    }

    @SideOnly(Side.CLIENT)
    public void initModelsAndVariants() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString()));
    }

    public void onASBlockPlacedBy(World world, BlockPos pos, IBlockState state, EnumFacing side, float hitX, float hitY, float hitZ, EntityLivingBase placer, ItemStack stack) {
    }

    public boolean canASBlockBePlaced(World world, BlockPos pos, IBlockState newState, EnumFacing side, float hitX, float hitY, float hitZ, EntityPlayer player, ItemStack stack) {
        return true;
    }

    @Override
    public Enum[] getMetaEnums()
    {
        return enumValues;
    }

    @Override
    public IBlockState getInventoryState(int meta) {
        IBlockState state = this.blockState.getBaseState().withProperty(this.property, enumValues[meta]);
        //		for(int i=0; i<this.additionalProperties.length; i++)
        //			if(this.additionalProperties[i]!=null && !this.additionalProperties[i].getAllowedValues().isEmpty())
        //				state = state.withProperty(this.additionalProperties[i], this.additionalProperties[i].getAllowedValues().toArray()[0]);
        return state;
    }

    @Override
    public PropertyEnum<E> getMetaProperty()
    {
        return this.property;
    }

    @Override
    public boolean useCustomStateMapper() {
        return false;
    }

    @Override
    public String getCustomStateMapping(int meta, boolean itemBlock)
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public StateMapperBase getCustomMapper()
    {
        return null;
    }

    @Override
    public boolean appendPropertiesToState() {
        return true;
    }

    protected <V extends Comparable<V>> IBlockState applyProperty(IBlockState in, IProperty<V> prop, Object val) {
        return in.withProperty(prop, (V)val);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if(state==null || !this.equals(state.getBlock()))
            return 0;
        return state.getValue(this.property).getMeta();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        for(int i=0; i<this.additionalProperties.length; i++)
            if(this.additionalProperties[i]!=null && !this.additionalProperties[i].getAllowedValues().isEmpty())
                state = applyProperty(state, this.additionalProperties[i], this.additionalProperties[i].getAllowedValues().toArray()[0]);
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        LogHelper.info("Getting state from meta. Property: " + this.property);
        IBlockState state = this.getDefaultState().withProperty(this.property, fromMeta(meta));
        for(int i=0; i<this.additionalProperties.length; i++)
            if(this.additionalProperties[i]!=null && !this.additionalProperties[i].getAllowedValues().isEmpty()) {
                state = applyProperty(state, this.additionalProperties[i], this.additionalProperties[i].getAllowedValues().toArray()[0]);
                LogHelper.info("additional props: " + this.additionalProperties[i]);
            }
        return state;
    }

    protected E fromMeta(int meta) {
        if(meta<0||meta>=enumValues.length)
            meta = 0;
        return enumValues[meta];
    }

    @Override
    protected BlockStateContainer createBlockState() {
        LogHelper.info("Creating new BlockState");
        if(this.property!=null)
            return createNotTempBlockState();
        if(tempUnlistedProperties.length>0)
            return new ExtendedBlockState(this, tempProperties, tempUnlistedProperties);
        return new BlockStateContainer(this, tempProperties);
    }

    protected BlockStateContainer createNotTempBlockState() {
        IProperty[] array = new IProperty[1+this.additionalProperties.length];
        array[0] = this.property;
        LogHelper.info("createnNotTempBlockState: " + array[0]);
        for(int i=0; i<this.additionalProperties.length; i++)
            array[1+i] = this.additionalProperties[i];
        if(this.additionalUnlistedProperties.length>0)
            return new ExtendedBlockState(this, array, additionalUnlistedProperties);
        return new BlockStateContainer(this, array);
    }

    protected IBlockState getInitDefaultState() {
        IBlockState state = this.blockState.getBaseState().withProperty(this.property, enumValues[0]);
        for(int i=0; i<this.additionalProperties.length; i++)
            if(this.additionalProperties[i]!=null && !this.additionalProperties[i].getAllowedValues().isEmpty())
                state = applyProperty(state, additionalProperties[i], additionalProperties[i].getAllowedValues().iterator().next());
        return state;
    }

}
