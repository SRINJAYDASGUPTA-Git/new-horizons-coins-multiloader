package net.enderman.nhcoins.menu;

import net.enderman.nhcoins.init.ModMenus;
import net.enderman.nhcoins.init.ModRecipes;
import net.enderman.nhcoins.recipes.CoinSmithRecipe;
import net.enderman.nhcoins.recipes.CoinSmithRecipeInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class CoinSmithMenu extends AbstractContainerMenu {

    // 🔹 Single input container
    private final Container input = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            super.setChanged();
            slotsChanged(this);
        }
    };

    private final ResultContainer output = new ResultContainer();
    private final Level level;

    public CoinSmithMenu(int id, Inventory playerInventory) {
        super(ModMenus.COIN_SMITH_MENU.get(), id);
        this.level = playerInventory.player.level();

        // 🔹 Input slot
        addSlot(new Slot(input, 0, 27, 47));

        // 🔹 Output slot
        addSlot(new Slot(output, 0, 134, 47) {
            @Override
            public void onTake(Player player, ItemStack stack) {
                CoinSmithMenu.this.onTake(player, stack);
            }
        });

        // 🔹 Player inventory
        addStandardInventorySlots(playerInventory, 8, 84);
    }

    // 🔥 Core logic: update output
//    @Override
//    public void slotsChanged(Container container) {
//        super.slotsChanged(container);
//
//        if (container == input && level instanceof ServerLevel serverLevel) {
//            CoinSmithRecipeInput recipeInput = new CoinSmithRecipeInput(input.getItem(0));
//            Optional<RecipeHolder<CoinSmithRecipe>> recipe = serverLevel.recipeAccess().getRecipeFor(ModRecipes.COIN_SMITH_RECIPE_TYPE, recipeInput, serverLevel);
//
//            if(recipe.isPresent()){
//                output.setItem(0, recipe.get().value().assemble(recipeInput));
//                output.setRecipeUsed(recipe.get());
//            } else {
//                output.clearContent();
//                output.setRecipeUsed(null);
//            }
//        }
//    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

        if (container == input && level instanceof ServerLevel serverLevel) {

            ItemStack inputStack = input.getItem(0);

            if (inputStack.isEmpty()) {
                output.clearContent();
                output.setRecipeUsed(null);
                return;
            }

            CoinSmithRecipeInput recipeInput = new CoinSmithRecipeInput(inputStack);

            Optional<RecipeHolder<CoinSmithRecipe>> recipeOpt =
                    serverLevel.recipeAccess().getRecipeFor(
                            ModRecipes.COIN_SMITH_RECIPE_TYPE.get(),
                            recipeInput,
                            serverLevel
                    );

            if (recipeOpt.isPresent()) {

                CoinSmithRecipe recipe = recipeOpt.get().value();

                // 🔥 SINGLE conversion only
                ItemStack result = recipe.assemble(recipeInput).copy();

                output.setItem(0, result);
                output.setRecipeUsed(recipeOpt.get());

            } else {
                output.clearContent();
                output.setRecipeUsed(null);
            }
        }
    }

    // 🔥 Handle consumption when player takes result
//    private void onTake(Player player, ItemStack stack) {
//        stack.onCraftedBy(player, stack.getCount());
//        output.awardUsedRecipes(player, List.of(input.getItem(0), input.getItem(1)));
//
//        input.removeItem(0, stack.getCount());
//        input.removeItem(1, stack.getCount());
//    }
    private void onTake(Player player, ItemStack stack) {

        if (!(level instanceof ServerLevel serverLevel)) return;

        ItemStack inputStack = input.getItem(0);

        if (inputStack.isEmpty()) return;

        input.removeItem(0, 1);

        stack.onCraftedBy(player, stack.getCount());

        output.awardUsedRecipes(player, List.of(inputStack));

        slotsChanged(input);
    }
//    @Override
//    public @NonNull ItemStack quickMoveStack(Player player, int index) {
//        return ItemStack.EMPTY;
//    }

    @Override
    public @NonNull ItemStack quickMoveStack(Player player, int index) {

        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {

            ItemStack stack = slot.getItem();
            original = stack.copy();

            // 🔥 SHIFT CLICK OUTPUT SLOT
            if (index == 1) {

                if (!(level instanceof ServerLevel serverLevel)) {
                    return ItemStack.EMPTY;
                }

                ItemStack inputStack = input.getItem(0);

                if (inputStack.isEmpty()) {
                    return ItemStack.EMPTY;
                }

                CoinSmithRecipeInput recipeInput = new CoinSmithRecipeInput(inputStack);

                Optional<RecipeHolder<CoinSmithRecipe>> recipeOpt =
                        serverLevel.recipeAccess().getRecipeFor(
                                ModRecipes.COIN_SMITH_RECIPE_TYPE.get(),
                                recipeInput,
                                serverLevel
                        );

                if (recipeOpt.isEmpty()) {
                    return ItemStack.EMPTY;
                }

                CoinSmithRecipe recipe = recipeOpt.get().value();

                int operations = inputStack.getCount();

                ItemStack result = recipe.assemble(recipeInput).copy();
                result.setCount(result.getCount() * operations);

                // respect max stack size
                if (result.getCount() > result.getMaxStackSize()) {
                    result.setCount(result.getMaxStackSize());
                }

                if (!this.moveItemStackTo(result, 2, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

                input.removeItem(0, operations);

                output.clearContent();

                return original;
            }

            // 🔥 PLAYER INVENTORY -> INPUT
            else if (index >= 2) {

                if (!this.moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            // 🔥 INPUT -> PLAYER INVENTORY
            else if (index == 0) {

                if (!this.moveItemStackTo(stack, 2, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        clearContainer(player, input);
    }
}
