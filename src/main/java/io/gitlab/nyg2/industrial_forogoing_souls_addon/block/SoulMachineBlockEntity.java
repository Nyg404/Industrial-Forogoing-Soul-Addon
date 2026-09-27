package io.gitlab.nyg2.industrial_forogoing_souls_addon.block;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.hrznstudio.titanium.module.BlockWithTile;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.SoulMachineTier;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.SoulRegistries;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.souls.Soul;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public abstract class SoulMachineBlockEntity<T extends SoulMachineBlockEntity<T>> extends IndustrialMachineTile<T> {
    protected SoulMachineTier tier;

    protected final Map<Holder<Soul>, Integer> storageSouls = new HashMap<>();

    public SoulMachineBlockEntity(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState, SoulMachineTier tier) {
        super(basicTileBlock, blockPos, blockState);
        this.tier = tier;
    }

    public int getMaxSoulsTypes() {
        return this.tier.getMaxSouls();
    }

    public Map<Holder<Soul>, Integer> getStorageSouls() {
        return this.storageSouls;
    }

    public int addSouls(Holder<Soul> soulType, int amount) {
        if (amount <= 0) {
            return 0;
        }

        int current = storageSouls.getOrDefault(soulType, 0);

        if (!storageSouls.containsKey(soulType) && storageSouls.size() >= getMaxSoulsTypes()) {
            return 0;
        }

        int space = tier.getMaxSoulCapacity() - current;

        if (space <= 0) {
            return 0;
        }

        int toAdd = Math.min(amount, space);

        storageSouls.put(soulType, current + toAdd);
        markForUpdate();

        return toAdd;
    }

    public int extractSouls(Holder<Soul> soulType, int amount, boolean simulate) {
        if (!storageSouls.containsKey(soulType)) return 0;

        int currentAmount = storageSouls.get(soulType);
        int toExtract = Math.min(currentAmount, amount);

        if (!simulate && toExtract > 0) {
            int leftover = currentAmount - toExtract;
            if (leftover <= 0) {
                storageSouls.remove(soulType);
            } else {
                storageSouls.put(soulType, leftover);
            }
            markForUpdate();
        }

        return toExtract;
    }

    @Override
    public void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);


        CompoundTag soulsTag = new CompoundTag();
        int i = 0;
        for (Map.Entry<Holder<Soul>, Integer> entry : storageSouls.entrySet()) {
            if (entry.getKey().getRegisteredName() != null) {
                soulsTag.putString("Soul_" + i, entry.getKey().getRegisteredName());
                soulsTag.putInt("Amount_" + i, entry.getValue());
                i++;
            }
        }
        soulsTag.putInt("Count", i);
        tag.put("storageSouls", soulsTag);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        storageSouls.clear();

        if (compound.contains("storageSouls")) {
            CompoundTag soulsTag = compound.getCompound("storageSouls");
            int count = soulsTag.getInt("Count");

            for (int i = 0; i < count; i++) {
                String soulName = soulsTag.getString("Soul_" + i);
                int amount = soulsTag.getInt("Amount_" + i);


                var resourceLoc = net.minecraft.resources.ResourceLocation.tryParse(soulName);
                if (resourceLoc != null) {
                    var soulOpt = level.registryAccess().registryOrThrow(SoulRegistries.SOUL_REGISTRY_KEY).getHolder(net.minecraft.resources.ResourceKey.create(SoulRegistries.SOUL_REGISTRY_KEY, resourceLoc));
                    soulOpt.ifPresent(soulTypeHolder -> storageSouls.put(soulTypeHolder, amount));
                }
            }
        }
    }


}
