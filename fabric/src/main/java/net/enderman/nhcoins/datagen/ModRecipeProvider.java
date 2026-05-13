package net.enderman.nhcoins.datagen;


import net.enderman.nhcoins.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
			public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
                shaped(RecipeCategory.MISC, ModItems.VOIDCORE_INGOT.get(), 1)
						.pattern("lll")
						.pattern("lll")
                        .pattern("lll")
						.define('l', ModItems.VOID_SHARDS.get()) // 'l' means "any log"
						.group("nhcoins") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
						.unlockedBy(getHasName(Items.CRAFTING_TABLE), has(ModItems.VOID_SHARDS.get()))
						.save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
