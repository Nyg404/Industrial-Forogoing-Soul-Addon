package io.gitlab.nyg2.industrial_forogoing_souls_addon;

public enum SoulMachineTier {
    BASIC(4, 1_000_000),
    SOUL(6, 5_000_000);

    private final int maxSouls;
    private final int maxSoulCapacity;

    SoulMachineTier(int maxSouls, int maxSoulCapacity) {
        this.maxSouls = maxSouls;
        this.maxSoulCapacity = maxSoulCapacity;
    }

    public int getMaxSouls() {
        return maxSouls;
    }

    public int getMaxSoulCapacity() {
        return maxSoulCapacity;
    }
}