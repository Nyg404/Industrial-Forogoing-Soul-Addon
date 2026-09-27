package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulRegistries.SOULS_REGISTRY;

public class Souls {
    public static final DeferredRegister<Soul> SOULS = DeferredRegister.create(SOULS_REGISTRY, Industrial_forogoing_souls_addon.MODID);
    public static final DeferredHolder<Soul, Soul> NONE_SOUL = SOULS.register("none_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/none_soul.png")));
    public static final DeferredHolder<Soul, Soul> ZOMBIE_SOUL = SOULS.register("zombie_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/zombie_soul.png")));
    public static final DeferredHolder<Soul, Soul> SKELETON_SOUL = SOULS.register("skeleton_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/skeleton_soul.png")));
    public static final DeferredHolder<Soul, Soul> BEE_SOUL = SOULS.register("bee_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/bee_soul.png")));
    public static final DeferredHolder<Soul, Soul> COW_SOUL = SOULS.register("cow_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/cow_soul.png")));
    public static final DeferredHolder<Soul, Soul> PIG_SOUL = SOULS.register("pig_soul", () -> new Soul(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "textures/soul/pig_soul.png")));

    public static void register(IEventBus eventBus) {
        SOULS.register(eventBus);
    }
}
