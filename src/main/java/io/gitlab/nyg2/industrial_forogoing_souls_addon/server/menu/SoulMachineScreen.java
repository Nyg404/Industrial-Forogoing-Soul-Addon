package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulMachineScreen extends AbstractContainerScreen<SoulMachineMenu> {

    public SoulMachineScreen(SoulMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    Industrial_forogoing_souls_addon.MODID,
                    "textures/gui/soul_machine.png"
            );

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, 256, 256);
    }
}