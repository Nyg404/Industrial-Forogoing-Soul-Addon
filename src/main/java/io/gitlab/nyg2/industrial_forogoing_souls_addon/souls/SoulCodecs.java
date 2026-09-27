package io.gitlab.nyg2.industrial_forogoing_souls_addon.souls;

import com.mojang.serialization.Codec;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulRegistries;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class SoulCodecs {

    public static final Codec<Holder<Soul>> SOUL = ResourceLocation.CODEC.xmap(
            location -> (Holder<Soul>) SoulRegistries.SOULS_REGISTRY.getHolder(
                    ResourceKey.create(SoulRegistries.SOUL_REGISTRY_KEY, location)
            ).orElseThrow(),
            holder -> holder.unwrapKey().map(ResourceKey::location).orElseThrow()
    );
}