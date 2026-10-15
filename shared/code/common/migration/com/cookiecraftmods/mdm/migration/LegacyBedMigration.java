package com.cookiecraftmods.mdm.migration;

import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public final class LegacyBedMigration {
    private static final Map<Tag, String> REPLACEMENTS = Map.of(
            StringTag.valueOf("mdm:bedroom_set_1white_doublebed"), "mdm:bedroom_set_1white_singlebed",
            StringTag.valueOf("mdm:bedroom_set_1grey_double_bed"), "mdm:bedroom_set_1grey_single_bed",
            StringTag.valueOf("mdm:bedroom_set_1wenge_double_bed"), "mdm:bedroom_set_1wenge_single_bed");

    private LegacyBedMigration() {}

    public static void migrate(Tag tag) {
        if (tag instanceof CompoundTag compound) {
            replaceId(compound, "Name");
            replaceId(compound, "id");
            NbtOps.INSTANCE.getMapValues(compound).result().ifPresent(entries ->
                    entries.forEach(entry -> migrate(entry.getSecond())));
        } else if (tag instanceof ListTag list) {
            for (Tag child : list) {
                migrate(child);
            }
        }
    }

    private static void replaceId(CompoundTag compound, String key) {
        Tag value = compound.get(key);
        if (value instanceof StringTag) {
            String replacement = REPLACEMENTS.get(value);
            if (replacement != null) {
                compound.putString(key, replacement);
            }
        }
    }
}
