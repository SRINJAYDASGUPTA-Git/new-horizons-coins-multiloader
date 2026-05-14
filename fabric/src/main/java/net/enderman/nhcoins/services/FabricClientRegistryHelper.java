package net.enderman.nhcoins.services;

import net.enderman.nhcoins.services.types.IClientRegistryHelper;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class FabricClientRegistryHelper implements IClientRegistryHelper {
    @Override
    public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void registerMenuScreen(RegistryHandle<MenuType<T>> menu, ScreenFactory<T, U> screenFactory) {
        MenuScreens.register(
                menu.get(),
                screenFactory::create
        );
    }
}
