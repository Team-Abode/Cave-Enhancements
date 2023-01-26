package com.teamabode.cave_enhancements.common.block.entity;

import com.teamabode.cave_enhancements.common.block.ReceiverBlock;
import com.teamabode.cave_enhancements.core.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ReceiverBlockEntity extends BlockEntity {

    public int timePoweredTicks = 0;
    public int output;

    public ReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REDSTONE_RECEIVER.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        int requiredPowerDurationTicks = ((ReceiverBlock) state.getBlock()).getRequiredPowerDurationTicks();

        if (state.getValue(BlockStateProperties.POWERED)) {
            if (timePoweredTicks < requiredPowerDurationTicks) timePoweredTicks++;
        } else{
            timePoweredTicks = 0;
        }
        level.setBlockAndUpdate(pos, state.setValue(ReceiverBlock.CAN_PASS, timePoweredTicks == requiredPowerDurationTicks));
    }

    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("PoweredTicks", this.timePoweredTicks);
        nbt.putInt("OutputSignal", this.output);
        this.setChanged();
        super.saveAdditional(nbt);
    }

    public void load(CompoundTag nbt) {
        timePoweredTicks = nbt.getInt("PoweredTicks");
        output = nbt.getInt("OutputSignal");
        super.load(nbt);
    }
}
