package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.goal.Goal;

public class DripstoneTortoiseOccasionalStompGoal extends Goal {

    private static final UniformInt OCCASIONAL_STOMP_COOLDOWN = TimeUtil.rangeOfSeconds(1, 2);
    private final DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoiseOccasionalStompGoal(DripstoneTortoise dripstoneTortoise) {
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return dripstoneTortoise.getOccasionalStompCooldown() == 0 && !dripstoneTortoise.isPregnant() && dripstoneTortoise.getTarget() == null && dripstoneTortoise.isOnGround() && !dripstoneTortoise.isAggressive() && !dripstoneTortoise.isBaby();
    }

    public boolean canContinueToUse() {
        return dripstoneTortoise.getOccasionalStompCooldown() == 0;
    }

    public void start() {
        for (int i = 0; i <= 10; i++) {
            double randomX = dripstoneTortoise.getRandomX(1);
            double randomZ = dripstoneTortoise.getRandomZ(1);

            dripstoneTortoise.summonPike(randomX, randomZ, dripstoneTortoise.getY() - 2, dripstoneTortoise.getY() + 5);
        }

        int newTime = OCCASIONAL_STOMP_COOLDOWN.sample(dripstoneTortoise.getRandom());

        dripstoneTortoise.setOccasionalStompCooldown(newTime);
    }
}
