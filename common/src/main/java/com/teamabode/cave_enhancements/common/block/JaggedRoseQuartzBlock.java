package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class JaggedRoseQuartzBlock extends DropExperienceBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
    private static final VoxelShape HANGING_SHAPE = Block.box(1.0D, 4.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public JaggedRoseQuartzBlock(Properties settings) {
        super(settings, UniformInt.of(1, 2));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(WATERLOGGED).add(HANGING);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = getDirection(state).getOpposite();
        return Block.canSupportCenter(world, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction getDirection(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Direction[] lookingDirections = context.getNearestLookingDirections();

        for (Direction direction : lookingDirections) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.defaultBlockState().setValue(HANGING, direction == Direction.UP);
                if (blockState.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return getDirection(state).getOpposite() == direction && !this.canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (random.nextIntBetweenInclusive(0, 3) == 0){
            int l = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double)l + random.nextDouble();
            double e = (double)j + 0.5D;
            double f = (double)k + random.nextDouble();

            double ySpeed = state.getValue(HANGING) ? -0.01D : 0.01D;
            world.addParticle(ModParticles.SHIMMER.get(), d, e, f, Mth.nextFloat(random, -1, 1) / 50, ySpeed, Mth.nextFloat(random, -1, 1) / 50);
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }
}
