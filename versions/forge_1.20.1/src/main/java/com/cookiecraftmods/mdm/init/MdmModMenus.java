package com.cookiecraftmods.mdm.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.Minecraft;

import java.util.Map;

import com.cookiecraftmods.mdm.world.inventory.StorageMenu;
import com.cookiecraftmods.mdm.world.inventory.OvenGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.FridgeGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.FreezerguiMenu;
import com.cookiecraftmods.mdm.network.MenuStateUpdateMessage;
import com.cookiecraftmods.mdm.MdmMod;

public class MdmModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MdmMod.MODID);
	public static final RegistryObject<MenuType<StorageMenu>> STORAGE = REGISTRY.register("storage", () -> IForgeMenuType.create(StorageMenu::new));
	public static final RegistryObject<MenuType<FreezerguiMenu>> FREEZERGUI = REGISTRY.register("freezergui", () -> IForgeMenuType.create(FreezerguiMenu::new));
	public static final RegistryObject<MenuType<FridgeGuiMenu>> FRIDGE_GUI = REGISTRY.register("fridge_gui", () -> IForgeMenuType.create(FridgeGuiMenu::new));
	public static final RegistryObject<MenuType<OvenGuiMenu>> OVEN_GUI = REGISTRY.register("oven_gui", () -> IForgeMenuType.create(OvenGuiMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				MdmMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof MdmModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				MdmMod.PACKET_HANDLER.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
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
