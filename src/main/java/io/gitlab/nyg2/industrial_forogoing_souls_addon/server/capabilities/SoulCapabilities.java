package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.capabilities;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.block.SoulMachineBlockEntity;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.core.Holder;

public class SoulCapabilities implements ISoulContainer {
    private final SoulMachineBlockEntity<?> machine;

    public SoulCapabilities(SoulMachineBlockEntity<?> machine) {
        this.machine = machine;
    }

    @Override
    public int getStorageTypesCount() {
        return machine.getStorageSouls().size();
    }

    @Override
    public Holder<Soul> getSoulType(int index) {
        Holder<Soul>[] souls = machine.getStorageSouls().keySet().toArray(new Holder[0]);
        return index >= 0 && index < souls.length ? souls[index] : null;
    }

    @Override
    public int getMaxStorageTypes(int index) {
        return machine.getMaxSoulsTypes();
    }

    @Override
    public int fill(Holder<Soul> soulType, int amount) {
        return machine.addSouls(soulType, amount);
    }

    @Override
    public int drain(Holder<Soul> soulType, int amount) {
        if (soulType == null || amount <= 0) return 0;

        return machine.extractSouls(soulType, amount, false);
    }

    @Override
    public int getSoul(Holder<Soul> soulType) {
        return machine.getStorageSouls().getOrDefault(soulType, 0);
    }
}