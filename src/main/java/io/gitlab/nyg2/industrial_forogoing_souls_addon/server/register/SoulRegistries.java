package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SoulRegistries {

    public static final ResourceKey<Registry<Soul>> SOUL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "soul_types"));
    public static final Registry<Soul> SOULS_REGISTRY = new RegistryBuilder<>(SOUL_REGISTRY_KEY)
            .sync(true)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "soul_empty"))
            .create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event){
        event.register(SOULS_REGISTRY);
    }
}
