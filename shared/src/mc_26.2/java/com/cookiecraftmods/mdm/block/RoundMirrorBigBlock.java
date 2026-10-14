package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class RoundMirrorBigBlock extends HorizontalFurnitureBlock {
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public RoundMirrorBigBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().emissiveRendering(state -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			// Bounds of the supplied round mirror; rotate with its facing.
			return switch (state.getValue(FACING)) {
				case NORTH -> box(-10, -7, 14.75, 26, 29, 16.25);
				case EAST -> box(-0.25, -7, -10, 1.25, 29, 26);
				case WEST -> box(14.75, -7, -10, 16.25, 29, 26);
				default -> box(-10, -7, -0.25, 26, 29, 1.25);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

}
