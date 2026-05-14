package net.enderman.nhcoins.services;

import com.mojang.serialization.MapCodec;
import net.enderman.nhcoins.services.types.IRegistryHelper;
import net.enderman.nhcoins.services.util.MenuFactory;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.inventory.MenuType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        Identifier id = key.identifier();

        T registered = Registry.register(BuiltInRegistries.ITEM, id, item.apply(new Item.Properties().setId(key)));

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name, properties -> item.apply(block.get(), properties));
    }

    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        ResourceKey<Block> key = IRegistryHelper.blockKey(name);
        Identifier id = key.identifier();

        T registered = Registry.register(BuiltInRegistries.BLOCK, id, block.apply(BlockBehaviour.Properties.of().setId(key)));
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Recipe<?>> RegistryHandle<RecipeSerializer<T>> registerRecipeSerializer(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiFunction<MapCodec<T>, StreamCodec<RegistryFriendlyByteBuf, T>, RecipeSerializer<T>> recipeSerializer) {
        ResourceKey<RecipeSerializer<?>> key = IRegistryHelper.recipeSerializerKey(name);
        Identifier id = key.identifier();
        RecipeSerializer<T> registered = Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id,
                recipeSerializer.apply(
                        codec, streamCodec
                )
        );
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public RecipeSerializer<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Recipe<?>> RegistryHandle<RecipeType<T>> registerRecipeType(String name, Supplier<RecipeType<T>> recipeType) {
        ResourceKey<RecipeType<?>> key = IRegistryHelper.recipeTypeKey(name);
        Identifier id = key.identifier();

        RecipeType<T> registered = Registry.register(
                BuiltInRegistries.RECIPE_TYPE,
                id,
                new RecipeType<T>() {}
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public RecipeType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerMenuType(String name, MenuFactory<T> constructor) {
        ResourceKey<net.minecraft.world.inventory.MenuType<?>> key = IRegistryHelper.menuTypeKey(name);
        Identifier id = key.identifier();
        MenuType<T> registered = Registry.register(
                BuiltInRegistries.MENU,
                id,
                new MenuType<>(constructor::create, FeatureFlagSet.of())
        );

        return new RegistryHandle<MenuType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public MenuType<T> get() {
                return registered;
            }
        };
    }

}
