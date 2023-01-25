package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.access.LivingEntityAccess;
import com.teamabode.cave_enhancements.common.effect.ReversalMobEffect;
import com.teamabode.cave_enhancements.core.registry.ModEffects;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {
    private static final EntityDataAccessor<Integer> REVERSAL_DAMAGE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

    @Shadow public abstract boolean hasEffect(MobEffect arg);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getJumpPower", at = @At("HEAD"), cancellable = true)
    private void getJumpPower(CallbackInfoReturnable<Float> cir) {
        if (this.hasEffect(ModEffects.STICKY.get())) {
            float jumpPower = 0.42F * this.getBlockJumpFactor();
            cir.setReturnValue(jumpPower / 2.0F);
        }
    }

    @Inject(method="defineSynchedData", at = @At("TAIL"))
    private void addReversalData(CallbackInfo ci) {
        this.entityData.define(REVERSAL_DAMAGE, 0);
    }

    @Inject(method="addAdditionalSaveData", at = @At("TAIL"))
    private void addReversalTag(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putInt(CaveEnhancements.MODID + ":" + "reversal_damage", this.getReversalDamage());
    }

    @Inject(method="readAdditionalSaveData", at = @At("TAIL"))
    private void readReversalTag(CompoundTag compoundTag, CallbackInfo ci) {
        this.setReversalDamage(compoundTag.getInt(CaveEnhancements.MODID + ":" + "reversal_damage"));
    }

    public int getReversalDamage() {
        return this.entityData.get(REVERSAL_DAMAGE);
    }

    public void setReversalDamage(int damage) {
        this.entityData.set(REVERSAL_DAMAGE, damage);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof LivingEntity livingAttacker)
            ReversalMobEffect.onHurt(LivingEntity.class.cast(this), livingAttacker, source, amount);
    }
}
