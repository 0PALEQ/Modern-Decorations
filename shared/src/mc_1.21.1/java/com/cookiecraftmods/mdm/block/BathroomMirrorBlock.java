package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BathroomMirrorBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BathroomMirrorBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			// Outer bounds of custom/mirror.json, including its rotated light fittings.
			return switch (state.getValue(FACING)) {
				case NORTH -> box(0.5, 4, 11.458512667781108, 15.5, 19.31312592975275, 16);
				case EAST -> box(0, 4, 0.5, 4.541487332218892, 19.31312592975275, 15.5);
				case WEST -> box(11.458512667781108, 4, 0.5, 16, 19.31312592975275, 15.5);
				default -> box(0.5, 4, 0, 15.5, 19.31312592975275, 4.541487332218892);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

}
