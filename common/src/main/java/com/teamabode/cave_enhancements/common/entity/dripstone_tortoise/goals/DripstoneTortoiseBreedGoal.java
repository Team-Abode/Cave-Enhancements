package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals;

import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.GameRules;

public class DripstoneTortoiseBreedGoal extends BreedGoal {
    private final DripstoneTortoise dripstoneTortoise;

    public DripstoneTortoiseBreedGoal(DripstoneTortoise dripstoneTortoise, double d) {
        super(dripstoneTortoise, d);
        this.dripstoneTortoise = dripstoneTortoise;
    }

    public boolean canUse() {
        return super.canUse() && dripstoneTortoise.getTarget() == null && !dripstoneTortoise.isPregnant();
    }

    protected void breed() {
        ServerPlayer serverPlayer = this.animal.getLoveCause();
        if (serverPlayer == null && this.partner.getLoveCause() != null) {
            serverPlayer = this.partner.getLoveCause();
        }

        if (serverPlayer != null) {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            if (this.partner != null) {
                CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this.animal, this.partner, null);
            }
        }

        this.dripstoneTortoise.setPregnant(true);
        this.animal.resetLove();
        this.partner.resetLove();
        RandomSource randomSource = this.animal.getRandom();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomSource.nextInt(7) + 1));
        }

    }
}
