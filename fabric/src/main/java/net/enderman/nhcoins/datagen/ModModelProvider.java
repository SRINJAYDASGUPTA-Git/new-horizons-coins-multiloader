package net.enderman.nhcoins.datagen;

import net.enderman.nhcoins.init.ModBlocks;
import net.enderman.nhcoins.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
	public void generateBlockStateModels(@NonNull BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createTrivialCube(ModBlocks.VOIDORE.block().get());
	}

	@Override
	public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.GOLD_MOHUR.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BRONZE_SICKLES.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.SILVER_DUCATS.get(), ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.VOID_SHARDS.get(), ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.VOIDCORE_INGOT.get(), ModelTemplates.FLAT_ITEM);
	}

	@Override
	public String getName() {
		return "NHCoins Model Provider";
	}
}
