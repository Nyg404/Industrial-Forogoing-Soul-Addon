package io.gitlab.nyg2.industrial_forogoing_souls_addon.item;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Souls.SoulType;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulDataComponents;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulsCapabilities;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.key.SoulRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InjectorSouls extends Item {
    public InjectorSouls(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, List<Component> p_41423_, TooltipFlag p_41424_) {
        Map<String, Integer> map = p_41421_.get(SoulDataComponents.SOULS.get());

        // 1. Проверяем, есть ли вообще карта в компонентах предмета
        if (map != null && !map.isEmpty()) {
            if (Screen.hasControlDown()) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    String name = entry.getKey();
                    int value = entry.getValue();

                    p_41423_.add(Component.literal("Душа: ").withStyle(ChatFormatting.GRAY)
                            .append(Component.literal(name).withStyle(ChatFormatting.AQUA)));

                    p_41423_.add(Component.literal("Ёмкость: ").withStyle(ChatFormatting.GRAY)
                            .append(Component.literal(String.valueOf(value)).withStyle(ChatFormatting.GOLD)));
                }
            } else {
                // Подсказка для игрока, если данные есть, но Ctrl не зажат
                p_41423_.add(Component.literal("Зажми [Ctrl] для просмотра душ").withStyle(ChatFormatting.DARK_GRAY));
            }
        } else {
            // 2. Если карты нет (предмет пустой или в Креативе)
            p_41423_.add(Component.literal("Сосуд пуст").withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(p_41421_, p_339594_, p_41423_, p_41424_);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = context.getItemInHand();

        ISoulContainer itemContainer = stack.getCapability(SoulsCapabilities.SOUL_ITEM_HANDLER);
        if (itemContainer == null) return InteractionResult.PASS;

        ISoulContainer blockContainer = level.getCapability(SoulsCapabilities.SOUL_HANDLER, context.getClickedPos());
        if (blockContainer == null) return InteractionResult.PASS;

        // 1. ЧИТАЕМ КАРТУ НАПРЯМУЮ ИЗ КОМПОНЕНТА ПРЕДМЕТА (БЕЗ ИНДЕКСОВ!)
        Map<String, Integer> componentMap = stack.get(SoulDataComponents.SOULS.get());
        if (componentMap == null || componentMap.isEmpty()) {
            return InteractionResult.PASS;
        }

        // Делаем копию, чтобы спокойно итерироваться и не поймать ConcurrentModificationException
        Map<String, Integer> soulsToMove = new java.util.HashMap<>(componentMap);

        // 2. ПЕРЕЛИВАЕМ ДУШИ
        for (Map.Entry<String, Integer> entry : soulsToMove.entrySet()) {
            String soulKeyStr = entry.getKey();
            int amountInItem = entry.getValue();

            if (amountInItem <= 0) continue;

            // Превращаем строковый ключ обратно в Holder<SoulType> для машины
            net.minecraft.resources.ResourceLocation loc = net.minecraft.resources.ResourceLocation.tryParse(soulKeyStr);
            if (loc == null) continue;

            var soulOpt = SoulRegistries.SOULS_REGISTRY.getHolder(
                    net.minecraft.resources.ResourceKey.create(SoulRegistries.SOUL_TYPE_REGISTRY_KEY, loc)
            );

            if (soulOpt.isPresent()) {
                Holder<SoulType> soulTypeHolder = soulOpt.get();

                // Сколько машина РЕАЛЬНО может принять?
                int insert = blockContainer.fill(soulTypeHolder, amountInItem);

                if (insert > 0) {
                    // Списываем из предмета ровно столько, сколько ушло
                    itemContainer.drain(soulTypeHolder, insert);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }


}