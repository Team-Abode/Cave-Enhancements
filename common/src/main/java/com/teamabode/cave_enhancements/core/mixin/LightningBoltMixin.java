package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.common.block.weathering.WeatheringBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Optional;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {
    @Shadow protected abstract BlockPos getStrikePosition();

    private final LightningBolt lightningBolt = LightningBolt.class.cast(this);

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LightningBolt;powerLightningRod()V", shift = At.Shift.AFTER))
    private void cleanWeatheringBlocks(CallbackInfo ci) {
        clearWeatheringBlocksOnLightningStrike(lightningBolt.level, this.getStrikePosition());
    }

    private static void clearWeatheringBlocksOnLightningStrike(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        BlockPos blockPos2;
        BlockState blockState2;
        if (blockState.is(Blocks.LIGHTNING_ROD)) {
            blockPos2 = blockPos.relative(blockState.getValue(LightningRodBlock.FACING).getOpposite());
            blockState2 = level.getBlockState(blockPos2);
        } else {
            blockPos2 = blockPos;
            blockState2 = blockState;
        }

        if (blockState2.getBlock() instanceof WeatheringBlock block && !block.getAge().isWaxed()) {
            level.setBlockAndUpdate(blockPos2, WeatheringBlock.getFirst(level.getBlockState(blockPos2)));
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
            int i = level.random.nextInt(3) + 3;

            for(int j = 0; j < i; ++j) {
                int k = level.random.nextInt(8) + 1;
                randomWalkCleaningWeatheringBlock(level, blockPos2, mutableBlockPos, k);
            }

        }
    }

    private static void randomWalkCleaningWeatheringBlock(Level level, BlockPos blockPos, BlockPos.MutableBlockPos mutableBlockPos, int i) {
        mutableBlockPos.set(blockPos);
        for(int j = 0; j < i; ++j) {
            Optional<BlockPos> optional = randomStepCleaningWeatheringBlock(level, mutableBlockPos);
            if (optional.isEmpty()) {
                break;
            }
            mutableBlockPos.set(optional.get());
        }
    }

    private static Optional<BlockPos> randomStepCleaningWeatheringBlock(Level level, BlockPos blockPos) {
        Iterator<BlockPos> blockPosIterator = BlockPos.randomInCube(level.random, 10, blockPos, 1).iterator();
        BlockPos blockPos2;
        BlockState blockState;
        do {
            if (!blockPosIterator.hasNext()) {
                return Optional.empty();
            }
            blockPos2 = blockPosIterator.next();
            blockState = level.getBlockState(blockPos2);
        } while(!(blockState.getBlock() instanceof WeatheringBlock));

        BlockPos finalBlockPos = blockPos2;
        WeatheringBlock.getPrevious(blockState).ifPresent((pBlockState) -> {
            level.setBlockAndUpdate(finalBlockPos, pBlockState);
        });
        level.levelEvent(3002, blockPos2, -1);
        return Optional.of(blockPos2);
    }
}
