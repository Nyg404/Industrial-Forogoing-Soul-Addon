package io.gitlab.nyg2.industrial_forogoing_souls_addon.block;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.SoulMachineTier;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.block.SoulBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SoulInfuserBlockEntity extends SoulMachineBlockEntity<SoulInfuserBlockEntity> {

    public SoulInfuserBlockEntity(BlockPos pos, BlockState state) {

        super(SoulBlockRegistry.SOUL_INFUSER, pos, state, SoulMachineTier.BASIC);
    }

    @Override
    public @NotNull SoulInfuserBlockEntity getSelf() {
        return this;
    }
}