package net.enderman.nhcoins.services;

import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.services.types.IClientRegistryHelper;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        value = Dist.CLIENT
)
public class NeoForgeClientRegistryHelper implements IClientRegistryHelper {
    public static void register(IEventBus eventBus) {
        eventBus.register(NeoForgeClientRegistryHelper.class);
    }

    private static final List<Runnable> SCREEN_REGISTRATIONS =
            new ArrayList<>();

    @Override
    public <T extends AbstractContainerMenu,
            U extends Screen & MenuAccess<T>>
    void registerMenuScreen(
            RegistryHandle<MenuType<T>> menu,
            ScreenFactory<T, U> screenFactory
    ) {

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