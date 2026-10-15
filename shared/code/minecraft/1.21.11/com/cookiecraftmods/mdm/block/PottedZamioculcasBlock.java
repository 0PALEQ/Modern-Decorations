package com.cookiecraftmods.mdm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.SoundType;

public class PottedZamioculcasBlock extends TransparentHorizontalFurnitureBlock {

	public PottedZamioculcasBlock() {
		super(com.cookiecraftmods.mdm.registry.RegistryProperties.block().sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().noCollision().isRedstoneConductor((bs, br, bp) -> false).instrument(NoteBlockInstrument.BASEDRUM));
	}

    private static final VoxelShape SHAPE = box(2, 0, 2, 14, 22, 14);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
