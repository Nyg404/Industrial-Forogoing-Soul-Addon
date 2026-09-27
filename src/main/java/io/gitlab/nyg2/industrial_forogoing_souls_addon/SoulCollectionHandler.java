package io.gitlab.nyg2.industrial_forogoing_souls_addon;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulData;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datageneratic.SoulDataMaps;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulsCapabilities;
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
        // Выполняем только на сервере
        if (event.getEntity().level().isClientSide()) return;

        LivingEntity victim = event.getEntity();

        // Проверяем, что убийца — это игрок
        if (event.getSource().getEntity() instanceof Player player) {

            // Узнаем, какая душа положена этому мобу через Data Map
            SoulData soulData = victim.getType().builtInRegistryHolder().getData(SoulDataMaps.ENTITY_SOULS);
            if (soulData == null) return; // У моба нет души

            // Проверяем весь инвентарь игрока на наличие предмета-контейнера душ
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);

                // Проверяем, есть ли у предмета наша capability для душ
                ISoulContainer soulContainer = stack.getCapability(SoulsCapabilities.SOUL_ITEM_HANDLER, null);
                if (soulContainer != null) {
                    // Пытаемся заполнить предмет душами
                    int filled = soulContainer.fill(soulData.soulType(), soulData.amount());

                    if (filled > 0) {
                        // Если успешно запихнули хоть сколько-то душ, выходим из цикла
                        // (можно убрать break, если хочешь заполнять сразу все фляги в инвентаре)
                        break;
                    }
                }
            }
        }
    }
}