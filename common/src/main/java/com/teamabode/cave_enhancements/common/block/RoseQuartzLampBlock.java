package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RoseQuartzLampBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(4.0D, 4.0D, 2.0D, 12.0D, 12.0D, 12.0D), Block.box(7.0D, 7.0D, 12.0D, 9.0D, 9.0D, 16.0D));
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 14.0D), Block.box(7D, 7D, 0D, 9D, 9D, 0D));
    public static final VoxelShape EAST_SHAPE = Shapes.or(Block.box(4.0D, 4.0D, 4.0D, 14.0D, 12.0D, 12.0D), Block.box(0D, 7D, 7D, 4D, 9D, 9D));
    public static final VoxelShape WEST_SHAPE = Shapes.or(Block.box(2.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D), Block.box(12D, 7D, 7D, 16D, 9D, 9D));
    public static final VoxelShape UP_SHAPE = Shapes.or(Block.box(4.0D, 4.0D, 4.0D, 12.0D, 14.0D, 12.0D), Block.box(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D));
    public static final VoxelShape DOWN_SHAPE = Shapes.or(Block.box(4.0D, 2.0D, 4.0D, 12.0D, 12.0D, 12.0D), Block.box(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    public RoseQuartzLampBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
        };
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return Block.canSupportCenter(level, pos.relative(direction.getOpposite()), direction);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        spawnParticles(level, blockPos);
    }

    private static void spawnParticles(Level level, BlockPos pos) {
        RandomSource randomSource = level.random;

        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            if (!level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.25 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(ModParticles.STAGNANT_SHIMMER.get(), (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor worldAccess = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, worldAccess.getFluidState(blockPos).getType() == Fluids.WATER).setValue(FACING, ctx.getClickedFace());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
