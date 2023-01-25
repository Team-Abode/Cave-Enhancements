package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.core.registry.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

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
}
