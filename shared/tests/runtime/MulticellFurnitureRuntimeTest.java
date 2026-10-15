import com.cookiecraftmods.mdm.block.FurnitureFootprint;
import com.cookiecraftmods.mdm.block.FurniturePartBlock;
import com.cookiecraftmods.mdm.block.MulticellFurniture;
import com.cookiecraftmods.mdm.init.MdmModBlocks;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class MulticellFurnitureRuntimeTest implements ModInitializer {
    private static final Set<String> EXPECTED = Set.of(
            "BedroomSet1whitewardrobeBlock", "BedroomSet1wengewardrobeBlock", "BedroomSet1greywardrobeBlock",
            "BedroomSet1whiteSinglebedBlock", "BedroomSet1wengeSingleBedBlock", "BedroomSet1greySingleBedBlock",
            "FoyerWallUnitBottomBlock", "FoyerWallUnitTopBlock", "WhiteWoodFoyerWallUnitBlock",
            "WhiteWoodFoyerWallUnitTopBlock", "BlackFoyerWallUnitBlock", "BlackFoyerWallUnitTopBlock",
            "Set1OvenMicrowaveBlock", "Set1FridgeBlock", "ShelfBlock", "HangingShelfBlock",
            "OfficeShelfBlock", "SteelShelfBlock", "BathroomStandBlock", "BlackModernFridge1Block",
            "TallMirrorBlock", "UniversalHangingShelfWithSinkDoubleBlock", "UniversalHangingShelfWithSinkDoubleOddBlock", "StandingWhiteBoardBlock",
            "WhiteBoardBlock", "CorkBoardBlock", "BenchBlock", "WhiteBenchBlock",
            "BedroomSet1greyBunkBedBlock", "BedroomSet1wengebunkbedBlock", "BedroomSet1whitebunkbedBlock",
            "BlackOfficeDeskBlock", "WhiteBathtubBlock",
            "BedroomSet1whiteloveseatBlock", "BedroomSet1greyloveseatBlock", "Bedroomset1BlackLoveseatBlock");
    private static final BlockPos POS = new BlockPos(0, 200, 0);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            try {
                run(server);
                System.out.println("MULTICELL_FURNITURE_RUNTIME_PASSED");
                Runtime.getRuntime().halt(0);
            } catch (Throwable failure) {
                failure.printStackTrace();
                Runtime.getRuntime().halt(1);
            }
        });
    }

    private static void run(MinecraftServer server) throws Exception {
        ServerLevel level = server.overworld();
        var profile = new GameProfile(UUID.randomUUID(), "FurnitureTest");
        ServerPlayer player = new ServerPlayer(server, level, profile, ClientInformation.createDefault());
        player.connection = new ServerGamePacketListenerImpl(server, new Connection(PacketFlow.SERVERBOUND),
                player, CommonListenerCookie.createInitial(profile, false));
        player.setPos(0.5, 200, 4.5);
        List<Block> targets = BuiltInRegistries.BLOCK.stream().filter(b -> b instanceof MulticellFurniture).toList();
        require(targets.stream().map(b -> b.getClass().getSimpleName()).collect(Collectors.toSet()).equals(EXPECTED),
                "Only the requested furniture types should opt in");
        int cases = 0;
        for (Block block : targets) {
            for (Direction facing : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
                player.setYRot(facing.getOpposite().toYRot());
                clear(level);
                BlockPlaceContext context = context(level, player, block, POS);
                BlockState state = block.getStateForPlacement(context);
                require(state != null, "Empty footprint rejected: " + block);
                List<BlockPos> offsets = FurnitureFootprint.offsets(FurnitureFootprint.fullShape(state, level, POS));
                require(!offsets.isEmpty(), "Missing occupied cells: " + block);
                BlockPos obstruction = POS.offset(offsets.get(0));
                level.setBlock(obstruction, Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
                require(block.getStateForPlacement(context) == null, "Obstruction accepted: " + block);
                level.removeBlock(obstruction, false);
                place(level, player, block, POS);
                require(level.getBlockState(POS).is(block), "Parent missing");
                require(Set.of("BLOCK", "IMMOVEABLE").contains(state.getPistonPushReaction().name()), "Parent can be pushed");
                for (BlockPos offset : offsets) {
                    BlockPos cell = POS.offset(offset);
                    BlockState part = level.getBlockState(cell);
                    require(part.getBlock() instanceof FurniturePartBlock, "Missing helper: " + block + " " + offset);
                    require(FurniturePartBlock.parentPos(part, cell).equals(POS), "Incorrect parent offset");
                    require(level.getBlockEntity(cell) == null, "Helper must not own inventory");
                    require(part.getRenderShape() == RenderShape.INVISIBLE, "Helper renders");
                    var expected = FurnitureFootprint.localShape(FurnitureFootprint.fullShape(state, level, POS), offset);
                    require(!Shapes.joinIsNotEmpty(expected, part.getShape(level, cell), BooleanOp.NOT_SAME), "Wrong helper shape");
                    var collision = ((MulticellFurniture) block).getFurnitureCollisionShape(state, level, POS, CollisionContext.empty());
                    require(!Shapes.joinIsNotEmpty(FurnitureFootprint.localShape(collision, offset),
                            part.getCollisionShape(level, cell), BooleanOp.NOT_SAME), "Wrong helper collision shape");
                    var box = expected.toAabbs().get(0);
                    Vec3 center = new Vec3(cell.getX() + (box.minX + box.maxX) / 2,
                            cell.getY() + (box.minY + box.maxY) / 2, cell.getZ() + (box.minZ + box.maxZ) / 2);
                    Vec3 start = offset.getX() != 0
                            ? new Vec3(cell.getX() + (offset.getX() > 0 ? box.maxX + 0.01 : box.minX - 0.01), center.y, center.z)
                            : offset.getZ() != 0
                            ? new Vec3(center.x, center.y, cell.getZ() + (offset.getZ() > 0 ? box.maxZ + 0.01 : box.minZ - 0.01))
                            : new Vec3(center.x, cell.getY() + (offset.getY() > 0 ? box.maxY + 0.01 : box.minY - 0.01), center.z);
                    var hit = level.clip(new ClipContext(start, center, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
                    require(hit.getBlockPos().equals(cell), "Cannot target occupied cell: " + block + " " + cell + " hit " + hit.getBlockPos());
                    require(pick(level, cell, part).is(block.asItem()), "Pick block loses furniture");
                }
                if (level.getBlockEntity(POS) instanceof Container container) container.setItem(0, new ItemStack(Items.DIAMOND, 3));
                boolean inventory = level.getBlockEntity(POS) instanceof Container;
                level.destroyBlock(POS.offset(offsets.get(offsets.size() - 1)), true);
                require(level.getBlockState(POS).isAir(), "Helper did not destroy parent");
                for (BlockPos offset : offsets) require(!(level.getBlockState(POS.offset(offset)).getBlock() instanceof FurniturePartBlock), "Orphan helper");
                require(drops(level, block.asItem()) == 1, "Duplicate/missing furniture drop: " + block);
                if (inventory) require(drops(level, Items.DIAMOND) == 3, "Inventory lost/duplicated");
                clear(level);
                place(level, player, block, POS);
                level.destroyBlock(POS, true);
                for (BlockPos offset : offsets) require(!(level.getBlockState(POS.offset(offset)).getBlock() instanceof FurniturePartBlock), "Parent left helpers");
                require(drops(level, block.asItem()) == 1, "Wrong parent drops");
                clear(level);
                place(level, player, block, POS);
                BlockPos cell = POS.offset(offsets.get(0));
                BlockState part = level.getBlockState(cell);
                player.setGameMode(net.minecraft.world.level.GameType.CREATIVE);
                part.getBlock().playerWillDestroy(level, cell, part, player);
                level.removeBlock(cell, false);
                player.setGameMode(net.minecraft.world.level.GameType.SURVIVAL);
                require(level.getBlockState(POS).isAir() && drops(level, block.asItem()) == 0, "Creative helper drops furniture: " + block);
                cases++;
            }
        }
        checkInventoryInteraction(level, player);
        checkLoveseatInteraction(level, player);
        checkWater(level, player);
        checkFoyer(level, player);
        checkExistingWorldRepair(level, player);
        checkOrphan(level);
        checkUnlisted(level, player);
        checkAdditionalFurniture(level, player);
        System.out.println("Verified " + cases + " furniture/facing combinations, inventories, water, and stacked foyers");
    }

    private static void checkInventoryInteraction(ServerLevel level, ServerPlayer player) {
        clear(level);
        Block block = MdmModBlocks.BEDROOM_SET_1WHITEWARDROBE.get();
        place(level, player, block, POS);
        BlockPos cell = POS.above();
        level.getBlockState(cell).useWithoutItem(level, player, new BlockHitResult(Vec3.atCenterOf(cell), Direction.NORTH, cell, false));
        require(player.containerMenu != player.inventoryMenu, "Upper wardrobe did not open parent menu");
        player.containerMenu = player.inventoryMenu;
    }

    private static void checkLoveseatInteraction(ServerLevel level, ServerPlayer player) {
        Block block = MdmModBlocks.BEDROOM_SET_1WHITELOVESEAT.get();
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            clear(level);
            player.setYRot(facing.getOpposite().toYRot());
            place(level, player, block, POS);
            var offsets = FurnitureFootprint.offsets(FurnitureFootprint.fullShape(level.getBlockState(POS), level, POS));
            require(offsets.size() == 1 && offsets.get(0).getY() == 0, "Loveseat must occupy exactly two horizontal cells");
            BlockPos cell = POS.offset(offsets.get(0));
            level.getBlockState(cell).useWithoutItem(level, player, new BlockHitResult(Vec3.atCenterOf(cell), Direction.NORTH, cell, false));
            require(player.containerMenu != player.inventoryMenu, "Loveseat helper did not open parent inventory");
            player.containerMenu = player.inventoryMenu;
        }
    }

    private static void checkWater(ServerLevel level, ServerPlayer player) {
        clear(level);
        level.setBlock(POS.above(), Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
        place(level, player, MdmModBlocks.STEEL_SHELF.get(), POS);
        require(level.getBlockState(POS.above()).getFluidState().isSource(), "Helper lost water");
        level.destroyBlock(POS, false);
        require(level.getBlockState(POS.above()).is(Blocks.WATER), "Removal lost water");
    }

    private static void checkFoyer(ServerLevel level, ServerPlayer player) {
        for (boolean topFirst : new boolean[]{false, true}) {
            clear(level);
            if (topFirst) {
                place(level, player, MdmModBlocks.FOYER_WALL_UNIT_TOP.get(), POS.above());
                place(level, player, MdmModBlocks.FOYER_WALL_UNIT_BOTTOM.get(), POS);
            } else {
                place(level, player, MdmModBlocks.FOYER_WALL_UNIT_BOTTOM.get(), POS);
                place(level, player, MdmModBlocks.FOYER_WALL_UNIT_TOP.get(), POS.above());
            }
            require(level.getBlockState(POS).is(MdmModBlocks.FOYER_WALL_UNIT_BOTTOM.get()), "Foyer bottom lost");
            require(level.getBlockState(POS.above()).is(MdmModBlocks.FOYER_WALL_UNIT_TOP.get()), "Foyer top lost");
            BlockState bottom = level.getBlockState(POS);
            BlockState upper = level.getBlockState(POS.above());
            for (BlockPos offset : FurnitureFootprint.offsets(FurnitureFootprint.fullShape(bottom, level, POS))) {
                BlockPos shared = POS.offset(offset);
                var bottomShape = FurnitureFootprint.localShape(FurnitureFootprint.fullShape(bottom, level, POS), offset);
                var upperShape = FurnitureFootprint.localShape(FurnitureFootprint.fullShape(upper, level, POS.above()), shared.subtract(POS.above()));
                if (!upperShape.isEmpty()) {
                    require(!Shapes.joinIsNotEmpty(Shapes.or(bottomShape, upperShape), level.getBlockState(shared).getShape(level, shared), BooleanOp.NOT_SAME),
                            "Shared foyer cell lost part of its hitbox");
                }
            }
            level.destroyBlock(POS, false);
            BlockState top = level.getBlockState(POS.above());
            ((com.cookiecraftmods.mdm.block.FurnitureBlock) top.getBlock()).tick(top, level, POS.above(), level.getRandom());
            require(level.getBlockState(POS).getBlock() instanceof FurniturePartBlock, "Foyer did not refill shared cell");
        }
        clear(level);
        place(level, player, MdmModBlocks.FOYER_WALL_UNIT_BOTTOM.get(), POS);
        place(level, player, MdmModBlocks.FOYER_WALL_UNIT_TOP.get(), POS.above());
        BlockState bottom = level.getBlockState(POS);
        BlockPos shared = POS.offset(FurnitureFootprint.offsets(FurnitureFootprint.fullShape(bottom, level, POS)).get(0));
        level.destroyBlock(shared, true);
        require(level.getBlockState(POS).isAir() && level.getBlockState(POS.above()).isAir(), "Shared foyer removal left furniture behind");
        require(drops(level, MdmModBlocks.FOYER_WALL_UNIT_BOTTOM.get().asItem()) == 1
                && drops(level, MdmModBlocks.FOYER_WALL_UNIT_TOP.get().asItem()) == 1, "Shared foyer duplicated drops");
    }

    private static void checkOrphan(ServerLevel level) {
        clear(level);
        BlockPos cell = POS.above();
        BlockState orphan = FurniturePartBlock.forOffset(new BlockPos(0, 1, 0), Blocks.WATER.defaultBlockState());
        level.setBlock(cell, orphan, Block.UPDATE_ALL);
        ((FurniturePartBlock) orphan.getBlock()).tick(orphan, level, cell, level.getRandom());
        require(level.getBlockState(cell).is(Blocks.WATER), "Orphan helper did not clean up and restore water");
    }

    private static void checkUnlisted(ServerLevel level, ServerPlayer player) {
        clear(level);
        Block block = MdmModBlocks.OVEN.get();
        place(level, player, block, POS);
        require(level.getBlockState(POS.above()).isAir(), "Unlisted furniture reserved cells");
    }

    private static void checkAdditionalFurniture(ServerLevel level, ServerPlayer player) {
        clear(level);
        player.setYRot(Direction.SOUTH.toYRot());
        Block desk = MdmModBlocks.BLACK_OFFICE_DESK.get();
        var deskShape = FurnitureFootprint.fullShape(desk.defaultBlockState(), level, POS);
        require(deskShape.bounds().minX == -1 && deskShape.bounds().maxX == 2, "Desk retained its phantom leg");
        require(contains(deskShape, -8, 8, 8), "Desk leg is missing");
        require(!contains(deskShape, -25, 8, 8), "Desk phantom leg remains");
        place(level, player, MdmModBlocks.WHITE_BATHTUB.get(), POS);
        var tubShape = FurnitureFootprint.fullShape(level.getBlockState(POS), level, POS);
        require(!contains(tubShape, 8, 8, 16) && contains(tubShape, 15, 8, 16), "Bathtub cavity is not hollow");
        clear(level);
        level.setBlock(POS.below(), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        place(level, player, MdmModBlocks.STANDING_WHITE_BOARD.get(), POS);
        require(level.getBlockState(POS.below()).is(Blocks.STONE), "Standing board reserves space below its feet");
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            clear(level);
            player.setYRot(facing.getOpposite().toYRot());
            Block board = MdmModBlocks.STANDING_WHITE_BOARD.get();
            place(level, player, board, POS);
            var shape = FurnitureFootprint.fullShape(level.getBlockState(POS), level, POS);
            var offsets = FurnitureFootprint.offsets(shape);
            require(shape.bounds().maxY == 30.0 / 16 && shape.bounds().minY == 0, "Standing board does not cover its full height");
            require(offsets.size() == 5 && offsets.stream().filter(offset -> offset.getY() == 1).count() == 3,
                    "Standing board must occupy three cells on each of two levels");
            require(offsets.stream().noneMatch(offset -> offset.getY() < 0), "Standing board reserves cells below its feet");
            level.destroyBlock(POS.above(), true);
            require(level.getBlockState(POS).isAir() && drops(level, board.asItem()) == 1, "Upper standing board helper did not destroy the board once");
            for (BlockPos offset : offsets) require(level.getBlockState(POS.offset(offset)).isAir(), "Standing board left an orphan helper");
        }
        for (Block board : new Block[]{MdmModBlocks.WHITE_BOARD.get(), MdmModBlocks.CORK_BOARD.get()}) {
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                clear(level);
                player.setYRot(facing.getOpposite().toYRot());
                BlockState state = board.defaultBlockState().setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing);
                var offsets = FurnitureFootprint.offsets(FurnitureFootprint.fullShape(state, level, POS));
                require(offsets.size() == 2 && offsets.stream().allMatch(offset -> offset.getY() == 0), "Board must occupy exactly three horizontal cells");
                for (BlockPos offset : new BlockPos[]{BlockPos.ZERO, offsets.get(0), offsets.get(1)}) {
                    level.setBlock(POS.offset(offset).above(), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
                    level.setBlock(POS.offset(offset).below(), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
                }
                place(level, player, board, POS);
                level.destroyBlock(POS, false);
                for (BlockPos offset : new BlockPos[]{BlockPos.ZERO, offsets.get(0), offsets.get(1)}) {
                    require(level.getBlockState(POS.offset(offset).above()).is(Blocks.STONE), "Board changed the cell above");
                    require(level.getBlockState(POS.offset(offset).below()).is(Blocks.STONE), "Board changed the cell below");
                }
            }
        }
        player.setYRot(Direction.SOUTH.toYRot());
        for (Block sink : BuiltInRegistries.BLOCK) {
            if (!Set.of("UniversalHangingShelfWithSinkDoubleBlock", "UniversalHangingShelfWithSinkDoubleOddBlock").contains(sink.getClass().getSimpleName())) continue;
            clear(level);
            place(level, player, sink, POS);
            BlockPos tap = POS.above();
            require(level.getBlockState(tap).isAir(), "Sink reserves the mirror cell");
            place(level, player, MdmModBlocks.BATHROOM_MIRROR.get(), tap);
            BlockState placed = level.getBlockState(POS);
            var offsets = FurnitureFootprint.offsets(FurnitureFootprint.fullShape(placed, level, POS));
            require(offsets.stream().allMatch(offset -> offset.getY() == 0), "Sink has vertical helpers");
            BlockPos helper = POS.offset(offsets.get(0));
            level.getBlockState(helper).useWithoutItem(level, player, new BlockHitResult(Vec3.atCenterOf(helper), Direction.NORTH, helper, false));
            require(level.getBlockState(POS).getValue(com.cookiecraftmods.mdm.block.RunningSinkBlock.RUNNING), "Shelf helper did not toggle the sink");
            level.destroyBlock(POS, false);
            require(level.getBlockState(tap).is(MdmModBlocks.BATHROOM_MIRROR.get()), "Sink removal destroyed the mirror");
            BlockState state = sink.defaultBlockState();
            level.setBlock(POS, state, Block.UPDATE_ALL);
            ((com.cookiecraftmods.mdm.block.FurnitureBlock) sink).tick(state, level, POS, level.getRandom());
            require(level.getBlockState(helper).getBlock() instanceof FurniturePartBlock, "Inactive existing sink did not gain helpers");
            require(level.getBlockState(tap).is(MdmModBlocks.BATHROOM_MIRROR.get()), "Sink repair replaced the mirror");
            var collision = ((MulticellFurniture) sink).getFurnitureCollisionShape(state, level, POS, CollisionContext.empty());
            double outletX = sink.getClass().getSimpleName().equals("UniversalHangingShelfWithSinkDoubleOddBlock") ? 0.5 : 0;
            AABB drop = new AABB(outletX - 0.01, 16.84 / 16, 11.5 / 16 - 0.01, outletX + 0.01, 16.84 / 16 + 0.01, 11.5 / 16 + 0.01);
            double landing = Shapes.collide(Direction.Axis.Y, drop, List.of(collision), -1);
            require(Math.abs(drop.minY + landing - 13.4 / 16) < 0.000001, "Sink water no longer lands on its drain");
        }
    }

    private static boolean contains(net.minecraft.world.phys.shapes.VoxelShape shape, double x, double y, double z) {
        return shape.toAabbs().stream().anyMatch(box -> box.contains(x / 16, y / 16, z / 16));
    }

    private static void checkExistingWorldRepair(ServerLevel level, ServerPlayer player) {
        clear(level);
        Block block = MdmModBlocks.OFFICE_SHELF.get();
        BlockState state = block.defaultBlockState();
        level.setBlock(POS, state, Block.UPDATE_ALL);
        var chunk = level.getChunkAt(POS);
        chunk.unregisterTickContainerFromLevel(level);
        chunk.registerTickContainerInLevel(level);
        require(level.getBlockTicks().hasScheduledTick(POS, block), "Existing furniture was not scheduled for repair");
        ((com.cookiecraftmods.mdm.block.FurnitureBlock) block).tick(state, level, POS, level.getRandom());
        require(level.getBlockState(POS.above()).getBlock() instanceof FurniturePartBlock, "Existing furniture not repaired");
        level.destroyBlock(POS, false);
        level.setBlock(POS.above(), Blocks.STONE.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(POS, state, Block.UPDATE_ALL);
        ((com.cookiecraftmods.mdm.block.FurnitureBlock) block).tick(state, level, POS, level.getRandom());
        require(level.getBlockState(POS.above()).is(Blocks.STONE), "Repair overwrote an unrelated block");
        clear(level);
        level.setBlock(POS, MdmModBlocks.WHITE_BOARD.get().defaultBlockState(), Block.UPDATE_ALL);
        BlockPos obsolete = POS.above();
        BlockState oldPart = FurniturePartBlock.forOffset(new BlockPos(0, 1, 0), Blocks.AIR.defaultBlockState());
        level.setBlock(obsolete, oldPart, Block.UPDATE_ALL);
        chunk.unregisterTickContainerFromLevel(level);
        chunk.registerTickContainerInLevel(level);
        require(level.getBlockTicks().hasScheduledTick(obsolete, oldPart.getBlock()), "Old vertical helper was not scheduled for cleanup");
        ((FurniturePartBlock) oldPart.getBlock()).tick(oldPart, level, obsolete, level.getRandom());
        require(level.getBlockState(obsolete).isAir() && level.getBlockState(POS).is(MdmModBlocks.WHITE_BOARD.get()), "Old helper cleanup removed the board");
    }

    private static BlockPlaceContext context(ServerLevel level, ServerPlayer player, Block block, BlockPos pos) {
        return new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, new ItemStack(block),
                new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
    }

    private static void place(ServerLevel level, ServerPlayer player, Block block, BlockPos pos) {
        var context = context(level, player, block, pos);
        require(((BlockItem) block.asItem()).place(context).consumesAction(), "Placement failed: " + block + " at " + pos);
    }

    private static int drops(ServerLevel level, net.minecraft.world.item.Item item) {
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(POS).inflate(5)).stream()
                .filter(entity -> entity.getItem().is(item)).mapToInt(entity -> entity.getItem().getCount()).sum();
    }

    private static ItemStack pick(ServerLevel level, BlockPos pos, BlockState state) throws Exception {
        for (var method : FurniturePartBlock.class.getDeclaredMethods()) {
            if (method.getName().equals("getCloneItemStack")) {
                return (ItemStack) (method.getParameterCount() == 4
                        ? method.invoke(state.getBlock(), level, pos, state, false)
                        : method.invoke(state.getBlock(), level, pos, state));
            }
        }
        throw new AssertionError("Missing pick-block forwarding");
    }

    private static void clear(ServerLevel level) {
        for (BlockPos pos : BlockPos.betweenClosed(POS.offset(-3, -3, -3), POS.offset(3, 4, 3))) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
        level.getEntitiesOfClass(ItemEntity.class, new AABB(POS).inflate(5)).forEach(ItemEntity::discard);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
