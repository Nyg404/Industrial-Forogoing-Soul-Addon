package io.gitlab.nyg2.industrial_forogoing_souls_addon.server.block;

import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.hrznstudio.titanium.component.inventory.InventoryComponent;
import com.hrznstudio.titanium.module.BlockWithTile;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.SoulMachineTier;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.menu.SoulMachineMenu;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.recipe.ModRecipes;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.recipe.SoulInfuserRecipe;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.register.SoulRegistries;
import io.gitlab.nyg2.industrial_forogoing_souls_addon.server.souls.Soul;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public abstract class SoulMachineBlockEntity<T extends SoulMachineBlockEntity<T>> extends IndustrialMachineTile<T> {
    protected SoulMachineTier tier;

    protected final InventoryComponent<T> itemInventory;
    private SoulInfuserRecipe currentRecipe;
    private int progress = 0;
    private final int MAX_PROGRESS = 100;
    protected final Map<Holder<Soul>, Integer> storageSouls = new HashMap<>();
    public InventoryComponent<T> getItemInventory() {
        return itemInventory;
    }
    public SoulMachineBlockEntity(BlockWithTile basicTileBlock, BlockPos blockPos, BlockState blockState, SoulMachineTier tier) {
        super(basicTileBlock, blockPos, blockState);
        this.tier = tier;


        this.addInventory(this.itemInventory = new InventoryComponent<T>("inventory", 0, 0, 5)
                .setComponentHarness(this.getSelf())
                .setSlotPosition(slot -> switch (slot) {
                    case 0 -> Pair.of(85, 41);
                    case 1 -> Pair.of(128, 78);
                    case 2 -> Pair.of(85, 78);
                    case 3 -> Pair.of(128, 41);
                    case 4 -> Pair.of(160, 60);
                    default -> Pair.of(0, 0);
                })
                .setInputFilter((stack, slot) -> slot != 4));

    }

    @Override
    public void openGui(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        serverPlayer.openMenu(
                new SimpleMenuProvider(
                        (containerId, inventory, p) ->
                                new SoulMachineMenu(containerId, inventory, this),
                        Component.literal("Soul Machine")
                ),
                buffer -> buffer.writeBlockPos(worldPosition)
        );
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

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (this.level == null || this.level.isClientSide) return;
        var recipes = this.level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.SOUL_INFUSER_TYPE.get());

        System.out.println("SOUL RECIPES: " + recipes.size());
        if (this.currentRecipe == null || !matchesRecipe(this.currentRecipe)) {
            this.currentRecipe = this.level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.SOUL_INFUSER_TYPE.get())
                    .stream()
                    .map(RecipeHolder::value)
                    .filter(this::matchesRecipe)
                    .findFirst()
                    .orElse(null);

            if (this.currentRecipe == null) {
                this.progress = 0;
                return;
            }

            System.out.println("FOUND RECIPE: " + this.currentRecipe);
        }

        System.out.println(
                "CRAFTING progress=" + this.progress +
                        " input=" + this.itemInventory.getStackInSlot(0) +
                        " souls=" + this.storageSouls
        );

        this.progress++;

        if (this.progress >= MAX_PROGRESS) {
            System.out.println("CRAFTING!");
            craftItem();
            this.progress = 0;
            this.currentRecipe = null;
        }
    }
    private boolean matchesRecipe(SoulInfuserRecipe recipe) {
        // Проверяем входной предмет в слоте 0 (к примеру)
        ItemStack inputStack = this.itemInventory.getStackInSlot(0);
        if (!recipe.getInputItem().test(inputStack)) return false;

        // Проверяем, хватает ли нужного типа душ во внутреннем Map storageSouls
        int availableSouls = this.storageSouls.getOrDefault(recipe.getRequiredSoul(), 0);
        if (availableSouls < recipe.getSoulAmount()) return false;

        // Проверяем, есть ли место в выходном слоте (слот 4)
        ItemStack outputStack = this.itemInventory.getStackInSlot(4);
        ItemStack recipeResult = recipe.getResultItem(this.level.registryAccess());

        if (!outputStack.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(outputStack, recipeResult)) return false;
            if (outputStack.getCount() + recipeResult.getCount() > outputStack.getMaxStackSize()) return false;
        }

        return true;
    }
    private void craftItem() {
        if (this.currentRecipe == null) return;

        // Списываем входной предмет
        this.itemInventory.getStackInSlot(0).shrink(1);

        // Списываем кастомные души из вашего хранилища
        extractSouls(this.currentRecipe.getRequiredSoul(), this.currentRecipe.getSoulAmount(), false);

        // Добавляем результат в слот 4
        ItemStack result = this.currentRecipe.getResultItem(this.level.registryAccess()).copy();
        ItemStack outputSlot = this.itemInventory.getStackInSlot(4);

        if (outputSlot.isEmpty()) {
            this.itemInventory.setStackInSlot(4, result);
        } else {
            outputSlot.grow(result.getCount());
        }

        this.markForUpdate();
    }
}
