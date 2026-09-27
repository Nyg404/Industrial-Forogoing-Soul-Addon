package io.gitlab.nyg2.industrial_forogoing_souls_addon.souls;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.capabilities.ISoulContainer;
import net.minecraft.core.Holder;

public class SoulContainerUtils {
    public static boolean transfer(ISoulContainer source, ISoulContainer target, Holder<Soul> soul, int amount) {
        int drained = source.drain(soul, amount);
        System.out.println("TRANSFER: drained = " + drained);

        if (drained <= 0) return false;

        int filled = target.fill(soul, drained);
        System.out.println("TRANSFER: filled = " + filled);

        if (filled < drained) {
            int returned = source.fill(soul, drained - filled);
            System.out.println("TRANSFER: returned = " + returned);
        }

        return filled > 0;
    }
}
