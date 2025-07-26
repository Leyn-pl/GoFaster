package net.leyn.gofaster;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class GoFaster implements ModInitializer {
	public static final String MOD_ID = "go-faster";
	private static float lastUpdateTicks = 20f;
	private static String storedFallHeight;
	private static String storedSpeed;
	private static boolean storedShowFH;
	private static boolean storedShowSpeed;

	@Override
	public void onInitialize() {
		MinecraftClient client = MinecraftClient.getInstance();
		Config config = Config.CURRENT;

		HudRenderCallback.EVENT.register((context, tickDeltaManager) -> {
			lastUpdateTicks += tickDeltaManager.getTickDelta(false);

			int x = context.getScaledWindowWidth() / 2 + 12 + config.xOffset;
			int y = context.getScaledWindowHeight() / 2;

			if (lastUpdateTicks >= config.updateRate) {
				storedFallHeight = "↓ " + String.format("%.0f", Math.floor(client.player.fallDistance)) + " b";
				storedSpeed = "→ " + String.format("%.0f", Math.floor(client.player.getVelocity().length() * 20)) + " m/s";
				storedShowFH = config.showFallHeight ? showFallHeight(client.player, config.thresholdFD, config.checkForMace) : false;
				storedShowSpeed = config.showSpeed ? showSpeed(client.player, config.thresholdS, config.alwaysShowFlying) : false;
				lastUpdateTicks = 0;
			}
			if (storedShowFH) {
				context.drawTextWithShadow(client.textRenderer, storedFallHeight, x + config.xOffset, (int) (y * 0.97f) + config.yOffset, config.textColor);
			}
			if (storedShowSpeed) {
				context.drawTextWithShadow(client.textRenderer, storedSpeed, x + config.xOffset, (int) (y * 1.02f) + config.yOffset, config.textColor);
			}
		});
	}

	private boolean hasMace(ClientPlayerEntity player) {
		return player.getInventory().containsAny(Set.of(Items.MACE));
	}

	private boolean showFallHeight(ClientPlayerEntity player, int thresholdFD, boolean checkForMace) {
		float fallHeight = player.fallDistance;
		if (fallHeight < thresholdFD) return false; // optimisation
		if (!checkForMace) return true;
		return hasMace(player);
	}

	private boolean showSpeed(ClientPlayerEntity player, int minSpeed, boolean checkFlying) {
		boolean isFlying = checkFlying ? player.isFallFlying() : true;
		boolean isGoingFast = player.getVelocity().length() * 20 >= minSpeed;
		return isFlying || isGoingFast;
	}
}