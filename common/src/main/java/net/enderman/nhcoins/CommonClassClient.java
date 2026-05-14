package net.enderman.nhcoins;

import net.enderman.nhcoins.init.ModMenus;
import net.enderman.nhcoins.rendering.screens.inventory.CoinSmithScreen;
import net.enderman.nhcoins.services.Services;
import net.minecraft.client.gui.screens.MenuScreens;

public class CommonClassClient {

    public static void init(){
        Services.REGISTRY.registerMenuScreen(
                ModMenus.COIN_SMITH_MENU, CoinSmithScreen::new
        );
    }
}
