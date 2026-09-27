package io.gitlab.nyg2.industrial_forogoing_souls_addon.register;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.Souls.SoulType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

import static io.gitlab.nyg2.industrial_forogoing_souls_addon.register.key.SoulRegistries.SOULS_REGISTRY;

public class Souls {
    public static final DeferredRegister<SoulType> SOULS = DeferredRegister.create(SOULS_REGISTRY, Industrial_forogoing_souls_addon.MODID);
    public static final Supplier<SoulType> NONE_SOUL = SOULS.register("none_soul", () -> new SoulType(0));
    public static final DeferredHolder<SoulType, SoulType> ZOMBIE_SOUL = SOULS.register("zombie_soul", () -> new SoulType(1));
    public static final DeferredHolder<SoulType, SoulType> SKELETON_SOUL = SOULS.register("skeleton_soul", () -> new SoulType(1));
    public static final DeferredHolder<SoulType, SoulType> BEE_SOUL = SOULS.register("bee_soul", () -> new SoulType(1));
    public static final DeferredHolder<SoulType, SoulType> COW_SOUL = SOULS.register("cow_soul", () -> new SoulType(1));
    public static final DeferredHolder<SoulType, SoulType> PIG_SOUL = SOULS.register("pig_soul", () -> new SoulType(1));
    public static void register(IEventBus eventBus) {
        SOULS.register(eventBus);
    }
}
