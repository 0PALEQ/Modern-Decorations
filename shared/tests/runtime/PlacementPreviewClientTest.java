import com.cookiecraftmods.mdm.client.placement.PlacementPreview;
import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import java.nio.file.Path;

/** Exercises the actual world renderer and saves valid/invalid placement screenshots. Fabric 1.21.1 only. */
public final class PlacementPreviewClientTest implements ClientModInitializer {
    private int ticks, phase;
    @Override public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            if (phase >= 2 || mc.player == null || mc.level == null || mc.getSingleplayerServer() == null) return;
            try {
                if (ticks++ == 0) {
                    mc.getSingleplayerServer().execute(() -> {
                        var server = mc.getSingleplayerServer();
                        var level = server.overworld();
                        for (var pos : BlockPos.betweenClosed(new BlockPos(-5,199,-5),new BlockPos(5,204,5))) {
                            level.setBlockAndUpdate(pos, pos.getY() == 199 ? Blocks.STONE.defaultBlockState() : Blocks.AIR.defaultBlockState());
                        }
                        level.setBlockAndUpdate(new BlockPos(0,201,3), Blocks.DIRT.defaultBlockState());
                        var player = server.getPlayerList().getPlayers().get(0);
                        player.connection.teleport(0.5,200,0.5,0,0);
                        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("mdm","electric_guitar_red"))));
                        player.inventoryMenu.broadcastChanges();
                        level.setDayTime(6000);
                    });
                }
                mc.options.keyShift.setDown(true);
                mc.player.setYRot(0);
                mc.player.setXRot(0);
                if (ticks < 100 || (phase == 1 && ticks < 120)) return;
                var preview = PlacementPreview.current();
                if (preview == null) throw new AssertionError("No in-world placement preview");
                if (phase == 0 && !preview.valid()) throw new AssertionError("Initial preview is invalid");
                if (phase == 1 && preview.valid()) {
                    if (ticks > 160) throw new AssertionError("Obstructed preview did not turn invalid");
                    return;
                }
                Path output = mc.gameDirectory.toPath().resolve(phase == 0 ? "preview-valid.png" : "preview-blocked.png");
                try (var image = Screenshot.takeScreenshot(mc.getMainRenderTarget())) { image.writeToFile(output); }
                System.out.println("PLACEMENT_PREVIEW_SCREENSHOT: " + output.toAbsolutePath());
                if (phase++ == 0) {
                    var obstruction = preview.pos().offset(FurnitureFootprint.offsets(preview.shape()).get(0));
                    mc.getSingleplayerServer().execute(() -> mc.getSingleplayerServer().overworld().setBlockAndUpdate(obstruction, Blocks.STONE.defaultBlockState()));
                    ticks = 100;
                } else {
                    System.out.println("PLACEMENT_PREVIEW_CLIENT_PASSED");
                    mc.stop();
                }
            } catch (Throwable failure) { failure.printStackTrace(); Runtime.getRuntime().halt(1); }
        });
    }
}
