package net.muellersites.advancedsteelmaking.block.base;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.muellersites.advancedsteelmaking.api.ASProperties;
import net.muellersites.advancedsteelmaking.item.base.ItemBlockBase;
import net.muellersites.advancedsteelmaking.proxy.CommonProxy;
import net.muellersites.advancedsteelmaking.util.ASInventoryHandler;
import net.muellersites.advancedsteelmaking.util.IASInventory;
import net.muellersites.advancedsteelmaking.block.base.BlockInterfaces.IDirectionalTile;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import java.util.List;

public abstract class BlockTileEntityProvider<E extends Enum<E> & BlockBase.IBlockEnum> extends BlockBase<E> implements ITileEntityProvider {

    private boolean hasColours = false;

    public BlockTileEntityProvider(String name, Material material, PropertyEnum<E> mainProperty, Class<? extends ItemBlockBase> itemBlock, Object... additionalProperties) {
        super(name, material, mainProperty, itemBlock, additionalProperties);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && ( !(tile instanceof BlockInterfaces.ITileDrop) || !((BlockInterfaces.ITileDrop)tile).preventInventoryDrop())) {
            if(tile instanceof IASInventory && ((IASInventory)tile).getDroppedItems()!=null) {
                for(ItemStack s : ((IASInventory)tile).getDroppedItems())
                    if(s!=null)
                        spawnAsEntity(world, pos, s);
            } else if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                IItemHandler h = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if(h instanceof ASInventoryHandler)
                    for(int i = 0; i < h.getSlots(); i++)
                        if(h.getStackInSlot(i)!=null)
                        {
                            spawnAsEntity(world, pos, h.getStackInSlot(i));
                            ((ASInventoryHandler)h).setStackInSlot(i, null);
                        }
            }
        }
        if(tile instanceof BlockInterfaces.IHasDummyBlocks) {
            ((BlockInterfaces.IHasDummyBlocks)tile).breakDummies(pos, state);
        }

        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }

    protected EnumFacing getDefaultFacing()
    {
        return EnumFacing.NORTH;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = super.getActualState(state, world, pos);
        LogHelper.info("getActualState");
        TileEntity tile = world.getTileEntity(pos);

        if(tile instanceof IDirectionalTile && (state.getPropertyNames().contains(ASProperties.FACING_ALL) || state.getPropertyNames().contains(ASProperties.FACING_HORIZONTAL))) {
            PropertyDirection prop = state.getPropertyNames().contains(ASProperties.FACING_HORIZONTAL)?ASProperties.FACING_HORIZONTAL: ASProperties.FACING_ALL;
            LogHelper.info("prop: " + prop);
            LogHelper.info("state prop names: " + state.getPropertyNames());
            if (prop == ASProperties.FACING_ALL) {
                LogHelper.info("well shit");
            } else {
                LogHelper.info("Yay");
                state = applyProperty(state, prop, ((IDirectionalTile)tile).getFacing());
            }
        }
        else if(state.getPropertyNames().contains(ASProperties.FACING_HORIZONTAL))
            state = state.withProperty(ASProperties.FACING_HORIZONTAL, getDefaultFacing());
        else if(state.getPropertyNames().contains(ASProperties.FACING_ALL))
            state = state.withProperty(ASProperties.FACING_ALL, getDefaultFacing());

        if(tile instanceof BlockInterfaces.IActiveState) {
            IProperty boolProp = ((BlockInterfaces.IActiveState) tile).getBoolProperty(BlockInterfaces.IActiveState.class);
            if(state.getPropertyNames().contains(boolProp))
                state = applyProperty(state, boolProp, ((BlockInterfaces.IActiveState) tile).getIsActive());
        }

        if(tile instanceof BlockInterfaces.IDualState) {
            IProperty boolProp = ((BlockInterfaces.IDualState) tile).getBoolProperty(BlockInterfaces.IDualState.class);
            if(state.getPropertyNames().contains(boolProp))
                state = applyProperty(state, boolProp, ((BlockInterfaces.IDualState) tile).getIsSecondState());
        }

        return state;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof IDirectionalTile) {
            if(!((IDirectionalTile)tile).canRotate(axis))
                return false;
            IBlockState state = world.getBlockState(pos);
            if(state.getPropertyNames().contains(ASProperties.FACING_ALL) || state.getPropertyNames().contains(ASProperties.FACING_HORIZONTAL)) {
                PropertyDirection prop = state.getPropertyNames().contains(ASProperties.FACING_HORIZONTAL)?ASProperties.FACING_HORIZONTAL: ASProperties.FACING_ALL;
                EnumFacing f = ((IDirectionalTile)tile).getFacing();
                int limit = ((IDirectionalTile)tile).getFacingLimitation();

                if(limit==0)
                    f = EnumFacing.VALUES[(f.ordinal() + 1) % EnumFacing.VALUES.length];
                else if(limit==1)
                    f = axis.getAxisDirection()== EnumFacing.AxisDirection.POSITIVE?f.rotateAround(axis.getAxis()).getOpposite():f.rotateAround(axis.getAxis());
                else if(limit == 2 || limit == 5)
                    f = axis.getAxisDirection()== EnumFacing.AxisDirection.POSITIVE?f.rotateY():f.rotateYCCW();
                if(f != ((IDirectionalTile)tile).getFacing())
                {
                    EnumFacing old = ((IDirectionalTile)tile).getFacing();
                    ((IDirectionalTile)tile).setFacing(f);
                    ((IDirectionalTile)tile).afterRotation(old,f);
                    state = applyProperty(state, prop, ((IDirectionalTile)tile).getFacing());
                    world.setBlockState(pos, state.cycleProperty(prop));
                }
            }
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);

        if(tile instanceof BlockInterfaces.IPlayerInteraction) {
            boolean b = ((BlockInterfaces.IPlayerInteraction)tile).interact(side, player, hand, heldItem, hitX, hitY, hitZ);
            if(b)
                return b;
        }
        if(tile instanceof BlockInterfaces.IGuiTile && hand == EnumHand.MAIN_HAND && !player.isSneaking()) {
            TileEntity master = ((BlockInterfaces.IGuiTile)tile).getGuiMaster();
            if(!world.isRemote && master!=null && ((BlockInterfaces.IGuiTile)master).canOpenGui(player))
                CommonProxy.openGuiForTile(player,(TileEntity & BlockInterfaces.IGuiTile)master);
            return true;
        }
        return false;
    }

    @Override
    public void onASBlockPlacedBy(World world, BlockPos pos, IBlockState state, EnumFacing side, float hitX, float hitY, float hitZ, EntityLivingBase placer, ItemStack stack) {
        LogHelper.info("onAsBlockPlacedBy: " + placer);
        TileEntity tile = world.getTileEntity(pos);

        if(tile instanceof IDirectionalTile)
        {
            EnumFacing f = ((IDirectionalTile)tile).getFacingForPlacement(placer, pos, side, hitX, hitY, hitZ);
            ((IDirectionalTile)tile).setFacing(f);
            if(tile instanceof BlockInterfaces.IAdvancedDirectionalTile)
                ((BlockInterfaces.IAdvancedDirectionalTile)tile).onDirectionalPlacement(side, hitX, hitY, hitZ, placer);
        }
        if(tile instanceof BlockInterfaces.IHasDummyBlocks)
        {
            ((BlockInterfaces.IHasDummyBlocks)tile).placeDummies(pos, state, side, hitX, hitY, hitZ);
        }
        if(tile instanceof BlockInterfaces.ITileDrop)
        {
            ((BlockInterfaces.ITileDrop)tile).readOnPlacement(placer, stack);
        }
    }

}
