package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datageneratic;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.SoulCodecs;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntitySoulData {
    public static final DataMapType<EntityType<?>, Holder<Soul>> ENTITY_SOULS =
            DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(MODID, "entity_souls"),
                    Registries.ENTITY_TYPE,
                    SoulCodecs.SOUL
            ).build();

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ENTITY_SOULS);
    }
}
