package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class StreetLanternBottomBlock extends FurnitureBlock {
	private static final VoxelShape SHAPE = Shapes.or(box(5, 0, 5, 11, 1, 11), box(6, 1, 6, 10, 2, 10), box(7, 2, 7, 9, 16, 9));

	public StreetLanternBottomBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
