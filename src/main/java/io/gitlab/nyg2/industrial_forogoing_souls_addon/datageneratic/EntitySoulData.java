package io.gitlab.nyg2.industrial_forogoing_souls_addon.datageneratic;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SoulDataMaps {
    public static final DataMapType<net.minecraft.world.entity.EntityType<?>, SoulData> ENTITY_SOULS =
            DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "entity_souls"),
                    Registries.ENTITY_TYPE,
                    SoulData.CODEC
            ).build();

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ENTITY_SOULS);
    }
}
