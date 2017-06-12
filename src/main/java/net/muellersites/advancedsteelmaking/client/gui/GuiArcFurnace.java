package net.muellersites.advancedsteelmaking.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.muellersites.advancedsteelmaking.client.gui.container.ContainerArcFurnace;
import net.muellersites.advancedsteelmaking.reference.Textures;
import net.muellersites.advancedsteelmaking.tileentity.TileEntityArcFurnace;

import java.io.IOException;
import java.util.ArrayList;

public class GuiArcFurnace extends GuiContainer {

    private static final ResourceLocation TEXTURE = Textures.Gui.ARC_FURNACE;
    private TileEntityArcFurnace tile;

    public GuiArcFurnace(InventoryPlayer playerInv, TileEntityArcFurnace te) {
        super(new ContainerArcFurnace(playerInv, te));

        this.ySize = 186;
        this.tile = te;
    }

    @Override
    public final void initGui() {
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ArrayList<String> tooltip = new ArrayList<String>();
        if(mouseX>guiLeft+157&&mouseX<guiLeft+164 && mouseY>guiTop+22&&mouseY<guiTop+68)
            // Todo: remove dummy
            tooltip.add(5000+"/"+10000+" RF");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // Todo: remove dummy
        int stored = (int)(46*(5000/(float)10000));
        drawGradientRect(guiLeft+157,guiTop+22+(46-stored), guiLeft+164,guiTop+68, 0xffb51500, 0xff600b00);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // NO-OP
    }
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

}
