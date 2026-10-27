package com.cookiecraftmods.mdm.block;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

public class ShoeRackBlock extends TransparentHorizontalFurnitureBlock {

	private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(box(0, 0, 7, 16, 9, 16), box(1.25, 9.025, 9.2, 14.25, 13.225, 12.95));
	private static final VoxelShape EAST_SHAPE = HorizontalFurnitureBlock.rotateShape(NORTH_SHAPE);
	private static final VoxelShape SOUTH_SHAPE = HorizontalFurnitureBlock.rotateShape(EAST_SHAPE);
	private static final VoxelShape WEST_SHAPE = HorizontalFurnitureBlock.rotateShape(SOUTH_SHAPE);

	public ShoeRackBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return switch (state.getValue(FACING)) {
			case EAST -> EAST_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> NORTH_SHAPE;
		};
	}
}
