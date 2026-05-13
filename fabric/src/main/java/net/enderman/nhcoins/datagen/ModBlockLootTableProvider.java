package net.enderman.nhcoins.datagen;

import net.enderman.nhcoins.init.ModBlocks;
import net.enderman.nhcoins.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        add(
                ModBlocks.VOIDORE.block().get(), LootTable.lootTable().withPool(
                        applyExplosionCondition(
                                ModItems.VOID_SHARDS.get(),
                                LootPool.lootPool()
                                .setRolls(new UniformGenerator(new ConstantValue(1), new ConstantValue(3)))
                                .add(LootItem.lootTableItem((ItemLike)ModItems.VOID_SHARDS.get()))
                        )
                )
        );

//        dropWhenSilkTouch(ModBlocks.VOIDORE);
    }
}
