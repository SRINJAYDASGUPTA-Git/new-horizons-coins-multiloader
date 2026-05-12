package net.enderman.nhcoins.init;

import net.enderman.nhcoins.services.Services;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
    private ModItems() {
    }

    public static void load() {
    }

    public static final RegistryHandle<Item> METRO_TICKET = Services.REGISTRY.registerItem(
            "metro_ticket",
            properties -> new Item(properties
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(64))
    );

    public static final RegistryHandle<Item> GOLD_MOHUR = Services.REGISTRY.registerItem(
            "gold_mohur",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON)
                    .stacksTo(64))
    );
    public static final RegistryHandle<Item> SILVER_DUCATS = Services.REGISTRY.registerItem(
            "silver_ducats",
            properties -> new Item(properties
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(64))
    );

    public static final RegistryHandle<Item> BRONZE_SICKLES = Services.REGISTRY.registerItem(
            "bronze_sickles",
            properties -> new Item(properties
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(64))
    );

    public static final RegistryHandle<Item> VOID_SHARDS = Services.REGISTRY.registerItem(
            "void_shards",
            properties -> new Item(properties
                    .rarity(Rarity.EPIC)
                    .stacksTo(64))
    );

    public static final RegistryHandle<Item> VOIDCORE_INGOT = Services.REGISTRY.registerItem(
            "voidcore_ingot",
            properties -> new Item(properties
                    .rarity(Rarity.EPIC)
                    .stacksTo(64))
    );
}
