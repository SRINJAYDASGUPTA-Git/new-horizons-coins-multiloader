package net.enderman.nhcoins;

import net.enderman.nhcoins.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class NewHorizonsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModTranslationProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModTagProvider::new);
		pack.addProvider(ModWorldgenProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
    }
}
