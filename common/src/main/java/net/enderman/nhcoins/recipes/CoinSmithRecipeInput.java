package net.enderman.nhcoins.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CoinSmithRecipeInput(ItemStack input) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
			case 0 -> input;
			default -> ItemStack.EMPTY;
		};
    }

    @Override
    public int size() {
        return 1;
    }
}
