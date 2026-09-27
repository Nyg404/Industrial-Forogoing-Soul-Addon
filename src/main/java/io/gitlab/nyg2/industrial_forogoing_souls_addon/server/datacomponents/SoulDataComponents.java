package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datacomponents;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.codec.SoulContainerCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class SoulDataComponents {

    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Industrial_forogoing_souls_addon.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<SoulData>>> SOULS =
            REGISTER.registerComponentType("souls",
                    builder -> builder
                            .persistent(SoulContainerCodecs.CODEC_LIST)
                            .networkSynchronized(SoulContainerCodecs.STREAM_CODEC_LIST)
            );
}