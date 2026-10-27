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

public class WalkinShowerBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WalkinShowerBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> VoxelShapes.or(box(0, 0, -16, 16, 0.25, 16), box(0, 0, 15, 16, 32, 16), box(15, 0, -16, 16, 32, 0));
				case NORTH -> VoxelShapes.or(box(0, 0, 0, 16, 0.25, 32), box(0, 0, 0, 16, 32, 1), box(0, 0, 16, 1, 32, 32));
				case EAST -> VoxelShapes.or(box(-16, 0, 0, 16, 0.25, 16), box(15, 0, 0, 16, 32, 16), box(-16, 0, 0, 0, 32, 1));
				case WEST -> VoxelShapes.or(box(0, 0, 0, 32, 0.25, 16), box(0, 0, 0, 1, 32, 16), box(16, 0, 15, 32, 32, 16));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
