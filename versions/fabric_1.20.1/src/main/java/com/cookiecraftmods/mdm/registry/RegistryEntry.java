package com.cookiecraftmods.mdm.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class RegistryEntry<T> {
    private final ResourceLocation id;
    private final T value;

    RegistryEntry(ResourceLocation id, T value) {
        this.id = Objects.requireNonNull(id);
        this.value = Objects.requireNonNull(value);
    }

    public T get() {
        return value;
    }

    public ResourceLocation getId() {
        return id;
    }
}
