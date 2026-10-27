package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import com.cookiecraftmods.mdm.block.entity.Set2HangingShelfBlockEntity;

public class Set2HangingShelfBlock extends Block implements ITileEntityProvider {

	private static final VoxelShape NORTH_SHAPE = box(0, 0, 10.625, 16, 16, 16);
	private static final VoxelShape EAST_SHAPE = HorizontalFurnitureBlock.rotateShape(NORTH_SHAPE);
	private static final VoxelShape SOUTH_SHAPE = HorizontalFurnitureBlock.rotateShape(EAST_SHAPE);
	private static final VoxelShape WEST_SHAPE = HorizontalFurnitureBlock.rotateShape(SOUTH_SHAPE);
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public Set2HangingShelfBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

    @Override
    public net.minecraft.block.material.PushReaction getPistonPushReaction(BlockState state) {
        return net.minecraft.block.material.PushReaction.BLOCK;
    }

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public ActionResultType use(BlockState blockstate, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		if (entity instanceof ServerPlayerEntity player) {
			if (world.getBlockEntity(pos) instanceof INamedContainerProvider provider) {
				player.openMenu(provider);
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof INamedContainerProvider menuProvider ? menuProvider : null;
	}

	@Override
	public TileEntity newBlockEntity(net.minecraft.world.IBlockReader level) {
		return new Set2HangingShelfBlockEntity();
	}

	@Override
	public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		TileEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof Set2HangingShelfBlockEntity be) {
				InventoryHelper.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof Set2HangingShelfBlockEntity be)
			return Container.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> EAST_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> NORTH_SHAPE;
		};
	}
}
