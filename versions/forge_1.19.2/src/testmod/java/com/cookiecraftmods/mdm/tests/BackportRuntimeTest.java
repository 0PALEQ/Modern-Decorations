package com.cookiecraftmods.mdm.tests;
import com.cookiecraftmods.eqlib.placement.PlacementOffsetAccess;
import com.cookiecraftmods.eqlib.placement.PrecisePlacement;
import com.cookiecraftmods.eqlib.block.FurnitureFootprint;
import com.cookiecraftmods.eqlib.block.FurniturePartBlock;

import com.cookiecraftmods.mdm.block.*;
import com.cookiecraftmods.mdm.init.*;
import com.cookiecraftmods.eqlib.placement.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import java.nio.file.*;
import java.util.UUID;

@Mod("mdm_backport_test")
public final class BackportRuntimeTest {
    private int blocks, lights, beds, containers, removed;
    public BackportRuntimeTest() {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.addListener(this::started);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.addListener(this::stopped);
    }
    private static void require(boolean ok, String message) { if (!ok) throw new AssertionError(message); }
    private void started(ServerStartedEvent event) {
        try {
            var server = event.getServer();
            var level = server.overworld();
            var pos = new BlockPos(40, 200, 40);
            level.getChunkAt(pos);
            var player = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), "BackportTest"), null);
            player.connection = new net.minecraft.server.network.ServerGamePacketListenerImpl(server,
                    new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND), player);
            level.setDayTime(18000);
            level.updateSkyBrightness();
            var tabItems = net.minecraft.core.NonNullList.<ItemStack>create();
            MdmModTabs.OFFICE.fillItemList(tabItems);
            require(!tabItems.isEmpty(), "Empty creative tab");
            for (var entry : MdmModBlocks.REGISTRY.getEntries()) {
                var block = entry.get();
                if (block instanceof FurniturePartBlock) continue;
                blocks++;
                var state = block.defaultBlockState();
                require(level.setBlock(pos, state, 3), "Cannot place " + entry.getId());
                var entity = level.getBlockEntity(pos);
                require(entity != null, "Missing placement entity " + entry.getId());
                require(entity instanceof PlacementOffsetAccess, "Missing offset mixin");
                ((PlacementOffsetAccess)entity).mdm$setPlacementOffset(new Vec3(0.125, 0.25, -0.125));
                var saved = entity.saveWithoutMetadata();
                ((PlacementOffsetAccess)entity).mdm$setPlacementOffset(null);
                entity.load(saved);
                require(PrecisePlacement.offset(level, pos).equals(new Vec3(0.125, 0.25, -0.125)), "Offset save/load " + entry.getId());
                for (var direction : Direction.Plane.HORIZONTAL) {
                    var rotated = state.hasProperty(HorizontalDirectionalBlock.FACING)
                            ? state.setValue(HorizontalDirectionalBlock.FACING, direction) : state;
                    require(!PrecisePlacement.rawShape(rotated, level, pos, false).isEmpty(), "Empty shape " + entry.getId());
                }
                if (state.hasProperty(LightSwitch.LIT)) {
                    lights++;
                    for (int i=0; i<10; i++) {
                        var before=level.getBlockState(pos);
                        require(LightSwitch.use(before,level,pos,player).consumesAction(), "Light interaction");
                        require(level.getBlockState(pos).getValue(LightSwitch.LIT) != before.getValue(LightSwitch.LIT), "Light toggle");
                    }
                }
                if (block instanceof SingleBedBlock) {
                    beds++;
                    for (var direction : Direction.Plane.HORIZONTAL) {
                        var oriented=state.setValue(BedBlock.FACING,direction);
                        level.setBlock(pos,oriented,3);
                        ((PlacementOffsetAccess)level.getBlockEntity(pos)).mdm$setPlacementOffset(new Vec3(0.125,0.25,-0.125));
                        player.setPos(pos.getX()+0.5,pos.getY(),pos.getZ()+0.5);
                        var sleep=player.startSleepInBed(pos);
                        require(sleep.right().isPresent(), "Cannot sleep in "+entry.getId()+" "+direction+": "+sleep);
                        require(player.getBedOrientation()==direction.getOpposite(),"Sleeping orientation");
                        require(Math.abs(player.getX()-(pos.getX()+0.625))<0.001,"Sleeping X offset");
                        player.stopSleepInBed(true,true);
                    }
                    level.setBlock(pos,state,3);
                    require(SingleBedBlock.sleepDirection(state) == state.getValue(BedBlock.FACING).getOpposite(), "Bed orientation");
                }
                if (entity instanceof net.minecraft.world.Container inventory) {
                    containers++;
                    inventory.setItem(0,new ItemStack(Items.DIAMOND,3));
                    var inventoryTag=entity.saveWithoutMetadata();
                    inventory.clearContent(); entity.load(inventoryTag);
                    require(inventory.getItem(0).is(Items.DIAMOND) && inventory.getItem(0).getCount()==3,"Inventory save/load");
                    inventory.clearContent();
                }
                FurnitureFootprint.placeParts(level,pos,state);
                for (var offset : FurnitureFootprint.offsets(FurnitureFootprint.fullShape(state,level,pos))) {
                    require(level.getBlockState(pos.offset(offset)).getBlock() instanceof FurniturePartBlock,"Missing footprint " + entry.getId());
                }
                level.removeBlock(pos,false);
            }
            require(blocks>200 && lights>0 && beds>=48 && containers>0,"Incomplete registry coverage");
            for (var entry : MdmModItems.REGISTRY.getEntries()) {
                if (java.util.Set.of("bedroom_set_1grey_double_bed", "bedroom_set_1wenge_double_bed", "bedroom_set_1white_doublebed").contains(entry.getId().getPath())) continue;
                require(tabItems.stream().anyMatch(stack -> stack.is(entry.get())),"Item missing from tab: " + entry.getId());
            }
            player.setPos(90,200,90);
            player.setShiftKeyDown(true);
            var support=new BlockPos(80,200,80);
            level.setBlock(support,Blocks.STONE.defaultBlockState(),3);
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                    new ItemStack(MdmModBlocks.ELECTRIC_GUITAR_BLACK.get(),16));
            var hit=new net.minecraft.world.phys.BlockHitResult(new Vec3(80.25,201,80.75),Direction.UP,support,false);
            var use=new net.minecraft.world.item.context.UseOnContext(player,net.minecraft.world.InteractionHand.MAIN_HAND,hit);
            var context=new net.minecraft.world.item.context.BlockPlaceContext(use);
            var plan=PrecisePlacement.plan(context);
            require(plan.valid(),"Precise placement plan invalid");
            require(player.getMainHandItem().useOn(use).consumesAction(),"Precise item placement failed");
            require(level.getBlockState(plan.pos()).is(MdmModBlocks.ELECTRIC_GUITAR_BLACK.get()),"Placed wrong block");
            require(PrecisePlacement.offset(level,plan.pos()).equals(plan.offset()),"Committed placement differs from preview");
            level.removeBlock(plan.pos(),false);
            // Shutdown must save and unload a precise placement spanning chunk edges.
            PrecisePlacement.clientDataRemoved = entity -> {
                if (entity instanceof PlacementOffsetAccess data && data.mdm$getPlacementOffset()!=null) removed++;
            };
            var edge=new BlockPos(175,200,175);
            level.getChunkAt(edge);
            var guitar=MdmModBlocks.ELECTRIC_GUITAR_BLACK.get().defaultBlockState();
            level.setBlock(edge,guitar,3);
            ((PlacementOffsetAccess)level.getBlockEntity(edge)).mdm$setPlacementOffset(new Vec3(0.5,0.5,0.5));
            FurnitureFootprint.placeParts(level,edge,guitar);
            server.halt(false);
        } catch(Throwable failure) { failure.printStackTrace(); Runtime.getRuntime().halt(1); }
    }
    private void stopped(ServerStoppedEvent event) {
        require(removed>0,"Precise entities did not unload");
        var result="BACKPORT_RUNTIME_PASSED: blocks="+blocks+", lights="+lights+", beds="+beds+", containers="+containers+", unloaded="+removed;
        try { Files.writeString(Path.of("backport-result.txt"),result); }
        catch(java.io.IOException failure) { throw new java.io.UncheckedIOException(failure); }
        System.out.println(result);
    }
}
