package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.common.block.entity.SpectacleCandleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class SpectacleCandleCakeBlock extends CandleCakeBlock implements EntityBlock {

    public SpectacleCandleCakeBlock(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SpectacleCandleBlockEntity(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (entityLevel, blockPos, state, entityType) -> SpectacleCandleBlockEntity.tick(level, blockPos, state);
    }
}
