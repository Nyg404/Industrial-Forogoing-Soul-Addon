package io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.codec.SoulContainerCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class SoulDataComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Industrial_forogoing_souls_addon.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Map<String, Integer>>> SOULS =
            REGISTER.registerComponentType(
                    "souls",
                    builder -> builder
                            .persistent(SoulContainerCodecs.CODEC_MAP)
                            .networkSynchronized(SoulContainerCodecs.STREAM_CODEC_MAP)
            );
}
