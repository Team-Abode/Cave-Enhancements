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

    private int getPoweredTicks() {
        return timePoweredTicks;
    }

    private void setPoweredTicks(int value) {
        timePoweredTicks = value;
        this.setChanged();
    }

    private int getOutputSignal() {
        return output;
    }

    private void setOutputSignal(int value) {
        output = value;
        this.setChanged();
    }

    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("PoweredTicks", this.getPoweredTicks());
        nbt.putInt("OutputSignal", this.getOutputSignal());
    }

    public void load(CompoundTag nbt) {
        this.setPoweredTicks(nbt.getInt("PoweredTicks"));
        this.setOutputSignal(nbt.getInt("OutputSignal"));
    }
}
