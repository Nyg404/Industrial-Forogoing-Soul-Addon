package io.gitlab.nyg2.industrial_forogoing_souls_addon.register;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.souls.Soul;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.gitlab.nyg2.industrial_forogoing_souls_addon.register.key.SoulRegistries.SOULS_REGISTRY;

public class SoulsType {
    public static final DeferredRegister<Soul> SOULS = DeferredRegister.create(SOULS_REGISTRY, Industrial_forogoing_souls_addon.MODID);
    public static final Supplier<Soul> NONE_SOUL = SOULS.register("none_soul", () -> new Soul(0));
    public static final DeferredHolder<Soul, Soul> ZOMBIE_SOUL = SOULS.register("zombie_soul", () -> new Soul(1));
    public static final DeferredHolder<Soul, Soul> SKELETON_SOUL = SOULS.register("skeleton_soul", () -> new Soul(1));
    public static final DeferredHolder<Soul, Soul> BEE_SOUL = SOULS.register("bee_soul", () -> new Soul(1));
    public static final DeferredHolder<Soul, Soul> COW_SOUL = SOULS.register("cow_soul", () -> new Soul(1));
    public static final DeferredHolder<Soul, Soul> PIG_SOUL = SOULS.register("pig_soul", () -> new Soul(1));
    public static void register(IEventBus eventBus) {
        SOULS.register(eventBus);
    }
}
