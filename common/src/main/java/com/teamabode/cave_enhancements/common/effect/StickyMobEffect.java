package com.teamabode.cave_enhancements.common.effect;

import com.teamabode.cave_enhancements.core.registry.misc.ModDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StickyMobEffect extends MobEffect {

    public StickyMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xf0dead);
    }

    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Vec3 motion = livingEntity.getDeltaMovement();

        if (motion.y > 0.0 && livingEntity.getRandom().nextFloat() > 0.25F) {
            livingEntity.hurt(ModDamageSource.VISCOUS, 1.0F + ((float) amplifier / 10) );
            if (livingEntity instanceof Player player) {
                player.causeFoodExhaustion(0.5F + ((float) amplifier / 10) );
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
