package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.recipe;



import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.codec.SoulContainerCodecs;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.Souls;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.SoulCodecs;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulRegistries;

public class SoulInfuserRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient inputItem;
    private final Holder<Soul> requiredSoul;
    private final int soulAmount;
    private final ItemStack outputItem;

    public SoulInfuserRecipe(Ingredient inputItem, Holder<Soul> requiredSoul, int soulAmount, ItemStack outputItem) {
        this.inputItem = inputItem;
        this.requiredSoul = requiredSoul;
        this.soulAmount = soulAmount;
        this.outputItem = outputItem;
    }

    // КОДЕК ДЛЯ СЕРИАЛИЗАЦИИ 1.21.1 (Чтение/запись JSON)
    public static final MapCodec<SoulInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("input").forGetter(r -> r.inputItem),
            SoulCodecs.SOUL.fieldOf("soul").forGetter(r -> r.requiredSoul),
            Codec.INT.fieldOf("soul_amount").forGetter(r -> r.soulAmount),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.outputItem)
    ).apply(instance, SoulInfuserRecipe::new));


    public static final StreamCodec<RegistryFriendlyByteBuf, SoulInfuserRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, r -> r.inputItem,
            ByteBufCodecs.holder(SoulRegistries.SOUL_REGISTRY_KEY, SoulCodecs.SOUL_VALUE_STREAM), r -> r.requiredSoul,
            ByteBufCodecs.INT, r -> r.soulAmount,
            ItemStack.STREAM_CODEC, r -> r.outputItem,
            SoulInfuserRecipe::new
    );

    // Геттеры
    public Ingredient getInputItem() { return inputItem; }
    public Holder<Soul> getRequiredSoul() { return requiredSoul; }
    public int getSoulAmount() { return soulAmount; }


    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.inputItem.test(singleRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput singleRecipeInput, HolderLookup.Provider provider) {
        return this.outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) { return this.outputItem; }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.SOUL_INFUSER_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return ModRecipes.SOUL_INFUSER_TYPE.get(); }
}
