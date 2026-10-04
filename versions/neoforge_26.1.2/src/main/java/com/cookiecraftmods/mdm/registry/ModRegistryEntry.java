package com.cookiecraftmods.mdm.registry;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A small typed facade over NeoForge deferred holders. Keeping this facade
 * avoids changing hundreds of generated block and block-entity call sites.
 */
public final class ModRegistryEntry<T> implements Supplier<T> {
    private static final Map<Registry<?>, DeferredRegister<?>> REGISTERS = new IdentityHashMap<>();
    private static final List<DeferredRegister<?>> REGISTER_ORDER = new ArrayList<>();

    private final Identifier id;
    private final Supplier<T> value;

    private ModRegistryEntry(Identifier id, Supplier<T> value) {
        this.id = id;
        this.value = value;
    }

    public static <R, T extends R> ModRegistryEntry<T> register(
            Registry<R> registry, String path, Supplier<T> factory) {
        DeferredRegister<R> deferredRegister = deferredRegister(registry);
        DeferredHolder<R, T> holder = deferredRegister.register(path, factory);
        return new ModRegistryEntry<>(holder.getId(), holder);
    }

    @SuppressWarnings("unchecked")
    private static <R> DeferredRegister<R> deferredRegister(Registry<R> registry) {
        DeferredRegister<?> existing = REGISTERS.get(registry);
        if (existing != null) {
            return (DeferredRegister<R>) existing;
        }

        DeferredRegister<R> created = DeferredRegister.create(registry, MdmMod.MODID);
        REGISTERS.put(registry, created);
        REGISTER_ORDER.add(created);
        return created;
    }

    public static void registerAll(IEventBus modEventBus) {
        REGISTER_ORDER.forEach(register -> register.register(modEventBus));
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public T get() {
        return value.get();
    }
}
