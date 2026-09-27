package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulMachineScreen extends AbstractContainerScreen<SoulMachineMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            Industrial_forogoing_souls_addon.MODID,
            "textures/gui/soul_machine.png"
    );

    public SoulMachineScreen(SoulMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        // Указываем точные размеры полезной графики (до координат 287 и 271)
        this.imageWidth = 288;
        this.imageHeight = 272;

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 0, 0 — это начало вашей графики (левый верхний угол файла)
        // this.imageWidth, this.imageHeight — сколько пикселей вырезать (288x272)
        // 512, 512 — реальный размер файла на диске после изменения холста
        guiGraphics.blit(
                TEXTURE,
                x, y,
                0, 0,
                this.imageWidth, this.imageHeight,
                512, 512
        );
    }
}
