package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class RingLampBlock extends FurnitureBlock {
	private static final VoxelShape SHAPE = Shapes.or(box(4, 15, 4, 12, 16, 12), box(4.25, 14, 4.25, 11.75, 15, 5.25), box(4.25, 14, 10.75, 11.75, 15, 11.75), box(4.25, 14, 5.25, 5.25, 15, 10.75), box(10.75, 14, 5.25, 11.75, 15, 10.75),
			box(5.5, 14.5, 5.5, 10.5, 15, 10.5));

	public RingLampBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> 15).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
