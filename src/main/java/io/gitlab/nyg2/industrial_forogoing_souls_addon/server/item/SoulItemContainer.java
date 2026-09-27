package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.item;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datacomponents.SoulData;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.datacomponents.SoulDataComponents;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SoulItemContainer implements ISoulContainer {
    private final ItemStack itemStack;
    private final int maxCapacity = 10000;

    public SoulItemContainer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    private List<SoulData> getData() {
        List<SoulData> data = itemStack.get(SoulDataComponents.SOULS);
        return data != null ? new ArrayList<>(data) : new ArrayList<>();
    }

    private void saveData(List<SoulData> data) {
        if (data.isEmpty()) {
            itemStack.remove(SoulDataComponents.SOULS);
        } else {
            itemStack.set(SoulDataComponents.SOULS, data);
        }
    }

    private SoulData find(List<SoulData> data, Holder<Soul> soul) {
        for (SoulData soulData : data) {
            if (soulData.soulType().equals(soul)) {
                return soulData;
            }
        }
        return null;
    }

    @Override
    public int getStorageTypesCount() {
        return getData().size();
    }

    @Override
    public Holder<Soul> getSoulType(int index) {
        List<SoulData> data = getData();
        return index >= 0 && index < data.size() ? data.get(index).soulType() : null;
    }

    @Override
    public int getMaxStorageTypes(int index) {
        return maxCapacity;
    }

    @Override
    public int fill(Holder<Soul> soul, int amount) {
        if (amount <= 0 || soul == null) return 0;

        List<SoulData> data = getData();
        SoulData existing = find(data, soul);

        if (existing == null) {
            int toAdd = Math.min(amount, maxCapacity);
            data.add(new SoulData(soul, toAdd));
            saveData(data);
            return toAdd;
        }

        int space = maxCapacity - existing.amount();
        int toAdd = Math.min(amount, space);

        if (toAdd > 0) {
            data.set(data.indexOf(existing), new SoulData(soul, existing.amount() + toAdd));
            saveData(data);
        }

        return toAdd;
    }

    @Override
    public int drain(Holder<Soul> soul, int amount) {
        if (amount <= 0 || soul == null) return 0;

        List<SoulData> data = getData();
        SoulData existing = find(data, soul);

        if (existing == null) return 0;

        int toExtract = Math.min(amount, existing.amount());
        int remaining = existing.amount() - toExtract;

        if (remaining <= 0) {
            data.remove(existing);
        } else {
            data.set(data.indexOf(existing), new SoulData(soul, remaining));
        }

        saveData(data);
        return toExtract;
    }

    @Override
    public int getSoul(Holder<Soul> soul) {
        if (soul == null) return 0;

        SoulData existing = find(getData(), soul);
        return existing != null ? existing.amount() : 0;
    }
}