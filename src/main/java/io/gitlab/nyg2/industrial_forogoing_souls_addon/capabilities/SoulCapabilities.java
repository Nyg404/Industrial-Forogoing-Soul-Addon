package io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Souls.SoulType;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.block.SoulMachineBlockEntity;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulDataComponents;
import net.minecraft.core.Holder;

import java.util.HashMap;
import java.util.Map;

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
    public Holder<SoulType> getSoulType(int index) {
        var key = machine.getStorageSouls().keySet().toArray(new Holder[0]);
        if(index >= 0 && index < key.length){
            return key[index];
        }
        return null;

    }

    @Override
    public int getMaxStorageTypes(int index) {
        Holder<SoulType> holder = getSoulType(index);
        if(holder != null){
            return machine.getStorageSouls().getOrDefault(holder, 0);
        }
        return 0;
    }

    @Override
    public int fill(Holder<SoulType> soulType, int amount) {
        Map<Holder<SoulType>, Integer> map = machine.getStorageSouls();
        if(!map.containsKey(soulType) && map.size() >= machine.getMaxSoulsTypes()){
            return 0;
        }

        if(Action.EXECUTE.execute()){
            machine.addSouls(soulType, amount);
        }

        return amount;
    }

    @Override
    public int drain(Holder<SoulType> soulType, int amount) {
        boolean simulate = !Action.EXECUTE.execute();
        return machine.extractSouls(soulType, amount, simulate);
    }

    @Override
    public int getSoul(Holder<SoulType> soulType) {
        return machine.getStorageSouls().getOrDefault(soulType, 0);
    }


}
