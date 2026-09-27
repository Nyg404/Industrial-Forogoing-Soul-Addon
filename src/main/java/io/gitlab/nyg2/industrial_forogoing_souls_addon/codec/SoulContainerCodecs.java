package io.gitlab.nyg2.industrial_forogoing_souls_addon.codec;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

public class SoulContainerCodecs {
    public static final Codec<Map<String, Integer>> CODEC_MAP =
            Codec.unboundedMap(Codec.STRING, Codec.INT);

    public static final StreamCodec<ByteBuf, Map<String, Integer>> STREAM_CODEC_MAP =
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT);
}
