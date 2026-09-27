package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu;

import com.hrznstudio.titanium.container.addon.SlotContainerAddon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.block.SoulMachineBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SoulMachineMenu extends AbstractContainerMenu {

    private final SoulMachineBlockEntity<?> blockEntity;

    public SoulMachineMenu(int containerId, Inventory inventory, SoulMachineBlockEntity<?> blockEntity) {
        super(MenuRegistry.SOUL_MACHINE_MENU.get(), containerId);
        this.blockEntity = blockEntity;


        SlotContainerAddon addon = new SlotContainerAddon(
                blockEntity.getItemInventory(),
                blockEntity.getItemInventory().getXPos(),
                blockEntity.getItemInventory().getYPos(),
                blockEntity.getItemInventory().getSlotPosition()
        );

        for (Slot slot : addon.getSlots()) {
            addSlot(slot);
        }
// Инвентарь игрока — 3 × 9

// Инвентарь игрока — 3 × 9
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(
                        inventory,
                        col + row * 9 + 9,
                        62 + col * 18,
                        175 + row * 18
                ));
            }
        }

// Хотбар — 9
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(
                    inventory,
                    col,
                    65 + col * 18,
                    229
            ));
        }



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
        return true;
    }
}