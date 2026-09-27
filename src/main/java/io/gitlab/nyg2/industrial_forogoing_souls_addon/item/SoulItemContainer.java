package io.gitlab.nyg2.industrial_forogoing_souls_addon.item;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Souls.SoulType;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulDataComponents;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.key.SoulRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SoulItemContainer implements ISoulContainer {
    private final ItemStack itemStack;
    private final int maxCapacity = 10000;

    public SoulItemContainer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    private Map<String, Integer> getMap() {
        Map<String, Integer> map = itemStack.get(SoulDataComponents.SOULS);

        return map != null ? new java.util.LinkedHashMap<>(map) : new java.util.LinkedHashMap<>();
    }


    private void saveMap(Map<String, Integer> map) {
        if (map.isEmpty()) {
            itemStack.remove(SoulDataComponents.SOULS);
        } else {
            itemStack.set(SoulDataComponents.SOULS, map);
        }
    }

    private String getSoulKey(Holder<SoulType> soulType) {
        if (soulType == null) return null;
        return soulType.unwrapKey().map(key -> key.location().toString()).orElse(null);
    }

    @Override
    public int getStorageTypesCount() {
        return getMap().size();
    }

    @Override
    public Holder<SoulType> getSoulType(int index) {
        Map<String, Integer> map = getMap();
        String[] keys = map.keySet().toArray(new String[0]);
        if (index >= 0 && index < keys.length) {
            ResourceLocation loc = ResourceLocation.tryParse(keys[index]);
            if (loc != null) {
                return SoulRegistries.SOULS_REGISTRY.getHolder(
                        ResourceKey.create(SoulRegistries.SOUL_TYPE_REGISTRY_KEY, loc)
                ).orElse(null);
            }
        }
        return null;
    }

    @Override
    public int getMaxStorageTypes(int index) {
        return maxCapacity;
    }

    @Override
    public int fill(Holder<SoulType> soulType, int amount) {
        String soulKey = getSoulKey(soulType);
        if (amount <= 0 || soulKey == null) return 0;

        Map<String, Integer> map = getMap();
        int current = map.getOrDefault(soulKey, 0);
        int space = maxCapacity - current;
        int toAdd = Math.min(amount, space);

        if (toAdd > 0) {
            map.put(soulKey, current + toAdd);
            saveMap(map);
        }

        return toAdd;
    }

    @Override
    public int drain(Holder<SoulType> soulType, int amount) {
        String soulKey = getSoulKey(soulType);
        if (amount <= 0 || soulKey == null) return 0;

        Map<String, Integer> map = getMap();
        int current = map.getOrDefault(soulKey, 0);
        if (current <= 0) return 0;

        int toExtract = Math.min(current, amount);
        int leftover = current - toExtract;

        if (leftover <= 0) {
            map.remove(soulKey);
        } else {
            map.put(soulKey, leftover);
        }

        saveMap(map);
        return toExtract;
    }

    @Override
    public int getSoul(Holder<SoulType> soulType) {
        String soulKey = getSoulKey(soulType);
        if (soulKey == null) return 0;
        Map<String, Integer> map = getMap();

        return map.getOrDefault(soulKey, 0);
    }


}