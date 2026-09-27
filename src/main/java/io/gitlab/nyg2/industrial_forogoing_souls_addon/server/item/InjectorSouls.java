package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.item;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.Souls;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulsCapabilities;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.SoulContainerUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

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