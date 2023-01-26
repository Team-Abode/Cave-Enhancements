package com.teamabode.cave_enhancements.common.entity.cruncher.goals;

import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class CruncherRandomStrollGoal extends Goal {

    private final Cruncher cruncher;
    private Vec3 targetPos;
    private int walkTime = 0;

    public CruncherRandomStrollGoal(Cruncher cruncher) {
        this.cruncher = cruncher;
    }

    public boolean canUse() {
        if(cruncher.getRandom().nextInt(reducedTickDelay(120)) != 0) return false;
        return cruncher.getEatingState() == 0;
    }

    public void start(){
        targetPos = DefaultRandomPos.getPos(cruncher, 10, 7);
        walkTime = 0;
        if(targetPos == null) return;
        cruncher.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, 1.0D);
    }

    public void tick() {
        walkTime++;
    }

    public boolean canContinueToUse() {
        return targetPos != null && !cruncher.getNavigation().isDone() && walkTime < 500;
    }

    public void stop(){
        cruncher.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
