package com.cookiecraftmods.mdm.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import com.cookiecraftmods.mdm.client.gui.StorageScreen;
import com.cookiecraftmods.mdm.client.gui.OvenGuiScreen;
import com.cookiecraftmods.mdm.client.gui.FridgeGuiScreen;
import com.cookiecraftmods.mdm.client.gui.FreezerguiScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MdmModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MdmModMenus.STORAGE.get(), StorageScreen::new);
			MenuScreens.register(MdmModMenus.FREEZERGUI.get(), FreezerguiScreen::new);
			MenuScreens.register(MdmModMenus.FRIDGE_GUI.get(), FridgeGuiScreen::new);
			MenuScreens.register(MdmModMenus.OVEN_GUI.get(), OvenGuiScreen::new);
		});
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}
