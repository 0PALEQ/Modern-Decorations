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

public class ShowerHeadBlock extends RunningSinkBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ShowerHeadBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)
				, WaterOutlets.SHOWER);
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> box(4.5, 5.15, 4.5, 11.5, 9.25, 16);
				case EAST -> box(0, 5.15, 4.5, 11.5, 9.25, 11.5);
				case WEST -> box(4.5, 5.15, 4.5, 16, 9.25, 11.5);
				default -> box(4.5, 5.15, 0, 11.5, 9.25, 11.5);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
