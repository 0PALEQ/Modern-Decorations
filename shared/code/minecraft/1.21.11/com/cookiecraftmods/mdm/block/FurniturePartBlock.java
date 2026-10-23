package com.cookiecraftmods.mdm.block;

import com.cookiecraftmods.mdm.init.MdmModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;

public class FurniturePartBlock extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty X = IntegerProperty.create("offset_x", 0, 8);
    public static final IntegerProperty Y = IntegerProperty.create("offset_y", 0, 8);
    public static final IntegerProperty Z = IntegerProperty.create("offset_z", 0, 8);
    public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FurniturePartBlock() {
        super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().strength(1f, 10f).noOcclusion().dynamicShape()
                .pushReaction(FurnitureFootprint.IMMOVABLE).isRedstoneConductor((state, level, pos) -> false));
        registerDefaultState(stateDefinition.any().setValue(X, 1).setValue(Y, 1).setValue(Z, 1).setValue(WATERLOGGED, false).setValue(EXTENDED, false));
    }

    public static BlockState forOffset(BlockPos offset, BlockState replaced) {
        return MdmModBlocks.FURNITURE_PART.get().defaultBlockState()
                .setValue(EXTENDED, true).setValue(X, offset.getX() + 4).setValue(Y, offset.getY() + 4).setValue(Z, offset.getZ() + 4)
                .setValue(WATERLOGGED, replaced.getFluidState().getType() == Fluids.WATER);
    }

    public static BlockPos parentPos(BlockState state, BlockPos pos) {
        int center = state.getValue(EXTENDED) ? 4 : 1;
        return pos.offset(center - state.getValue(X), center - state.getValue(Y), center - state.getValue(Z));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(X, Y, Z, WATERLOGGED, EXTENDED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.partShape(level, pos, state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FurnitureFootprint.partCollisionShape(level, pos, state);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.INVISIBLE; }



    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        int center = state.getValue(EXTENDED) ? 4 : 1;
        BlockPos offset = new BlockPos(state.getValue(X) - center, state.getValue(Y) - center, state.getValue(Z) - center).rotate(rotation);
        return state.setValue(X, offset.getX() + center).setValue(Z, offset.getZ() + center);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        int center = state.getValue(EXTENDED) ? 4 : 1;
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(Z, 2 * center - state.getValue(Z));
            case FRONT_BACK -> state.setValue(X, 2 * center - state.getValue(X));
            default -> state;
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!(context.getItemInHand().getItem() instanceof BlockItem item)) return false;
        BlockPos parent = parentPos(state, context.getClickedPos());
        BlockState candidate = item.getBlock().defaultBlockState();
        if (candidate.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            candidate = candidate.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
        return FurnitureFootprint.compatible(candidate, context.getClickedPos(), context.getLevel().getBlockState(parent), parent);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative() && FurnitureFootprint.validPart(level, pos, state)) {
            FurnitureFootprint.destroyParents(level, pos, state, false);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos parent = parentPos(state, pos);
        if (level.hasChunkAt(parent) && !FurnitureFootprint.validPart(level, pos, state)) {
            level.setBlock(pos, state.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
        }
    }


    @Override
    public ItemStack getCloneItemStack(net.minecraft.world.level.LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (!FurnitureFootprint.validPart(level, pos, state)) return ItemStack.EMPTY;
        BlockPos parent = parentPos(state, pos);
        BlockState owner = level.getBlockState(parent);
        return owner.getCloneItemStack(level, parent, includeData);
    }

    private static InteractionResult useParent(Level level, BlockPos parent, Player player, BlockHitResult hit) {
        BlockState owner = level.getBlockState(parent);
        InteractionResult result = SeatBlock.use(owner, level, parent, player);
        return result.consumesAction() ? result : owner.useWithoutItem(level, player, parentHit(hit, parent));
    }

    private static BlockHitResult parentHit(BlockHitResult hit, BlockPos parent) {
        return new BlockHitResult(hit.getLocation(), hit.getDirection(), parent, hit.isInside());
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        BlockPos parent = parentPos(state, pos);
        return FurnitureFootprint.validPart(level, pos, state)
                ? useParent(level, parent, player, hit) : InteractionResult.PASS;
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos parent = parentPos(state, pos);
        return FurnitureFootprint.validPart(level, pos, state)
                ? level.getBlockState(parent).useItemOn(stack, level, player, hand, parentHit(hit, parent))
                : InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        FurnitureFootprint.partRemoved(level, pos, state, level.getBlockState(pos));
        super.affectNeighborsAfterRemoval(state, level, pos, moving);
    }

    @Override
    protected BlockState updateShape(BlockState state, net.minecraft.world.level.LevelReader level,
            net.minecraft.world.level.ScheduledTickAccess ticks, BlockPos pos, net.minecraft.core.Direction direction,
            BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        ticks.scheduleTick(pos, this, 1);
        if (state.getValue(WATERLOGGED)) ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return state;
    }

}
