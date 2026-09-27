package io.gitlab.nyg2.industrial_forogoing_souls_addon.block;

import com.buuz135.industrial.block.IndustrialBlock;
import com.buuz135.industrial.module.ModuleResourceProduction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SoulInfuserBlock extends IndustrialBlock<SoulInfuserBlockEntity> {

    public SoulInfuserBlock() {
        super("soul_infuser", BlockBehaviour.Properties.of(), SoulInfuserBlockEntity.class , ModuleResourceProduction.TAB_RESOURCE);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<SoulInfuserBlockEntity> getTileEntityFactory() {
        return SoulInfuserBlockEntity::new;
    }


}