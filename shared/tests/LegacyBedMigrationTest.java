import com.cookiecraftmods.mdm.migration.LegacyBedMigration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;

public class LegacyBedMigrationTest {
    public static void main(String[] args) throws Exception {
        boolean runtime = args.length > 0 && args[0].equals("--runtime");
        String[][] ids = {
                {"bedroom_set_1white_doublebed", "bedroom_set_1white_singlebed"},
                {"bedroom_set_1grey_double_bed", "bedroom_set_1grey_single_bed"},
                {"bedroom_set_1wenge_double_bed", "bedroom_set_1wenge_single_bed"}
        };
        for (String[] pair : ids) {
            for (String facing : new String[]{"north", "east", "south", "west"}) {
                checkSavedBed("mdm:" + pair[0], "mdm:" + pair[1], facing, runtime);
            }
        }
        CompoundTag unrelated = new CompoundTag();
        unrelated.putString("id", "another_mod:bedroom_set_1white_doublebed");
        unrelated.putString("Name", "minecraft:red_bed");
        unrelated.putString("CustomName", "mdm:bedroom_set_1white_doublebed");
        unrelated.putString("description", "mdm:bedroom_set_1grey_double_bed");
        unrelated.putInt("numeric", 42);
        CompoundTag before = unrelated.copy();
        LegacyBedMigration.migrate(unrelated);
        require(before.equals(unrelated), "Unrelated data changed");
        LegacyBedMigration.migrate(null);
        System.out.println("Legacy bed migration passed: all colors/directions, inventories, metadata and repeat loads.");
    }

    private static void checkSavedBed(String oldId, String newId, String facing, boolean runtime) throws Exception {
        CompoundTag state = new CompoundTag();
        state.putString("Name", oldId);
        CompoundTag properties = new CompoundTag();
        properties.putString("facing", facing);
        state.put("Properties", properties);
        CompoundTag item = new CompoundTag();
        item.putString("id", oldId);
        item.putByte("Count", (byte) 12);
        item.putInt("count", 12);
        item.putByte("Slot", (byte) 3);
        CompoundTag metadata = new CompoundTag();
        metadata.putString("CustomName", "My stored bed");
        item.put("tag", metadata);
        item.put("components", metadata.copy());
        ListTag items = new ListTag();
        items.add(item);
        CompoundTag blockEntity = new CompoundTag();
        blockEntity.putString("id", oldId);
        blockEntity.putInt("x", -100);
        blockEntity.putInt("y", 64);
        blockEntity.putInt("z", 123);
        blockEntity.putString("CustomName", "Bed storage");
        blockEntity.put("Items", items);
        CompoundTag root = new CompoundTag();
        root.putInt("DataVersion", 99999);
        ListTag palette = new ListTag();
        palette.add(state);
        CompoundTag blockStates = new CompoundTag();
        blockStates.put("palette", palette);
        blockStates.putLongArray("data", new long[]{0, 1, Long.MAX_VALUE});
        root.put("block_states", blockStates);
        ListTag blockEntities = new ListTag();
        blockEntities.add(blockEntity);
        root.put("block_entities", blockEntities);
        root.put("Inventory", items.copy());
        root.put("EnderItems", items.copy());
        CompoundTag droppedItem = new CompoundTag();
        droppedItem.putString("id", "minecraft:item");
        droppedItem.put("Item", item.copy());
        ListTag entities = new ListTag();
        entities.add(droppedItem);
        root.put("Entities", entities);
        CompoundTag nested = new CompoundTag();
        nested.put("minecraft:container", items.copy());
        root.put("components", nested);

        CompoundTag expected = root.copy();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        NbtIo.write(expected, new DataOutputStream(bytes));
        CompoundTag loaded = NbtIo.read(new DataInputStream(new ByteArrayInputStream(bytes.toByteArray())));
        if (!runtime) LegacyBedMigration.migrate(loaded);
        CompoundTag migrated = loaded.copy();
        LegacyBedMigration.migrate(loaded);
        require(migrated.equals(loaded), "Migration is not idempotent");

        checkAndRestore(loaded, oldId, newId);
        require(expected.equals(loaded), "Lost properties, contents or metadata for " + oldId + "/" + facing);
    }

    private static void checkAndRestore(net.minecraft.nbt.Tag tag, String oldId, String newId) {
        if (tag instanceof CompoundTag compound) {
            for (String key : new String[]{"id", "Name"}) {
                require(!StringTag.valueOf(oldId).equals(compound.get(key)), "Old ID survived: " + key);
                if (StringTag.valueOf(newId).equals(compound.get(key))) compound.putString(key, oldId);
            }
            net.minecraft.nbt.NbtOps.INSTANCE.getMapValues(compound).result().orElseThrow()
                    .forEach(entry -> checkAndRestore(entry.getSecond(), oldId, newId));
        } else if (tag instanceof ListTag list) {
            for (var child : list) checkAndRestore(child, oldId, newId);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
