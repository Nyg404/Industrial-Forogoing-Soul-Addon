package io.gitlab.nyg2.industrial_forogoing_souls_addon;

public enum SoulMachineTier {
    BASIC(4),
    SOUL(6);

    private final int maxSouls;

    SoulMachineTier(int maxSouls) {
        this.maxSouls = maxSouls;
    }
    public int getMaxSouls() {
        return maxSouls;
    }
}
