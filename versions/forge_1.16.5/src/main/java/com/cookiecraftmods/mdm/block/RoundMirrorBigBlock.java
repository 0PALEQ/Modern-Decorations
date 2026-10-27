package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class RoundMirrorBigBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public RoundMirrorBigBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)
				);
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
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
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
