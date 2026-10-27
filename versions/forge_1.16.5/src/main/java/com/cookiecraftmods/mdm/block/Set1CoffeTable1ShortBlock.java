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

public class Set1CoffeTable1ShortBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1CoffeTable1ShortBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.or(box(0, 5, 1, 15, 8, 15), box(8.5, 0, 0.25, 10.5, 1, 15.75), box(8.5, 4.5, 1, 10.5, 5, 15), box(8.5, 1, 0.25, 10.5, 7, 1), box(8.5, 1, 15, 10.5, 7, 15.75));
				case NORTH -> VoxelShapes.or(box(1, 5, 1, 16, 8, 15), box(5.5, 0, 0.25, 7.5, 1, 15.75), box(5.5, 4.5, 1, 7.5, 5, 15), box(5.5, 1, 15, 7.5, 7, 15.75), box(5.5, 1, 0.25, 7.5, 7, 1));
				case EAST -> VoxelShapes.or(box(1, 5, 1, 15, 8, 16), box(0.25, 0, 5.5, 15.75, 1, 7.5), box(1, 4.5, 5.5, 15, 5, 7.5), box(0.25, 1, 5.5, 1, 7, 7.5), box(15, 1, 5.5, 15.75, 7, 7.5));
				case WEST -> VoxelShapes.or(box(1, 5, 0, 15, 8, 15), box(0.25, 0, 8.5, 15.75, 1, 10.5), box(1, 4.5, 8.5, 15, 5, 10.5), box(15, 1, 8.5, 15.75, 7, 10.5), box(0.25, 1, 8.5, 1, 7, 10.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
