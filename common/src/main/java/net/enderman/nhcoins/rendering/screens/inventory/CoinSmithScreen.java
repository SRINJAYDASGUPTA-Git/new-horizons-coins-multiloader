package net.enderman.nhcoins.rendering.screens.inventory;

import net.enderman.nhcoins.Constants;
import net.enderman.nhcoins.menu.CoinSmithMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CoinSmithScreen extends AbstractContainerScreen<CoinSmithMenu> {
//    E:\MC Mods\new-horizons-coins-26.1\.gradle\loom-cache\minecraftMaven\net\minecraft\minecraft-merged-a26c9a9f3c\26.1\minecraft-merged-a26c9a9f3c-26.1.jar!\assets\minecraft\textures\gui\container\smithing.png
    private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/coin_smith_gui.png");
        public CoinSmithScreen(CoinSmithMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, Component.nullToEmpty(" "));
//        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractBackground(graphics, mouseX, mouseY, delta);
		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
	}

}
