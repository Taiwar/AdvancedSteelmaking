package net.muellersites.advancedsteelmaking.item.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;
import net.muellersites.advancedsteelmaking.util.LogHelper;

import java.util.List;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block b) {
        super(b);
        if(((BlockBase)b).enumValues.length>1)
            setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int damageValue)
    {
        return damageValue;
    }
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> itemList)
    {
        this.block.getSubBlocks(item, tab, itemList);
    }
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ((BlockBase) this.block).getUnlocalizedName(stack);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if(!((BlockBase)this.block).canASBlockBePlaced(world, pos, newState, side, hitX,hitY,hitZ, player, stack))
            return false;
        boolean ret = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if(ret)
        {
            ((BlockBase)this.block).onASBlockPlacedBy(world, pos, newState, side, hitX,hitY,hitZ, player, stack);
        }
        return ret;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        LogHelper.info("onItemUse");
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();
        if (!block.isReplaceable(world, pos))
            pos = pos.offset(side);
        if(stack.stackSize > 0 && player.canPlayerEdit(pos, side, stack)) {
            int i = this.getMetadata(stack.getMetadata());
            IBlockState iblockstate_new = this.block.onBlockPlaced(world, pos, side, hitX, hitY, hitZ, i, player);
            if(placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, iblockstate_new)) {
                SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                if(!player.capabilities.isCreativeMode)
                    --stack.stackSize;
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}
