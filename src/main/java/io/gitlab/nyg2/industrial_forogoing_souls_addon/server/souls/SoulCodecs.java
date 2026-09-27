package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls;

import com.mojang.serialization.Codec;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulRegistries;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class SoulCodecs {

    public static final Codec<Holder<Soul>> SOUL = ResourceLocation.CODEC.xmap(
            location -> (Holder<Soul>) SoulRegistries.SOULS_REGISTRY.getHolder(
                    ResourceKey.create(SoulRegistries.SOUL_REGISTRY_KEY, location)
            ).orElseThrow(),
            holder -> holder.unwrapKey().map(ResourceKey::location).orElseThrow()
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Soul> SOUL_VALUE_STREAM =
            ResourceLocation.STREAM_CODEC.map(
                    Soul::new,
                    Soul::getTexture
            ).cast();


    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Soul>> SOUL_STREAM =
            ByteBufCodecs.holder(SoulRegistries.SOUL_REGISTRY_KEY, SOUL_VALUE_STREAM);
}