import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.List;
import net.minecraft.SharedConstants;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

/** Decode the shipped JSON using Minecraft 26.3, then exercise crafting. */
public final class DyedBedRecipeTest {
    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        var frozen = MappedRegistry.class.getDeclaredField("frozen");
        frozen.setAccessible(true);
        frozen.set(BuiltInRegistries.ITEM, false);
        var holders = MappedRegistry.class.getDeclaredField("unregisteredIntrusiveHolders");
        holders.setAccessible(true);
        holders.set(BuiltInRegistries.ITEM, new IdentityHashMap<>());

        Path directory = Path.of(args[0], "data/mdm/recipe");
        List<Path> recipes;
        try (var files = Files.list(directory)) {
            recipes = files.filter(p -> p.getFileName().toString().matches("bedroom_set_1.*single.*bed_.+\\.json")).sorted().toList();
        }
        require(recipes.size() == 45, "Expected 45 dye recipes");
        // Stand-in items isolate recipe loading from loader initialization.
        // Their registry IDs are the same IDs the real bed items use.
        for (Path file : recipes) {
            var json = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
            register(json.getAsJsonArray("ingredients").get(0).getAsString());
            register(json.getAsJsonObject("result").get("id").getAsString());
        }
        // 26.3 normally binds item defaults during datapack loading. Minimal
        // stack defaults are sufficient for this isolated crafting test.
        var defaults = DataComponentMap.builder().set(DataComponents.MAX_STACK_SIZE, 64).build();
        BuiltInRegistries.ITEM.listElements().filter(holder -> !holder.areComponentsBound())
                .forEach(holder -> holder.bindComponents(defaults));
        // Bootstrap has already installed tags; preserve them when restoring
        // the frozen state instead of trying to freeze those tags a second time.
        frozen.set(BuiltInRegistries.ITEM, true);
        var ops = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY).createSerializationContext(JsonOps.INSTANCE);
        for (Path file : recipes) {
            JsonObject json = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
            var decoded = Recipe.DIRECT_CODEC.parse(ops, json).getOrThrow();
            require(decoded instanceof ShapelessRecipe, "Not shapeless: " + file);
            var recipe = (ShapelessRecipe) decoded;
            Item bed = BuiltInRegistries.ITEM.getValue(Identifier.parse(json.getAsJsonArray("ingredients").get(0).getAsString()));
            Item dye = BuiltInRegistries.ITEM.getValue(Identifier.parse(json.getAsJsonArray("ingredients").get(1).getAsString()));
            var input = CraftingInput.of(2, 1, List.of(new ItemStack(bed), new ItemStack(dye)));
            require(recipe.matches(input, null), "Matching bed and dye rejected: " + file);
            require(recipe.matches(CraftingInput.of(2, 1, List.of(new ItemStack(dye), new ItemStack(bed))), null), "Ingredient order matters: " + file);
            require(!recipe.matches(CraftingInput.of(2, 1, List.of(new ItemStack(bed), new ItemStack(Items.STICK))), null), "Wrong ingredient accepted: " + file);
            var result = recipe.assemble(input);
            require(result.getCount() == 1 && BuiltInRegistries.ITEM.getKey(result.getItem()).toString().equals(json.getAsJsonObject("result").get("id").getAsString()), "Wrong crafted bed: " + file);
            json.addProperty("category", "decorations");
            require(Recipe.DIRECT_CODEC.parse(ops, json).error().isPresent(), "Invalid category regression was not detected");
        }
        System.out.println("45 bed recipes passed Minecraft 26.3 decoding, shapeless crafting, outputs, and invalid-category regression.");
    }

    private static void register(String name) {
        Identifier id = Identifier.parse(name);
        if (BuiltInRegistries.ITEM.containsKey(id)) return;
        Registry.registerForHolder(BuiltInRegistries.ITEM, id,
                new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
