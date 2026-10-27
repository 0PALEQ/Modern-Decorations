package com.cookiecraftmods.mdm.test;
import com.cookiecraftmods.eqlib.placement.PlacementOffsetAccess;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;
import com.cookiecraftmods.mdm.block.*;
import com.cookiecraftmods.eqlib.placement.*;
import com.cookiecraftmods.mdm.init.*;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.block.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
@Mod.EventBusSubscriber(modid="mdm")
public final class BackportSmokeTest {
    @SubscribeEvent public static void started(FMLServerStartedEvent event) {
        net.minecraft.server.MinecraftServer server = event.getServer();
        try {
            ServerWorld world = server.overworld();
            ServerPlayerEntity player = new ServerPlayerEntity(server, world, new GameProfile(UUID.randomUUID(), "BackportTest"), new net.minecraft.server.management.PlayerInteractionManager(world));
            BlockPos pos = new BlockPos(20, 150, 20);
            world.getChunkAt(pos);
            int count = 0, storage = 0, lights = 0;
            for (Block block : Registry.BLOCK) {
                if (!Registry.BLOCK.getKey(block).getNamespace().equals("mdm") || block instanceof FurniturePartBlock) continue;
                BlockState state = block.defaultBlockState();
                world.setBlock(pos, state, 3);
                require(world.getBlockState(pos).getBlock() == block, "Placement: " + block);
                state.getShape(world, pos);
                state.getCollisionShape(world, pos);
                TileEntity entity = world.getBlockEntity(pos);
                if (entity != null) {
                    require(entity.getType().isValid(block), "Tile entity type: " + block);
                    require(entity instanceof PlacementOffsetAccess, "Missing placement mixin");
                    ((PlacementOffsetAccess)entity).mdm$setPlacementOffset(new Vector3d(0.125, 0, -0.25));
                    CompoundNBT saved = entity.save(new CompoundNBT());
                    require(saved.getBoolean("mdm_precise"), "Placement not saved: " + block);
                    TileEntity loaded = TileEntity.loadStatic(state, saved);
                    require(loaded != null && new Vector3d(0.125, 0, -0.25).equals(((PlacementOffsetAccess)loaded).mdm$getPlacementOffset()), "Placement round trip: " + block);
                    require(entity.getUpdateTag().getBoolean("mdm_precise"), "Placement sync: " + block);
                    if (entity instanceof net.minecraft.inventory.IInventory) storage++;
                }
                if (state.hasProperty(LightSwitch.LIT)) {
                    require(LightSwitch.use(state, world, pos, player).consumesAction(), "Light interaction: " + block);
                    require(world.getBlockState(pos).getValue(LightSwitch.LIT) != state.getValue(LightSwitch.LIT), "Light toggle: " + block);
                    lights++;
                }
                world.removeBlock(pos, false);
                count++;
            }
            require(count > 300 && storage > 50 && lights > 10, "Incomplete registry: " + count + "/" + storage + "/" + lights);
            String result = "BACKPORT_SMOKE_PASSED blocks="+count+" storage="+storage+" lights="+lights;
            System.out.println(result);
            java.nio.file.Files.write(java.nio.file.Paths.get("smoke-result.txt"), result.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Throwable error) {
            error.printStackTrace();
            throw new IllegalStateException("Backport smoke test failed", error);
        } finally { server.halt(false); }
    }
    private static void require(boolean ok, String message) { if (!ok) throw new AssertionError(message); }
}

