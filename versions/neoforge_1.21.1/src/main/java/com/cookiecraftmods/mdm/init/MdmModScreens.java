/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package com.cookiecraftmods.mdm.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import com.cookiecraftmods.mdm.client.gui.StorageScreen;
import com.cookiecraftmods.mdm.client.gui.OvenGuiScreen;
import com.cookiecraftmods.mdm.client.gui.FridgeGuiScreen;
import com.cookiecraftmods.mdm.client.gui.FreezerguiScreen;

@EventBusSubscriber(Dist.CLIENT)
public class MdmModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(MdmModMenus.STORAGE.get(), StorageScreen::new);
		event.register(MdmModMenus.FREEZERGUI.get(), FreezerguiScreen::new);
		event.register(MdmModMenus.FRIDGE_GUI.get(), FridgeGuiScreen::new);
		event.register(MdmModMenus.OVEN_GUI.get(), OvenGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}