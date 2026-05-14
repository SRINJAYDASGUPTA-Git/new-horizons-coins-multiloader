package net.enderman.nhcoins.blocks;

import net.enderman.nhcoins.menu.CoinSmithMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public  class CoinSmithBlock extends Block implements MenuProvider {
//public  class CoinSmithBlock extends Block{
    public CoinSmithBlock(Properties properties) {
        super(properties);
    }

	@Override
	protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		player.openMenu(this);
		return InteractionResult.SUCCESS;
	}

	@Override
	public @NonNull Component getDisplayName() {
			return getName();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new CoinSmithMenu(containerId, inventory);
	}
}