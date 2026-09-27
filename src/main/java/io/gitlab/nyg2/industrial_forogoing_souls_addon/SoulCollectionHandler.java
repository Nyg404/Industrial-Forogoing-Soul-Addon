package io.gitlab.nyg2.industrial_forogoing_souls_addon;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datageneratic.EntitySoulData;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulsCapabilities;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID)
public class SoulCollectionHandler {

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        LivingEntity victim = event.getEntity();

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        Holder<Soul> soul = victim.getType()
                .builtInRegistryHolder()
                .getData(EntitySoulData.ENTITY_SOULS);

        if (soul == null) {
            return;
        }

        int amount = calculateSoulAmount(victim);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            ISoulContainer soulContainer = stack.getCapability(SoulsCapabilities.SOUL_ITEM_HANDLER, null);

            if (soulContainer == null) {
                continue;
            }

            int filled = soulContainer.fill(soul, amount);

            if (filled > 0) {
                break;
            }
        }
    }

    private static int calculateSoulAmount(LivingEntity entity) {
        return 100;
    }
}