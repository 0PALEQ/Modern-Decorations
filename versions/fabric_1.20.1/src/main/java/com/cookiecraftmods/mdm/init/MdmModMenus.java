package com.cookiecraftmods.mdm.init;

import com.cookiecraftmods.mdm.MdmMod;
import com.cookiecraftmods.mdm.registry.RegistryEntry;
import com.cookiecraftmods.mdm.registry.SimpleRegistry;
import com.cookiecraftmods.mdm.world.inventory.FreezerguiMenu;
import com.cookiecraftmods.mdm.world.inventory.FridgeGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.OvenGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.StorageMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.Map;

public final class MdmModMenus {
    public static final SimpleRegistry<MenuType<?>> REGISTRY =
            SimpleRegistry.create(BuiltInRegistries.MENU, MdmMod.MODID);

    public static final RegistryEntry<MenuType<StorageMenu>> STORAGE =
            REGISTRY.register("storage", () -> new ExtendedScreenHandlerType<>(StorageMenu::new));
    public static final RegistryEntry<MenuType<FreezerguiMenu>> FREEZERGUI =
            REGISTRY.register("freezergui", () -> new ExtendedScreenHandlerType<>(FreezerguiMenu::new));
    public static final RegistryEntry<MenuType<FridgeGuiMenu>> FRIDGE_GUI =
            REGISTRY.register("fridge_gui", () -> new ExtendedScreenHandlerType<>(FridgeGuiMenu::new));
    public static final RegistryEntry<MenuType<OvenGuiMenu>> OVEN_GUI =
            REGISTRY.register("oven_gui", () -> new ExtendedScreenHandlerType<>(OvenGuiMenu::new));

    private MdmModMenus() {
    }

    public interface MenuAccessor {
        Map<String, Object> getMenuState();

        Map<Integer, Slot> getSlots();

        default <T> T getMenuState(int elementType, String name, T defaultValue) {
            try {
                return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
            } catch (ClassCastException exception) {
                return defaultValue;
            }
        }
    }
}
