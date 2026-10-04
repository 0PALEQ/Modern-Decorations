package com.cookiecraftmods.mdm.init;

import com.cookiecraftmods.mdm.client.gui.FreezerguiScreen;
import com.cookiecraftmods.mdm.client.gui.FridgeGuiScreen;
import com.cookiecraftmods.mdm.client.gui.OvenGuiScreen;
import com.cookiecraftmods.mdm.client.gui.StorageScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public final class MdmModScreens {
    private MdmModScreens() {
    }

    public static void initialize() {
        MenuScreens.register(MdmModMenus.STORAGE.get(), StorageScreen::new);
        MenuScreens.register(MdmModMenus.FREEZERGUI.get(), FreezerguiScreen::new);
        MenuScreens.register(MdmModMenus.FRIDGE_GUI.get(), FridgeGuiScreen::new);
        MenuScreens.register(MdmModMenus.OVEN_GUI.get(), OvenGuiScreen::new);
    }

    public interface ScreenAccessor {
        void updateMenuState(int elementType, String name, Object elementState);
    }
}
