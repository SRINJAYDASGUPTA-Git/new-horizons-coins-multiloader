package net.enderman.nhcoins.services.util;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface ScreenFactory<
        T extends AbstractContainerMenu,
        U extends Screen & MenuAccess<T>
> {

    U create(T menu, Inventory inventory, Component title);
}
