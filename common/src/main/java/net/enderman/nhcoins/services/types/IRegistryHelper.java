package net.enderman.nhcoins.services.types;

import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public interface IRegistryHelper {
    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    static ResourceKey<Item> itemKey(String name){
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }
}
