package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.common.block.DripstoneTortoiseEggBlock;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;

public class DripstoneTortoiseLayEggGoal extends MoveToBlockGoal {
    private final DripstoneTortoise dripstoneTortoise;
    boolean hasLayedEggs;

    public DripstoneTortoiseLayEggGoal(DripstoneTortoise dripstoneTortoise, double d) {
        super(dripstoneTortoise, d, 16);
        hasLayedEggs = false;
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return dripstoneTortoise.isPregnant() && dripstoneTortoise.getTarget() == null && !dripstoneTortoise.isBaby() && super.canUse();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && dripstoneTortoise.getTarget() == null && dripstoneTortoise.isPregnant();
    }

    public void tick() {
        super.tick();
        if (this.isReachedTarget() && !hasLayedEggs) {
            dripstoneTortoise.level.setBlock(blockPos.below(), ModBlocks.DRIPSTONE_TORTOISE_EGG.get().defaultBlockState().setValue(DripstoneTortoiseEggBlock.WATERLOGGED, true).setValue(DripstoneTortoiseEggBlock.HATCH, 0), 3);
            dripstoneTortoise.setPregnant(false);
            hasLayedEggs = true;
        }
    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Blocks.WATER);
    }
}
