package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.common.block.entity.ReceiverBlockEntity;
import com.teamabode.cave_enhancements.common.block.weathering.WeatherState;
import com.teamabode.cave_enhancements.common.block.weathering.WeatheringBlock;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;

public class ReceiverBlock extends DiodeBlock implements WeatheringBlock, EntityBlock {
    public static final BooleanProperty CAN_PASS = BooleanProperty.create("can_pass");
    private final WeatherState weatherState;

    public ReceiverBlock(WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
        this.registerDefaultState(this.getStateDefinition().any().setValue(CAN_PASS, false).setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CAN_PASS, POWERED, FACING);
    }

    public int getRequiredPowerDurationTicks() {
        return switch (weatherState) {
            case UNAFFECTED, WAXED_UNAFFECTED -> 2;
            case EXPOSED, WAXED_EXPOSED -> 5;
            case WEATHERED, WAXED_WEATHERED -> 10;
            case OXIDIZED, WAXED_OXIDIZED -> 20;
        };
    }

    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        int i = super.getInputSignal(level, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.hasAnalogOutputSignal()) {
            i = blockState.getAnalogOutputSignal(level, blockPos);
        } else if (i < 15 && blockState.isRedstoneConductor(level, blockPos)) {
            if(blockState.hasAnalogOutputSignal()) i = blockState.getAnalogOutputSignal(level, blockPos);
        }
        return i;
    }

    protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof ReceiverBlockEntity receiverBlockEntity ? receiverBlockEntity.output : 0;
    }

    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        if (!state.getValue(CAN_PASS)) {
            return 0;
        } else {
            return state.getValue(FACING) == direction ? this.getOutputSignal(world, pos, state) : 0;
        }
    }

    public boolean isSignalSource(BlockState state) {
        return state.getValue(CAN_PASS);
    }

    protected int getDelay(BlockState blockState) {
        return 0;
    }

    private int calculateOutputSignal(Level level, BlockPos pos, BlockState state) {
        return getInputSignal(level, pos, state);
    }

    private void refreshOutputState(Level level, BlockPos pos, BlockState state) {
        int i = this.calculateOutputSignal(level, pos, state);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        int j = 0;

        if (blockEntity instanceof ReceiverBlockEntity receiverBlockEntity) {
            j = receiverBlockEntity.output;
            receiverBlockEntity.output = i;
        }

        if (j != i) {
            boolean bl = this.shouldTurnOn(level, pos, state);
            boolean bl2 = state.getValue(POWERED);

            if (bl2 && !bl) {
                level.setBlock(pos, state.setValue(POWERED, false), 2);
            } else if (!bl2 && bl) {
                level.setBlock(pos, state.setValue(POWERED, true), 2);
            }

            this.updateNeighborsInFront(level, pos, state);
        }
    }

    protected void checkTickOnNeighbor(Level level, BlockPos pos, BlockState state) {
        if (!level.getBlockTicks().willTickThisTick(pos, this)) {
            int i = this.calculateOutputSignal(level, pos, state);

            BlockEntity blockEntity = level.getBlockEntity(pos);

            int j = blockEntity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)blockEntity).getOutputSignal() : 0;

            if (i != j || state.getValue(POWERED) != this.shouldTurnOn(level, pos, state)) {
                TickPriority tickPriority = this.shouldPrioritize(level, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
                level.scheduleTick(pos, this, 2, tickPriority);
            }
        }
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.refreshOutputState(serverLevel, blockPos, blockState);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        assert blockState != null;
        return blockState.setValue(CAN_PASS, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.onRandomTick(blockState, serverLevel, blockPos, randomSource);
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return this.interactLogic(blockState, level, blockPos, player, interactionHand, blockHitResult).orElse(super.use(blockState, level, blockPos, player, interactionHand, blockHitResult));
    }

    public WeatherState getAge() {
        return this.weatherState;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ReceiverBlockEntity(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (level1, blockPos, blockState1, blockEntity) -> {
            if (blockEntity instanceof ReceiverBlockEntity receiverBlockEntity) receiverBlockEntity.tick(level1, blockPos, blockState1, blockEntity);
        };
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            Direction direction = state.getValue(FACING);
            double d = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double)pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float g = -5.0F;
            if (random.nextBoolean()) {
                g = (float)(4 * 2 - 1);
            }

            g /= 16.0F;
            double h = g * (float)direction.getStepX();
            double i = g * (float)direction.getStepZ();
            world.addParticle(DustParticleOptions.REDSTONE, d + h, e, f + i, 0.0D, 0.0D, 0.0D);
        }
    }

    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public boolean canConnectRedstone(BlockState blockState, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        if (direction == null) return false;
        return direction == blockState.getValue(FACING) || direction.getOpposite() == blockState.getValue(FACING);
    }
}
