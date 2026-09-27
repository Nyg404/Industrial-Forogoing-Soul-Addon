package io.gitlab.nyg2.industrial_forogoing_souls_addon.client.screen;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu.MenuRegistry;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu.SoulMachineScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.SOUL_MACHINE_MENU.get(), SoulMachineScreen::new);
    }
}