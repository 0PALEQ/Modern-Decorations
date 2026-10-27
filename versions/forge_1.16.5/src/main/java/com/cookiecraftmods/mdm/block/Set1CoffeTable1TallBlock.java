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

public class Set1CoffeTable1TallBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1CoffeTable1TallBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.or(box(0, 13, 1, 16, 16, 15), box(6, 0, 0.25, 8, 1, 15.75), box(6, 9.5, 1, 8, 10, 15), box(6, 1, 15, 8, 15, 15.75), box(6, 1, 0.25, 8, 15, 1));
				case NORTH -> VoxelShapes.or(box(0, 13, 1, 16, 16, 15), box(8, 0, 0.25, 10, 1, 15.75), box(8, 9.5, 1, 10, 10, 15), box(8, 1, 0.25, 10, 15, 1), box(8, 1, 15, 10, 15, 15.75));
				case EAST -> VoxelShapes.or(box(1, 13, 0, 15, 16, 16), box(0.25, 0, 8, 15.75, 1, 10), box(1, 9.5, 8, 15, 10, 10), box(15, 1, 8, 15.75, 15, 10), box(0.25, 1, 8, 1, 15, 10));
				case WEST -> VoxelShapes.or(box(1, 13, 0, 15, 16, 16), box(0.25, 0, 6, 15.75, 1, 8), box(1, 9.5, 6, 15, 10, 8), box(0.25, 1, 6, 1, 15, 8), box(15, 1, 6, 15.75, 15, 8));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
