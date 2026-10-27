package com.cookiecraftmods.mdm.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class RingLampBlock extends FurnitureBlock {
	public static final net.minecraft.world.level.block.state.properties.BooleanProperty LIT = LightSwitch.LIT;

	private static final VoxelShape SHAPE = box(4, 14, 4, 12, 16, 12);

	public RingLampBlock() {
		super(BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE).sound(SoundType.GLASS).strength(1f, 10f).lightLevel(blockstate -> blockstate.getValue(LIT) ? 15 : 0).noOcclusion().hasPostProcess((bs, br, bp) -> bs.getValue(LIT)).emissiveRendering((bs, br, bp) -> bs.getValue(LIT)).isRedstoneConductor((bs, br, bp) -> false)
				);
		registerDefaultState(defaultBlockState().setValue(LIT, true));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

}
