package net.enderman.nhcoins.datagen;

import net.enderman.nhcoins.init.ModBlocks;
import net.enderman.nhcoins.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModTranslationProvider extends FabricLanguageProvider {
    public ModTranslationProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.@NonNull Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.GOLD_MOHUR.get(), "Mohur");
        translationBuilder.add(ModItems.SILVER_DUCATS.get(), "Ducats");
        translationBuilder.add(ModItems.BRONZE_SICKLES.get(), "Sickles");
//        translationBuilder.add(ModItems.NHCOINS_ITEM_GROUP_KEY.get(), "New Horizons Coins");
        translationBuilder.add(ModItems.METRO_TICKET.get(), "Metro Ticket");
        translationBuilder.add(ModItems.VOID_SHARDS.get(), "Void Shards");
        translationBuilder.add(ModItems.VOIDCORE_INGOT.get(), "Void Core Ingot");

        translationBuilder.add(ModBlocks.COIN_SMITH.block().get(), "Coin Smith");
        translationBuilder.add(ModBlocks.VOIDORE.block().get(), "Void Ore");
//        translationBuilder.add(ModBlocks.ATM_BLOCK, "ATM Block");
//        translationBuilder.add("container.coin_smith", "Coin Smith");
    }
}
