package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.block;

import com.hrznstudio.titanium.module.BlockWithTile;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.block.SoulInfuserBlock;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.block.SoulInfuserBlockEntity;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.item.InjectorSouls;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.gitlab.nyg2.industrial_forogoing_souls_addon.Industrial_forogoing_souls_addon.MODID;

public class SoulBlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<SoulInfuserBlock> SOUL_INFUSER_BLOCK = BLOCKS.register("soul_infuser", SoulInfuserBlock::new);
    public static final DeferredItem<BlockItem> SOUL_INFUSER_ITEM = ITEMS.registerSimpleBlockItem("soul_infuser", SOUL_INFUSER_BLOCK);

    public static final DeferredItem<Item> INJECTOR_SOULS_ITEM = ITEMS.registerItem("injectro_souls_item", InjectorSouls::new);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulInfuserBlockEntity>> SOUL_INFUSER_BLOCK_ENTITY =

            BLOCK_ENTITY_TYPES.register("soul_infuser", () -> BlockEntityType.Builder.of(
                    SoulInfuserBlockEntity::new,
                    SOUL_INFUSER_BLOCK.get()
            ).build(null));


    public static final BlockWithTile SOUL_INFUSER = new BlockWithTile(
            (DeferredHolder) SOUL_INFUSER_BLOCK,
            (DeferredHolder) SOUL_INFUSER_BLOCK_ENTITY
    );

    public static void registry(IEventBus bus){
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
    }
}