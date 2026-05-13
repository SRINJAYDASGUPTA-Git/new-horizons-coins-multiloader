package net.enderman.nhcoins.datagen;

import net.enderman.nhcoins.init.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends FabricTagsProvider.BlockTagsProvider {


    public ModTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.VOIDORE.block().get());
    }
}
