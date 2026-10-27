package com.cookiecraftmods.mdm.block;

import com.cookiecraftmods.mdm.block.entity.WallClockBlockEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.ImmutableMap;

public class BlackWallClockBlock extends HorizontalFurnitureBlock implements ITileEntityProvider {
	@Override
	public TileEntity newBlockEntity(net.minecraft.world.IBlockReader level) {
		return new WallClockBlockEntity(com.cookiecraftmods.mdm.init.MdmModBlockEntities.BLACK_WALL_CLOCK.get());
	}

	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public BlackWallClockBlock() {
		super(AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return com.cookiecraftmods.mdm.block.LegacyShapes.forStates(this, state -> {
			return switch (state.getValue(FACING)) {
				default -> box(3, 3, 0, 13, 13, 1);
				case NORTH -> box(3, 3, 15, 13, 13, 16);
				case EAST -> box(0, 3, 3, 1, 13, 13);
				case WEST -> box(15, 3, 3, 16, 13, 13);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return shapes.get(state);
	}

}
