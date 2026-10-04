package com.cookiecraftmods.mdm.registry;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A small, loader-independent registry reference used to keep registration
 * call sites type-safe without relying on a loader's deferred-register API.
 */
public final class ModRegistryEntry<T> implements Supplier<T> {
    private final Identifier id;
    private final T value;

    private ModRegistryEntry(Identifier id, T value) {
        this.id = id;
        this.value = value;
    }

    public static <R, T extends R> ModRegistryEntry<T> register(
            Registry<R> registry, String path, Supplier<T> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(MdmMod.MODID, path);
        T value = Registry.register(registry, id, Objects.requireNonNull(factory.get(), "Registry factory returned null for " + id));
        return new ModRegistryEntry<>(id, value);
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public T get() {
        return value;
    }
}
