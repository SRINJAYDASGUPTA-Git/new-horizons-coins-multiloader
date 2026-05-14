package net.enderman.nhcoins.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.enderman.nhcoins.init.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class CoinSmithRecipe implements Recipe<CoinSmithRecipeInput> {
    private final ItemStackTemplate result;
    private final Ingredient input;

    public CoinSmithRecipe(ItemStackTemplate result, Ingredient input) {
        this.input = input;
        this.result = result;
    }

    public ItemStackTemplate getResult() {
        return result;
    }

    public Ingredient getInput() {
        return input;
    }


    // ✅ MATCHES (single input)
    @Override
    public boolean matches(CoinSmithRecipeInput recipeInput, Level level) {
        return input.test(recipeInput.input());
    }

    // ✅ OUTPUT (single operation, scaling happens in menu)
    @Override
    public ItemStack assemble(CoinSmithRecipeInput recipeInput) {
        return result.create().copy();
    }

    // registry stuff stays same
    @Override
    public RecipeSerializer<? extends Recipe<CoinSmithRecipeInput>> getSerializer() {
        return ModRecipes.COIN_SMITH_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CoinSmithRecipeInput>> getType() {
        return ModRecipes.COIN_SMITH_RECIPE_TYPE.get();
    }

    // recipe book (unchanged)
    @Override
    public @Nullable RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "coin_smithing";
    }

    public static final MapCodec<CoinSmithRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(CoinSmithRecipe::getResult),
                    Ingredient.CODEC.fieldOf("input").forGetter(CoinSmithRecipe::getInput)
            ).apply(instance, CoinSmithRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CoinSmithRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC,
            CoinSmithRecipe::getResult,
            Ingredient.CONTENTS_STREAM_CODEC,
            CoinSmithRecipe::getInput,
            CoinSmithRecipe::new
    );
}
