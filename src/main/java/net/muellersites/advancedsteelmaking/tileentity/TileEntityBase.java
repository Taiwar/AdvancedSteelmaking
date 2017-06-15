package net.muellersites.advancedsteelmaking.tileentity;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.muellersites.advancedsteelmaking.AdvancedSteelmaking;
import net.muellersites.advancedsteelmaking.block.base.BlockBase;
import net.muellersites.advancedsteelmaking.init.ModTileEntities;
import net.muellersites.advancedsteelmaking.reference.Names;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TileEntityBase extends TileEntity implements ITickable {

    protected String customName;
    protected UUID owner;

    public TileEntityBase() {
        customName = "";
        owner = null;
        ModTileEntities.register(this);
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public void update() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readCustomNBT(nbt, false);
    }
    public abstract void readCustomNBT(NBTTagCompound nbt, boolean descPacket);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeCustomNBT(nbt, false);
        return nbt;
    }
    public abstract void writeCustomNBT(NBTTagCompound nbt, boolean descPacket);

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return super.getCapability(capability, facing);
    }

    public void markContainingBlockForUpdate(IBlockState newState)
    {
        markBlockForUpdate(getPos(), newState);
    }
    public void markBlockForUpdate(BlockPos pos, IBlockState newState) {
        IBlockState state = worldObj.getBlockState(getPos());
        if(newState==null)
            newState = state;
        worldObj.notifyBlockUpdate(pos,state,newState,3);
        worldObj.notifyNeighborsOfStateChange(pos, newState.getBlock());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeCustomNBT(nbttagcompound, true);
        return new SPacketUpdateTileEntity(this.pos, 3, nbttagcompound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = super.writeToNBT(new NBTTagCompound());
        writeCustomNBT(nbt, true);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readCustomNBT(pkt.getNbtCompound(), true);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if(id == 0 || id == 255) {
            markContainingBlockForUpdate(null);
            return true;
        }
        return super.receiveClientEvent(id, type);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        if (world.isBlockLoaded(pos))
            newState = world.getBlockState(pos);
        if (oldState.getBlock()!=newState.getBlock()||!(oldState.getBlock() instanceof BlockBase)||!(newState.getBlock() instanceof BlockBase))
            return true;
        IProperty type = ((BlockBase)oldState.getBlock()).getMetaProperty();
        return oldState.getValue(type) != newState.getValue(type);
    }

    public void receiveMessageFromClient(NBTTagCompound message) {
    }

    public void receiveMessageFromServer(NBTTagCompound message) {
    }

}
