package io.gitlab.nyg2.industrial_forogoing_souls_addon.item;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.block.SoulMachineBlockEntity;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulData;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.Souls;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.souls.Soul;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulDataComponents;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulsCapabilities;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulRegistries;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.souls.SoulContainerUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;

public class InjectorSouls extends Item {
    public InjectorSouls(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, TooltipContext p_339594_, List<Component> p_41423_, TooltipFlag p_41424_) {


        super.appendHoverText(p_41421_, p_339594_, p_41423_, p_41424_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack stack = context.getItemInHand();

        ISoulContainer itemContainer = stack.getCapability(SoulsCapabilities.SOUL_ITEM_HANDLER);
        if (itemContainer == null) return InteractionResult.PASS;

        ISoulContainer machineContainer = level.getCapability(
                SoulsCapabilities.SOUL_HANDLER,
                context.getClickedPos()
        );
        if (machineContainer == null) return InteractionResult.PASS;

        Holder<Soul> soul = Souls.ZOMBIE_SOUL;

        return SoulContainerUtils.transfer(itemContainer, machineContainer, soul, 100)
                ? InteractionResult.SUCCESS
                : InteractionResult.PASS;
    }


}