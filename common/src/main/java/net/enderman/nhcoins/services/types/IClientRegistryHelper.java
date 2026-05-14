package net.enderman.nhcoins.services.types;

import net.enderman.nhcoins.services.util.RegistryHandle;
import net.enderman.nhcoins.services.util.ScreenFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface IClientRegistryHelper {

    <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>>
    void registerMenuScreen(
            RegistryHandle<MenuType<T>> menu,
            ScreenFactory<T, U> screenFactory
    );
}
