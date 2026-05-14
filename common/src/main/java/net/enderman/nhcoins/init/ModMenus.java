package net.enderman.nhcoins.init;

import net.enderman.nhcoins.menu.CoinSmithMenu;
import net.enderman.nhcoins.services.Services;
import net.enderman.nhcoins.services.util.RegistryHandle;
import net.minecraft.world.inventory.MenuType;

public final class ModMenus {
    private ModMenus(){
    }

    public static void load(){}

    public static final RegistryHandle<MenuType<CoinSmithMenu>> COIN_SMITH_MENU = Services.REGISTRY.registerMenuType(
            "coin_smith_menu",
            CoinSmithMenu::new
    );

}
