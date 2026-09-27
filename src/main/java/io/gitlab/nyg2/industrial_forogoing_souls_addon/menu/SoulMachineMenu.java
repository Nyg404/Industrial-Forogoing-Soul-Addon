package io.gitlab.nyg2.industrial_forogoing_souls_addon.menu;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.block.SoulMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SoulMachineMenu extends AbstractContainerMenu {

    private final SoulMachineBlockEntity<?> blockEntity;

    public SoulMachineMenu(int containerId, Inventory inventory, SoulMachineBlockEntity<?> blockEntity) {
        super(MenuRegistry.SOUL_MACHINE_MENU.get(), containerId);
        this.blockEntity = blockEntity;
    }

    public SoulMachineMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buffer) {
        this(
                containerId,
                inventory,
                (SoulMachineBlockEntity<?>) inventory.player.level().getBlockEntity(buffer.readBlockPos())
        );
    }
    public SoulMachineBlockEntity<?> getBlockEntity() {
        return blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}