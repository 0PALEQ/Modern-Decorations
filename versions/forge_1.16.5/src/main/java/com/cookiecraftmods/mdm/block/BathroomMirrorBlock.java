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

public class BathroomMirrorBlock extends HorizontalFurnitureBlock {
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BathroomMirrorBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.GLASS).strength(1f, 10f).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				);
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
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
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
