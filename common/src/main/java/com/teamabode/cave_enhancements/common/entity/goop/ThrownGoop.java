package com.teamabode.cave_enhancements.common.entity.goop;

import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.misc.ModDamageSource;
import com.teamabode.cave_enhancements.core.registry.ModEffects;
import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownGoop extends ThrowableItemProjectile {
    public ThrownGoop(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public ThrownGoop(Level world, LivingEntity owner) {
        super(ModEntities.THROWN_GOOP.get(), owner, world);
    }

    public ThrownGoop(Level world, double x, double y, double z) {
        super(ModEntities.THROWN_GOOP.get(), x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.GOOP.get();
    }

    @Environment(EnvType.CLIENT)
    private ParticleOptions getParticleParameters() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.GOOP.get(), 1));
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            ParticleOptions particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(ModDamageSource.GOOP_DRIP, 2.0F);
            if (!livingEntity.hasEffect(ModEffects.STICKY.get())) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.STICKY.get(), 200, 0, false, true));
            }
        }
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
