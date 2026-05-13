package net.enderman.nhcoins.services.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public record BlockWithItemRegistryHolder<T extends Block>(
        RegistryHandle<T> block,
        RegistryHandle<? extends BlockItem> blockItem
) {
}
