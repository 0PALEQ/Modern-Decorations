package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class RingLampBlock extends FurnitureBlock {
	private static final VoxelShape SHAPE = box(4, 14, 4, 12, 16, 12);

	public RingLampBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> 15).noOcclusion().postProcess((bs, br, bp) -> bp).emissiveRendering(state -> true).isRedstoneConductor((bs, br, bp) -> false)
				.instrument(NoteBlockInstrument.BASEDRUM));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

}
