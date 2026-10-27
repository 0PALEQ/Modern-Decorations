package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.StateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.*;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.world.IBlockReader;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class GreenCurtainBlock extends FurnitureBlock {
	public static final DirectionProperty FACING = net.minecraft.block.HorizontalBlock.FACING;
	public static final EnumProperty<AttachFace> FACE = net.minecraft.block.HorizontalFaceBlock.FACE;
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public GreenCurtainBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> switch (state.getValue(FACE)) {
					case FLOOR -> box(-4, -16, 0, 20, 32, 2);
					case WALL -> box(-4, -16, 0, 20, 32, 2);
					case CEILING -> box(-4, -16, 0, 20, 32, 2);
				};
				case NORTH -> switch (state.getValue(FACE)) {
					case FLOOR -> box(-4, -16, 14, 20, 32, 16);
					case WALL -> box(-4, -16, 14, 20, 32, 16);
					case CEILING -> box(-4, -16, 14, 20, 32, 16);
				};
				case EAST -> switch (state.getValue(FACE)) {
					case FLOOR -> box(0, -16, -4, 2, 32, 20);
					case WALL -> box(0, -16, -4, 2, 32, 20);
					case CEILING -> box(0, -16, -4, 2, 32, 20);
				};
				case WEST -> switch (state.getValue(FACE)) {
					case FLOOR -> box(14, -16, -4, 16, 32, 20);
					case WALL -> box(14, -16, -4, 16, 32, 20);
					case CEILING -> box(14, -16, -4, 16, 32, 20);
				};
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, FACE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACE, faceForDirection(context.getNearestLookingDirection())).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	private AttachFace faceForDirection(Direction direction) {
		if (direction.getAxis() == Direction.Axis.Y)
			return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
		else
			return AttachFace.WALL;
	}
}
