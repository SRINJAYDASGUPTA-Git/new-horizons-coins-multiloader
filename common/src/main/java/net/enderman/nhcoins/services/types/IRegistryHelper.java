package net.enderman.nhcoins.services.types;

import com.mojang.serialization.MapCodec;
import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.services.util.BlockWithItemRegistryHolder;
import net.enderman.nhcoins.services.util.MenuFactory;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryHelper {

    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item);

    default <T extends Block> BlockWithItemRegistryHolder<T> reigisterBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block) {
        return reigisterBlockWithItem(name, block, BlockItem::new);
    }

    default <T extends Block> BlockWithItemRegistryHolder<T> reigisterBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block, BiFunction<Block, Item.Properties, BlockItem> item) {
        RegistryHandle<T> blockHandle = registerBlock(name, block);
        RegistryHandle<? extends BlockItem> itemHandle = registerBlockItem(name, blockHandle, item);
        return new BlockWithItemRegistryHolder<>(blockHandle, itemHandle);
    }

    <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block);

    static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }

    static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Constants.id(name));
    }

    static ResourceKey<RecipeSerializer<?>> recipeSerializerKey(String name) {
        return ResourceKey.create(
                Registries.RECIPE_SERIALIZER,
                Constants.id(name)
        );
    }

    static ResourceKey<RecipeType<?>> recipeTypeKey(String name) {
        return ResourceKey.create(
                Registries.RECIPE_TYPE,
                Constants.id(name)
        );
    }

    <T extends Recipe<?>> RegistryHandle<RecipeSerializer<T>> registerRecipeSerializer(
            String name,
            MapCodec<T> codec,
            StreamCodec<RegistryFriendlyByteBuf, T> streamCodec,
            BiFunction<MapCodec<T>,
                    StreamCodec<RegistryFriendlyByteBuf, T>,
                    RecipeSerializer<T>> factory
    );

    <T extends Recipe<?>> RegistryHandle<RecipeType<T>> registerRecipeType(
            String name,
            Supplier<RecipeType<T>> recipeType
    );

    static ResourceKey<net.minecraft.world.inventory.MenuType<?>> menuTypeKey(String name) {
        return ResourceKey.create(
                Registries.MENU,
                Constants.id(name)
        );
    }

    <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerMenuType(
            String name,
            MenuFactory<T> constructor
    );

    <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>>
    void registerMenuScreen(
            RegistryHandle<MenuType<T>> menu,
            ScreenFactory<T, U> screenFactory
    );
}
