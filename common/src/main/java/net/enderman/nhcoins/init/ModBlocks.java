package net.enderman.nhcoins.init;

import net.enderman.nhcoins.custom.blocks.CoinSmithBlock;
import net.enderman.nhcoins.services.Services;
import net.enderman.nhcoins.services.util.BlockWithItemRegistryHolder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;

public final class ModBlocks {
    private ModBlocks(){
    }

    public static void load(){}

    public static final BlockWithItemRegistryHolder<Block> VOIDORE = Services.REGISTRY.reigisterBlockWithItem(
            "voidore",
            properties -> new DropExperienceBlock(
                    UniformInt.of(3, 6),
                    properties
                            .strength(4.5F, 3.0F)
                            .sound(SoundType.DEEPSLATE)
            )
    );

    public static final BlockWithItemRegistryHolder<Block> COIN_SMITH = Services.REGISTRY.reigisterBlockWithItem(
            "coin_smith",
            properties -> new CoinSmithBlock(
                    properties
                            .sound(SoundType.NETHERITE_BLOCK)
					        .strength(-1.0F, 3600000.0F)
					        .noLootTable()
            )
    );
}
