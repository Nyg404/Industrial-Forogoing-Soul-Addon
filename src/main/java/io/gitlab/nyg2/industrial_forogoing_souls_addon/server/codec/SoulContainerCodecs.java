package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.codec;

import com.mojang.serialization.Codec;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datacomponents.SoulData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public class SoulContainerCodecs {

    public static final Codec<List<SoulData>> CODEC_LIST = SoulData.CODEC.listOf();

    public static final StreamCodec<RegistryFriendlyByteBuf, List<SoulData>> STREAM_CODEC_LIST =
            ByteBufCodecs.collection(ArrayList::new, SoulData.STREAM_CODEC);
}