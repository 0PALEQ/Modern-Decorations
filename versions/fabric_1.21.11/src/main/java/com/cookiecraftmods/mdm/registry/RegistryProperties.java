package com.cookiecraftmods.mdm.registry;

import com.cookiecraftmods.mdm.MdmMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Supplies the registry keys that Minecraft 1.21.11 requires while blocks and
 * items are being constructed. Registration is immediate and single-threaded,
 * while ThreadLocal keeps accidental nested or concurrent registration safe.
 */
public final class RegistryProperties {
    private static final ThreadLocal<ResourceKey<Block>> BLOCK_KEY = new ThreadLocal<>();
    private static final ThreadLocal<ResourceKey<Item>> ITEM_KEY = new ThreadLocal<>();

    private RegistryProperties() {
    }

    public static <T> T withBlockKey(String path, Supplier<T> factory) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id(path));
        return withKey(BLOCK_KEY, key, factory);
    }

    public static <T> T withItemKey(String path, Supplier<T> factory) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id(path));
        return withKey(ITEM_KEY, key, factory);
    }

    public static BlockBehaviour.Properties block() {
        return BlockBehaviour.Properties.of().setId(require(BLOCK_KEY, "block"));
    }

    public static Item.Properties item() {
        return new Item.Properties().setId(require(ITEM_KEY, "item"));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MdmMod.MODID, path);
    }

    private static <K, T> T withKey(ThreadLocal<K> context, K key, Supplier<T> factory) {
        if (context.get() != null) {
            throw new IllegalStateException("Nested registry property context for " + key);
        }
        context.set(key);
        try {
            return Objects.requireNonNull(factory.get(), "Registry factory returned null for " + key);
        } finally {
            context.remove();
        }
    }

    private static <K> K require(ThreadLocal<K> context, String type) {
        return Objects.requireNonNull(context.get(), "No " + type + " registry key is active during construction");
    }
}
