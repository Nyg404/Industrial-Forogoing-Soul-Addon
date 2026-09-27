package io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.Souls.SoulType;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.key.SoulRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record SoulData(Holder<SoulType> soulType, int amount) {
    public static final Codec<SoulData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.xmap(
                            loc -> (Holder<SoulType>) SoulRegistries.SOULS_REGISTRY.getHolder(
                                    ResourceKey.create(SoulRegistries.SOUL_TYPE_REGISTRY_KEY, loc)
                            ).orElse(null),
                            holder -> holder.unwrapKey().map(ResourceKey::location).orElse(ResourceLocation.withDefaultNamespace("empty"))
                    ).fieldOf("soul_type").forGetter(SoulData::soulType),

                    Codec.INT.fieldOf("amount").forGetter(SoulData::amount)
            ).apply(instance, SoulData::new)
    );
}