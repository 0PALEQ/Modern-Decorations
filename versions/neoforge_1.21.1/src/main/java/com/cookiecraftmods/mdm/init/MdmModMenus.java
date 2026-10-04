/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.cookiecraftmods.mdm.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.Map;

import com.cookiecraftmods.mdm.world.inventory.StorageMenu;
import com.cookiecraftmods.mdm.world.inventory.OvenGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.FridgeGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.FreezerguiMenu;
import com.cookiecraftmods.mdm.network.MenuStateUpdateMessage;
import com.cookiecraftmods.mdm.MdmMod;

public class MdmModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, MdmMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<StorageMenu>> STORAGE = REGISTRY.register("storage", () -> IMenuTypeExtension.create(StorageMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FreezerguiMenu>> FREEZERGUI = REGISTRY.register("freezergui", () -> IMenuTypeExtension.create(FreezerguiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FridgeGuiMenu>> FRIDGE_GUI = REGISTRY.register("fridge_gui", () -> IMenuTypeExtension.create(FridgeGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<OvenGuiMenu>> OVEN_GUI = REGISTRY.register("oven_gui", () -> IMenuTypeExtension.create(OvenGuiMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof MdmModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				PacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}