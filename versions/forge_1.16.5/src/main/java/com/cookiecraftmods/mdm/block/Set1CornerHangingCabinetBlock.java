package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class Set1CornerHangingCabinetBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public Set1CornerHangingCabinetBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.join(box(0, 0, 0, 16, 16, 16), box(9, 0, 9, 16, 16, 16), IBooleanFunction.ONLY_FIRST);
				case NORTH -> VoxelShapes.join(box(0, 0, 0, 16, 16, 16), box(0, 0, 0, 7, 16, 7), IBooleanFunction.ONLY_FIRST);
				case EAST -> VoxelShapes.join(box(0, 0, 0, 16, 16, 16), box(9, 0, 0, 16, 16, 7), IBooleanFunction.ONLY_FIRST);
				case WEST -> VoxelShapes.join(box(0, 0, 0, 16, 16, 16), box(0, 0, 9, 7, 16, 16), IBooleanFunction.ONLY_FIRST);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
