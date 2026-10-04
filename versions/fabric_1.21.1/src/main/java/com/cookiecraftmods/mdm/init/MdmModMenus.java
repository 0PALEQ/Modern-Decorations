package com.cookiecraftmods.mdm.init;

import com.cookiecraftmods.mdm.registry.ModRegistryEntry;
import com.cookiecraftmods.mdm.world.inventory.FreezerguiMenu;
import com.cookiecraftmods.mdm.world.inventory.FridgeGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.OvenGuiMenu;
import com.cookiecraftmods.mdm.world.inventory.StorageMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.Map;

public final class MdmModMenus {
    public static final ModRegistryEntry<MenuType<StorageMenu>> STORAGE = register(
            "storage", new MenuType<>(StorageMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final ModRegistryEntry<MenuType<FreezerguiMenu>> FREEZERGUI = register(
            "freezergui", new MenuType<>(FreezerguiMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final ModRegistryEntry<MenuType<FridgeGuiMenu>> FRIDGE_GUI = register(
            "fridge_gui", new MenuType<>(FridgeGuiMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final ModRegistryEntry<MenuType<OvenGuiMenu>> OVEN_GUI = register(
            "oven_gui", new MenuType<>(OvenGuiMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private MdmModMenus() {
    }

    private static <T extends net.minecraft.world.inventory.AbstractContainerMenu>
    ModRegistryEntry<MenuType<T>> register(String name, MenuType<T> menuType) {
        return ModRegistryEntry.register(BuiltInRegistries.MENU, name, () -> menuType);
    }

    public static void init() {
        // Invoking this method initializes the static registrations.
    }

    public interface MenuAccessor {
        Map<String, Object> getMenuState();

        Map<Integer, Slot> getSlots();

        @SuppressWarnings("unchecked")
        default <T> T getMenuState(int elementType, String name, T defaultValue) {
            try {
                return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
            } catch (ClassCastException ignored) {
                return defaultValue;
            }
        }
    }
}
