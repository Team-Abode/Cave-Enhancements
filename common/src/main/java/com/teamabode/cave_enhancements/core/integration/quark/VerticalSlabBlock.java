package com.teamabode.cave_enhancements.core.integration.quark;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public VerticalSlabBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.getValue(TYPE) == VerticalSlabType.DOUBLE ? blockState : blockState.setValue(TYPE, VerticalSlabType.fromDirection(rotation.rotate(blockState.getValue(TYPE).direction)));
    }

    public BlockState mirror(BlockState blockState, Mirror mirror) {
        VerticalSlabType type = blockState.getValue(TYPE);
        if (type == VerticalSlabType.DOUBLE || mirror == Mirror.NONE) {
            return blockState;
        }
        if ((mirror == Mirror.LEFT_RIGHT && type.direction.getAxis() == Direction.Axis.Z) || (mirror == Mirror.FRONT_BACK && type.direction.getAxis() == Direction.Axis.X)) {
            return blockState.setValue(TYPE, VerticalSlabType.fromDirection(blockState.getValue(TYPE).direction.getOpposite()));
        }
        return blockState;
    }

    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return blockState.getValue(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Nullable
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(TYPE).voxelShape;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        if(blockstate.getBlock() == this)
            return blockstate.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);

        FluidState fluid = context.getLevel().getFluidState(blockpos);
        BlockState finalState = defaultBlockState().setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
        Direction direction = getDirectionForPlacement(context);
        VerticalSlabType type = VerticalSlabType.fromDirection(direction);

        return finalState.setValue(TYPE, type);
    }

    private Direction getDirectionForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if(direction.getAxis() != Direction.Axis.Y)
            return direction;

        BlockPos pos = context.getClickedPos();
        Vec3 vec = context.getClickLocation().subtract(new Vec3(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
        double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
        return Direction.fromYRot(angle).getOpposite();
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        VerticalSlabType slabtype = state.getValue(TYPE);
        return slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem() && (useContext.replacingClickedOnBlock() && (useContext.getClickedFace() == slabtype.direction && getDirectionForPlacement(useContext) == slabtype.direction) || (!useContext.replacingClickedOnBlock() && useContext.getClickedFace() != slabtype.direction));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidStateIn);
    }

    public boolean canPlaceLiquid(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(worldIn, pos, state, fluidIn);
    }

    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        return pathComputationType == PathComputationType.WATER && blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
    }
}
