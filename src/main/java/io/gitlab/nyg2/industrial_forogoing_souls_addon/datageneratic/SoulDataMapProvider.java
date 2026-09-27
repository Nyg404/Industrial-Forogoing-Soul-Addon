package io.gitlab.nyg2.industrial_forogoing_souls_addon.datageneratic;

import io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.datacomponents.SoulData;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.register.Souls;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
@EventBusSubscriber(modid = Industrial_forogoing_souls_addon.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SoulDataMapProvider extends DataMapProvider {
    protected SoulDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        super.gather(provider);
        this.builder(SoulDataMaps.ENTITY_SOULS)
                .add(EntityType.ZOMBIE.builtInRegistryHolder(), new SoulData(Souls.ZOMBIE_SOUL, 1500), false)
                .add(EntityType.SKELETON.builtInRegistryHolder(), new SoulData(Souls.SKELETON_SOUL, 1200), false)
                .add(EntityType.BEE.builtInRegistryHolder(), new SoulData(Souls.BEE_SOUL, 800), false)
                .add(EntityType.COW.builtInRegistryHolder(), new SoulData(Souls.COW_SOUL, 500), false)
                .add(EntityType.PIG.builtInRegistryHolder(), new SoulData(Souls.PIG_SOUL, 500), false);
    }

    @SubscribeEvent // on the mod event bus
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        generator.addProvider(
                event.includeServer(),
                new SoulDataMapProvider(output, lookupProvider)
        );
    }
}
