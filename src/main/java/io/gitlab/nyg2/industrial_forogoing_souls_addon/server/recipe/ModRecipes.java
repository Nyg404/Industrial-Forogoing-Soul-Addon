package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.recipe;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.recipe.SoulInfuserRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Industrial_forogoing_souls_addon.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Industrial_forogoing_souls_addon.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SoulInfuserRecipe>> SOUL_INFUSER_TYPE =
            RECIPE_TYPES.register("soul_infusion", () -> new RecipeType<>() {
                @Override
                public String toString() { return "soul_infusion"; }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SoulInfuserRecipe>> SOUL_INFUSER_SERIALIZER =
            RECIPE_SERIALIZERS.register("soul_infusion", () -> new RecipeSerializer<SoulInfuserRecipe>() {
                @Override
                public com.mojang.serialization.MapCodec<SoulInfuserRecipe> codec() {
                    return SoulInfuserRecipe.CODEC;
                }

                @Override
                public net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, SoulInfuserRecipe> streamCodec() {
                    return SoulInfuserRecipe.STREAM_CODEC;
                }
            });

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
