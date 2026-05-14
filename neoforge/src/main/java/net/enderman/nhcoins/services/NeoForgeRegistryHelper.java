package net.enderman.nhcoins.services;

import com.mojang.serialization.MapCodec;
import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.services.types.IRegistryHelper;
import net.enderman.nhcoins.services.util.MenuFactory;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Constants.MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, Constants.MOD_ID);
    private static final List<Runnable> SCREEN_REGISTRATIONS =
            new ArrayList<>();
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        MENU_TYPES.register(eventBus);

        eventBus.register(NeoForgeRegistryHelper.class);
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        Identifier id = Constants.id(name);
        DeferredItem<T> deferredItem = ITEMS.registerItem(name, item);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return deferredItem.get();
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name, properties -> item.apply(block.get(), properties));
    }

    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        Identifier id = Constants.id(name);
        DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return deferredBlock.get();
            }
        };
    }

    @Override
    public <T extends Recipe<?>> RegistryHandle<RecipeSerializer<T>> registerRecipeSerializer(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiFunction<MapCodec<T>, StreamCodec<RegistryFriendlyByteBuf, T>, RecipeSerializer<T>> recipeSerializer) {
        Identifier id = Constants.id(name);
        Supplier<RecipeSerializer<T>> registered =
            RECIPE_SERIALIZERS.register(
                    name,
                    () -> new RecipeSerializer<>(
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
                return registered.get();
            }
        };
    }

    @Override
    public <T extends Recipe<?>> RegistryHandle<RecipeType<T>> registerRecipeType(String name, Supplier<RecipeType<T>> recipeType) {
        Identifier id = Constants.id(name);
        Supplier<RecipeType<T>> registered =
            RECIPE_TYPES.register(
                    name,
                    () -> new RecipeType<>(){}
            );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public RecipeType<T> get() {
                return registered.get();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu> RegistryHandle<MenuType<T>> registerMenuType(String name, MenuFactory<T> constructor) {
        Identifier id = Constants.id(name);
        Supplier<MenuType<T>> registered =
                MENU_TYPES.register(
                        name,
                        () -> new MenuType<>(constructor::create, FeatureFlagSet.of())
                );

        return new RegistryHandle<MenuType<T>>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public MenuType<T> get() {
                return registered.get();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void registerMenuScreen(RegistryHandle<MenuType<T>> menu, ScreenFactory<T, U> screenFactory) {
        SCREEN_REGISTRATIONS.add(() ->
                CURRENT_EVENT.register(
                        menu.get(),
                        screenFactory::create
                )
        );
    }

    private static RegisterMenuScreensEvent CURRENT_EVENT;

    @SubscribeEvent
    public static void onRegisterScreens(
            RegisterMenuScreensEvent event
    ) {

        CURRENT_EVENT = event;

        SCREEN_REGISTRATIONS.forEach(Runnable::run);

        CURRENT_EVENT = null;
    }
}
