package net.muellersites.advancedsteelmaking.common.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.muellersites.advancedsteelmaking.common.gui.ASSlot;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;

public class ContainerArcFurnace extends ContainerBase<TileEntityArcFurnace> {

    public ContainerArcFurnace(InventoryPlayer playerInv, TileEntityArcFurnace te) {
        super(playerInv, te);

        this.addSlotToContainer(new ASSlot.ArcElectrode(this, this.inv, 0, 62,4));
        this.addSlotToContainer(new ASSlot.ArcElectrode(this, this.inv, 1, 80,4));
        this.addSlotToContainer(new ASSlot.ArcElectrode(this, this.inv, 2, 98,4));

        this.addSlotToContainer(new ASSlot.ArcInput(this, this.inv, 3, 14,56));
        this.addSlotToContainer(new ASSlot.ArcAdditive(this, this.inv, 4, 38,56));
        this.addSlotToContainer(new ASSlot.Output(this, this.inv, 5, 134,56));

        slotCount=6;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(playerInv, j+i*9+9, 8+j*18, 106+i*18));
        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(playerInv, i, 8+i*18, 164));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return super.transferStackInSlot(player, slot);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.inv.setField(id, data);
    }

}

