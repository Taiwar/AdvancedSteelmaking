package net.muellersites.advancedsteelmaking.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
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
        // NO-OP
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.readCustomNBT(nbt, false);
    }
    public abstract void readCustomNBT(NBTTagCompound nbt, boolean descPacket);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
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
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return super.hasCapability(capability, facing);
    }
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return super.getCapability(capability, facing);
    }

}
