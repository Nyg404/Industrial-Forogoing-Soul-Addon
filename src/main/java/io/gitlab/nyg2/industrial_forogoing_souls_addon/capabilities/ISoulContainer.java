package io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.souls.Soul;
import net.minecraft.core.Holder;

public interface ISoulContainer {

    enum Action {
        EXECUTE, SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }

        public boolean simulate() {
            return this == SIMULATE;
        }
    }

    int getStorageTypesCount();

    Holder<Soul> getSoulType(int index);
    int getMaxStorageTypes(int index);

    int fill(Holder<Soul> soulType, int amount);
    int drain(Holder<Soul> soulType, int amount);

    int getSoul(Holder<Soul> soulType);


}
