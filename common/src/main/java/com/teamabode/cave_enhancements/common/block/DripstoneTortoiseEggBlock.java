package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DripstoneTortoiseEggBlock extends Block implements SimpleWaterloggedBlock {

    public static final IntegerProperty HATCH = IntegerProperty.create("hatch", 0, 2);
    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public DripstoneTortoiseEggBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HATCH, 0).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, WATERLOGGED);
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            int i = blockState.getValue(HATCH);

            if (i < 2) {
                serverLevel.playSound(null, blockPos, ModSounds.ENTITY_DRIPSTONE_TORTOISE_CRACK_EGG.get(), SoundSource.BLOCKS, 0.7F, 0.9F + randomSource.nextFloat() * 0.2F);
                serverLevel.setBlock(blockPos, blockState.setValue(HATCH, i + 1), 2);
            } else {
                serverLevel.playSound(null, blockPos, ModSounds.ENTITY_DRIPSTONE_TORTOISE_HATCH_EGG.get(), SoundSource.BLOCKS, 0.7F, 0.9F + randomSource.nextFloat() * 0.2F);
                serverLevel.removeBlock(blockPos, false);
                serverLevel.levelEvent(2001, blockPos, Block.getId(blockState));

                DripstoneTortoise dripstoneTortoise = ModEntities.DRIPSTONE_TORTOISE.get().create(serverLevel);
                dripstoneTortoise.setAge(-24000);
                dripstoneTortoise.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

                serverLevel.addFreshEntity(dripstoneTortoise);
            }
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterCheck = fluidState.getType() == Fluids.WATER;
        BlockState blockState = super.defaultBlockState();
        return blockState.setValue(WATERLOGGED, waterCheck);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(WATERLOGGED) && !level.isClientSide) {
            level.levelEvent(2005, pos, 0);
        }
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (!level.isClientSide && !(entity instanceof DripstoneTortoise)) {
            if (level.random.nextInt(10) == 0) {
                level.destroyBlock(pos, false);
                level.playSound(null, pos, ModSounds.ENTITY_DRIPSTONE_TORTOISE_BREAK_EGG.get(), SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
            } else {
                level.playSound(null, pos, ModSounds.ENTITY_DRIPSTONE_TORTOISE_CRACK_EGG.get(), SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
            }
        }
        super.fallOn(level, state, pos, entity, f);
    }
}
