package com.cookiecraftmods.mdm.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class SimpleRegistry<T> {
    private final Registry<T> registry;
    private final String namespace;
    private final List<RegistryEntry<? extends T>> entries = new ArrayList<>();

    private SimpleRegistry(Registry<T> registry, String namespace) {
        this.registry = registry;
        this.namespace = namespace;
    }

    public static <T> SimpleRegistry<T> create(Registry<T> registry, String namespace) {
        return new SimpleRegistry<>(registry, namespace);
    }

    public <V extends T> RegistryEntry<V> register(String path, Supplier<V> supplier) {
        ResourceLocation id = new ResourceLocation(namespace, path);
        V value = Registry.register(registry, id, supplier.get());
        RegistryEntry<V> entry = new RegistryEntry<>(id, value);
        entries.add(entry);
        return entry;
    }

    public List<RegistryEntry<? extends T>> entries() {
        return Collections.unmodifiableList(entries);
    }

    public void initialize() {
        // Loading the owner class performs these immediate vanilla registrations.
    }
}
