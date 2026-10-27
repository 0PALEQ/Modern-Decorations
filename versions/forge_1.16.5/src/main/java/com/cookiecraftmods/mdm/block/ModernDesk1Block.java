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

public class ModernDesk1Block extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ModernDesk1Block() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.or(box(-16, 15, 0, 32, 16, 16), box(24.25, 0, 0, 26.25, 1, 16), box(-10.25, 0, 0, -8.25, 1, 16));
				case NORTH -> VoxelShapes.or(box(-16, 15, 0, 32, 16, 16), box(-10.25, 0, 0, -8.25, 1, 16), box(24.25, 0, 0, 26.25, 1, 16));
				case EAST -> VoxelShapes.or(box(0, 15, -16, 16, 16, 32), box(0, 0, -10.25, 16, 1, -8.25), box(0, 0, 24.25, 16, 1, 26.25));
				case WEST -> VoxelShapes.or(box(0, 15, -16, 16, 16, 32), box(0, 0, 24.25, 16, 1, 26.25), box(0, 0, -10.25, 16, 1, -8.25));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
