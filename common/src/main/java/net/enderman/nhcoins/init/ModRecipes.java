package net.enderman.nhcoins.init;

import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.recipes.CoinSmithRecipe;
import net.enderman.nhcoins.services.Services;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipes {
    private ModRecipes() {
    }

    public static void load() {
    }

    public static final RegistryHandle<RecipeSerializer<CoinSmithRecipe>> COIN_SMITH_RECIPE_SERIALIZER = Services.REGISTRY.registerRecipeSerializer(
            "coin_smithing",
            CoinSmithRecipe.CODEC,
            CoinSmithRecipe.STREAM_CODEC,
            RecipeSerializer::new
    );

    public static final RegistryHandle<RecipeType<CoinSmithRecipe>> COIN_SMITH_RECIPE_TYPE = Services.REGISTRY.registerRecipeType(
            "coin_smithing",
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return Constants.MOD_ID + "coin_smithing";
                }
            }
    );
}
