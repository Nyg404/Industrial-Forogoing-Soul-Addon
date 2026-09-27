package io.gitlab.nyg2.industrial_forogoing_souls_addon.menu;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistry {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Industrial_forogoing_souls_addon.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulMachineMenu>> SOUL_MACHINE_MENU =
            MENUS.register("soul_machine", () ->
                    IMenuTypeExtension.create(SoulMachineMenu::new)
            );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}