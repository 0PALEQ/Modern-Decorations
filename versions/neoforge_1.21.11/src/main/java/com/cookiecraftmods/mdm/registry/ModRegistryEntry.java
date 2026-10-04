package com.cookiecraftmods.mdm.registry;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * A small typed facade over NeoForge deferred holders. It lets the generated
 * registration classes keep their stable public API while respecting the
 * NeoForge registry lifecycle.
 */
public final class ModRegistryEntry<T> implements Supplier<T> {
    private static final Map<Registry<?>, DeferredRegister<?>> REGISTERS = new IdentityHashMap<>();
    private static final Set<DeferredRegister<?>> ATTACHED = new LinkedHashSet<>();

    private final Identifier id;
    private final Supplier<T> holder;

    private ModRegistryEntry(Identifier id, Supplier<T> holder) {
        this.id = id;
        this.holder = holder;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <R, T extends R> ModRegistryEntry<T> register(
            Registry<R> registry, String path, Supplier<T> factory) {
        DeferredRegister<R> register = (DeferredRegister<R>) REGISTERS.computeIfAbsent(
                registry, ignored -> DeferredRegister.create(registry, MdmMod.MODID));
        DeferredHolder<R, T> holder = register.register(path, factory);
        return new ModRegistryEntry<>(Identifier.fromNamespaceAndPath(MdmMod.MODID, path), holder);
    }

    public static synchronized void attachAll(IEventBus modEventBus) {
        for (DeferredRegister<?> register : REGISTERS.values()) {
            if (ATTACHED.add(register)) {
                register.register(modEventBus);
            }
        }
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public T get() {
        return holder.get();
    }
}
