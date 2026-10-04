package com.cookiecraftmods.mdm.init;

import com.cookiecraftmods.mdm.client.gui.FreezerguiScreen;
import com.cookiecraftmods.mdm.client.gui.FridgeGuiScreen;
import com.cookiecraftmods.mdm.client.gui.OvenGuiScreen;
import com.cookiecraftmods.mdm.client.gui.StorageScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class MdmModScreens {
    private MdmModScreens() {
    }

    public static void register(RegisterMenuScreensEvent event) {
        event.register(MdmModMenus.STORAGE.get(), StorageScreen::new);
        event.register(MdmModMenus.FREEZERGUI.get(), FreezerguiScreen::new);
        event.register(MdmModMenus.FRIDGE_GUI.get(), FridgeGuiScreen::new);
        event.register(MdmModMenus.OVEN_GUI.get(), OvenGuiScreen::new);
    }
}
