package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class DripstoneTortoiseAttackGoal extends Goal {
    private final DripstoneTortoise dripstoneTortoise;

    private long lastCanUseCheck;

    private Path path;

    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;

    public DripstoneTortoiseAttackGoal(DripstoneTortoise dripstoneTortoise) {
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        long l = dripstoneTortoise.level.getGameTime();

        if (l - lastCanUseCheck < 20L) {
            return false;
        } else {
            lastCanUseCheck = l;

            LivingEntity livingEntity = dripstoneTortoise.getTarget();

            if (livingEntity == null) return false;

            if (!livingEntity.isAlive()) return false;

            if (livingEntity.isBaby()) return false;

            path = dripstoneTortoise.getNavigation().createPath(livingEntity, 0);

            if(this.path == null) return false;

            return true;
        }
    }

    public boolean canContinueToUse() {
        LivingEntity livingEntity = dripstoneTortoise.getTarget();

        if (livingEntity == null) return false;

        if (!livingEntity.isAlive()) return false;

        if(!(livingEntity instanceof Player) || livingEntity.isSpectator() || ((Player)livingEntity).isCreative()) return false;

        return true;
    }

    public void start() {
        System.out.println("Attack start");

        if(path != null) dripstoneTortoise.getNavigation().moveTo(path, 1.25);

        dripstoneTortoise.setAggressive(true);

        ticksUntilNextPathRecalculation = 0;
        ticksUntilNextAttack = 0;
    }

    public void tick() {
        LivingEntity livingEntity = dripstoneTortoise.getTarget();

        if(livingEntity == null) return;

        dripstoneTortoise.getLookControl().setLookAt(livingEntity, 30.0F, 30.0F);

        double distance = dripstoneTortoise.distanceTo(livingEntity);

        ticksUntilNextPathRecalculation = Math.max(ticksUntilNextPathRecalculation - 1, 0);

        if (dripstoneTortoise.getSensing().hasLineOfSight(livingEntity)) {
            if(ticksUntilNextPathRecalculation <= 0) {
                if(dripstoneTortoise.distanceTo(livingEntity) > 5) {
                    ticksUntilNextPathRecalculation = 4 + dripstoneTortoise.getRandom().nextInt(7);

                    if (distance > 1024.0D) {
                        ticksUntilNextPathRecalculation += 10;
                    } else if (distance > 256.0D) {
                        ticksUntilNextPathRecalculation += 5;
                    }

                    if (!dripstoneTortoise.getNavigation().moveTo(livingEntity, 1.25)) {
                        ticksUntilNextPathRecalculation += 15;
                    }

                    ticksUntilNextPathRecalculation = adjustedTickDelay(ticksUntilNextPathRecalculation);
                }else {
                    dripstoneTortoise.getNavigation().stop();
                }
            }

            ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);

            checkAndPerformAttack(livingEntity);
        }
    }

    protected void checkAndPerformAttack(LivingEntity enemy) {
        if(ticksUntilNextAttack > 0) return;

        if(dripstoneTortoise.distanceTo(enemy) > 7) return;

        resetAttackCooldown();

        for (int i = 0; i <= 10; i++) {
            double randomX = dripstoneTortoise.getRandom().nextFloat() * 3F - 3F / 2F;
            double randomZ = dripstoneTortoise.getRandom().nextFloat() * 3F - 3F / 2F;

            double minY = Math.min(enemy.getY(), dripstoneTortoise.getY());
            double maxY = Math.max(enemy.getY(), dripstoneTortoise.getY()) + 1.0;

            dripstoneTortoise.summonPike(enemy.getX() + randomX, enemy.getZ() + randomZ, minY, maxY);
        }
    }

    public void stop() {
        dripstoneTortoise.setAggressive(false);
        dripstoneTortoise.getNavigation().stop();
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(30);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
