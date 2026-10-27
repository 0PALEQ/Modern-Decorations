package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class RainShowerBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public RainShowerBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.METAL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.or(box(7.25, 12.25, 0, 8.75, 13.75, 8), box(7.25, 10.75, 7, 8.75, 12.25, 8), box(6.25, 9.75, 5.75, 9.75, 10.75, 9.25), box(6.75, 9.5, 6.25, 9.25, 9.75, 8.75), box(10, 3, 0, 12, 5, 2), box(4, 3, 0, 6, 5, 2));
				case NORTH -> VoxelShapes.or(box(7.25, 12.25, 8, 8.75, 13.75, 16), box(7.25, 10.75, 8, 8.75, 12.25, 9), box(6.25, 9.75, 6.75, 9.75, 10.75, 10.25), box(6.75, 9.5, 7.25, 9.25, 9.75, 9.75), box(4, 3, 14, 6, 5, 16), box(10, 3, 14, 12, 5, 16));
				case EAST -> VoxelShapes.or(box(0, 12.25, 7.25, 8, 13.75, 8.75), box(7, 10.75, 7.25, 8, 12.25, 8.75), box(5.75, 9.75, 6.25, 9.25, 10.75, 9.75), box(6.25, 9.5, 6.75, 8.75, 9.75, 9.25), box(0, 3, 4, 2, 5, 6), box(0, 3, 10, 2, 5, 12));
				case WEST -> VoxelShapes.or(box(8, 12.25, 7.25, 16, 13.75, 8.75), box(8, 10.75, 7.25, 9, 12.25, 8.75), box(6.75, 9.75, 6.25, 10.25, 10.75, 9.75), box(7.25, 9.5, 6.75, 9.75, 9.75, 9.25), box(14, 3, 10, 16, 5, 12), box(14, 3, 4, 16, 5, 6));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
