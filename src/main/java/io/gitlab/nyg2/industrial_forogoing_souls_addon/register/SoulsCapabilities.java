package io.gitlab.nyg2.industrial_forogoing_souls_addon.register;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.SoulCapabilities;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.item.SoulItemContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.block.SoulBlockRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SoulsCapabilities {

    public static final BlockCapability<ISoulContainer, Void> SOUL_HANDLER =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "soul_handler"),
                    ISoulContainer.class
            );

    public static final ItemCapability<ISoulContainer, Void> SOUL_ITEM_HANDLER =
            ItemCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Industrial_forogoing_souls_addon.MODID, "soul_handler"),
                    ISoulContainer.class
            );
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                SOUL_HANDLER,

                SoulBlockRegistry.SOUL_INFUSER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> new SoulCapabilities(blockEntity)
        );

        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                SoulBlockRegistry.SOUL_INFUSER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getEnergyStorage()
        );
        event.registerItem(
                SOUL_ITEM_HANDLER,
                (itemStack, context) -> new SoulItemContainer(itemStack),
                SoulBlockRegistry.INJECTOR_SOULS_ITEM.get()
        );
    }
}
