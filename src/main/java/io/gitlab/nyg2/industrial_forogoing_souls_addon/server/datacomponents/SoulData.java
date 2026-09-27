package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datacomponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulRegistries;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.SoulCodecs;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SoulData(Holder<Soul> soulType, int amount) {


    public static final Codec<SoulData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    SoulCodecs.SOUL.fieldOf("soul_type").forGetter(SoulData::soulType),
                    Codec.INT.fieldOf("amount").forGetter(SoulData::amount)
            ).apply(instance, SoulData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SoulData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.holderRegistry(SoulRegistries.SOUL_REGISTRY_KEY),
                    SoulData::soulType,
                    ByteBufCodecs.INT,
                    SoulData::amount,
                    SoulData::new
            );
}