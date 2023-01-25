package com.teamabode.cave_enhancements.common.effect;

import com.teamabode.cave_enhancements.common.access.LivingEntityAccess;
import com.teamabode.cave_enhancements.core.registry.ModEffects;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.Map;

public class ReversalMobEffect extends MobEffect {
    public ReversalMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf7addc);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "b0675f38-b536-4dda-b068-dfc145c2016d", 0.0F, AttributeModifier.Operation.ADDITION);
    }
    
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
        if (!entity.level.isClientSide) {

            for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
                AttributeInstance attributeInstance = attributeMapIn.getInstance(entry.getKey());
                int amount = ((LivingEntityAccess)entity).getReversalDamage();

                if (attributeInstance != null) {
                    AttributeModifier attributemodifier = entry.getValue();
                    attributeInstance.removeModifier(attributemodifier);
                    attributeInstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), "Reversal Boost", amount, attributemodifier.getOperation()));
                }
            }
        }
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void onHurt(LivingEntity victim, LivingEntity attacker, DamageSource source, float amount) {
        LivingEntityAccess victimData = (LivingEntityAccess) victim;
        LivingEntityAccess attackerData = (LivingEntityAccess) attacker;

        if (attacker.hasEffect(ModEffects.REVERSAL.get()) && attackerData.getReversalDamage() > 0 && !source.isProjectile()) {
            attacker.playSound(ModSounds.EFFECT_REVERSAL_REVERSE.get(), 1.0F, 1.0F);
            attackerData.setReversalDamage(0);
        }
        if (amount > 0 && victim.hasEffect(ModEffects.REVERSAL.get()) && !victim.isBlocking()) {
            int amountAdded = Math.min(Mth.floor(victimData.getReversalDamage() + (amount / 2)), 10);
            victimData.setReversalDamage(amountAdded);

            if (victim.level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 20; i++) {
                    RandomSource random = victim.getRandom();
                    serverLevel.sendParticles(
                            ModParticles.STAGNANT_SHIMMER.get(),
                            victim.getX() + random.nextGaussian() * 0.2,
                            victim.getEyeY() + random.nextGaussian() * 0.6,
                            victim.getZ() + random.nextGaussian() * 0.2,
                            1, 0.0D, 0.0D, 0.0D, 0.0D
                    );
                }
            }

        }
    }
}
