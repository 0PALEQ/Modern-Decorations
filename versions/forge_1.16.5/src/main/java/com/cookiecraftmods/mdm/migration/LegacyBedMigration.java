package com.cookiecraftmods.mdm.migration;

import java.util.Map;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.StringNBT;
import net.minecraft.nbt.INBT;

public final class LegacyBedMigration {
    private static final Map<INBT, String> REPLACEMENTS = com.google.common.collect.ImmutableMap.of(
            StringNBT.valueOf("mdm:bedroom_set_1white_doublebed"), "mdm:bedroom_set_1white_singlebed",
            StringNBT.valueOf("mdm:bedroom_set_1grey_double_bed"), "mdm:bedroom_set_1grey_single_bed",
            StringNBT.valueOf("mdm:bedroom_set_1wenge_double_bed"), "mdm:bedroom_set_1wenge_single_bed");

    private LegacyBedMigration() {}

    public static void migrate(INBT tag) {
        if (tag instanceof CompoundNBT compound) {
            replaceId(compound, "Name");
            replaceId(compound, "id");
            NBTDynamicOps.INSTANCE.getMapValues(compound).result().ifPresent(entries ->
                    entries.forEach(entry -> migrate(entry.getSecond())));
        } else if (tag instanceof ListNBT list) {
            for (INBT child : list) {
                migrate(child);
            }
        }
    }

    private static void replaceId(CompoundNBT compound, String key) {
        INBT value = compound.get(key);
        if (value instanceof StringNBT) {
            String replacement = REPLACEMENTS.get(value);
            if (replacement != null) {
                compound.putString(key, replacement);
            }
        }
    }
}
