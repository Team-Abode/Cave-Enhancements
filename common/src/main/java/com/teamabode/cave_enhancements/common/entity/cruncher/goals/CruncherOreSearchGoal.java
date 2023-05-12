package com.teamabode.cave_enhancements.common.entity.cruncher.goals;

import com.mojang.datafixers.util.Pair;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.core.registry.ModTags;
import com.teamabode.cave_enhancements.core.registry.misc.ModCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CruncherOreSearchGoal extends Goal {
    private final Cruncher cruncher;
    boolean isFinished = false;
    boolean reachedPosition = false;
    int goalTickTime = 0;
    @Nullable BlockPos targetPos = null;
    @Nullable BlockPos treasurePos = null;

    public CruncherOreSearchGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
    }

    public boolean canUse() {
        return cruncher.getEatingState() == 1;
    }

    public void start(){
        targetPos = getOrePosition(cruncher.getLevel(), 15, 150);
    }

    public boolean canContinueToUse() {
        return cruncher.getEatingState() == 1 && !isFinished && targetPos != null && goalTickTime < 300 && cruncher.getLastHurtByMob() == null;
    }

    public void tick() {
        goalTickTime++;

        if (goalTickTime >= 300) return;

        if (targetPos == null) return;

        cruncher.getNavigation().moveTo(cruncher.getNavigation().createPath(targetPos, 0), 1.5F);

        if (cruncher.position().distanceTo(Vec3.atCenterOf(targetPos)) <= 1D) {
            reachedPosition = true;

            isFinished = true;
        }
    }

    public void stop() {
        if (reachedPosition) {
            cruncher.setDeltaMovement(0.0, 0.0, 0.0);
            cruncher.teleportToWithTicket(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);
            cruncher.setEatingState(2);
            cruncher.setTargetPos(targetPos);

            if (cruncher.getFeedingPlayer() instanceof ServerPlayer serverPlayer && treasurePos != null) {
                ModCriteriaTriggers.CRUNCHER_FOUND_BLOCK_TRIGGER.trigger(serverPlayer, treasurePos);
            }
        } else {
            cruncher.particleResponse(false);
            cruncher.setEatingState(0);
            cruncher.getNavigation().stop();
        }
        goalTickTime = 0;
        targetPos = null;
        reachedPosition = false;
        isFinished = false;
    }

    @Nullable
    @SuppressWarnings("SameParameterValue")
    private BlockPos getOrePosition(Level level, int radius, int tries) {
        List<Pair<BlockPos, Integer>> potentialPositions = new ArrayList<>();

        for (int i = 0; i < tries; i++) {
            Vec3 vec = DefaultRandomPos.getPos(cruncher, radius, 5);

            if(vec == null) continue;

            BlockPos potentialPos = new BlockPos(vec);

            for (int j = 0; j <= 10; j++) {
                BlockPos pos = potentialPos.below(j);

                if (level.getBlockState(pos).getMaterial().isLiquid()) continue;
                if (level.getBlockState(pos).isAir()) continue;
                if (level.getBlockState(pos).is(Blocks.BEDROCK)) continue;
                if (!level.getBlockState(pos.below()).is(ModTags.CRUNCHER_CONSUMABLES)) continue;

                if (level.getBlockState(pos).is(ModTags.CRUNCHER_SEARCHABLES)) {
                    treasurePos = pos;
                    potentialPositions.add(Pair.of(potentialPos, pos.getY()));
                    break;
                }
            }
        }

        if (potentialPositions.size() == 0) return null;

        int randomSelection = level.getRandom().nextInt(potentialPositions.size());
        Pair<BlockPos, Integer> pair = potentialPositions.get(randomSelection);
        return pair.getFirst();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
