package com.teamabode.cave_enhancements.common.entity.goop;

import com.teamabode.cave_enhancements.core.registry.*;
import com.teamabode.cave_enhancements.core.registry.misc.ModDamageSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GoopDrip extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Boolean> TRAP = SynchedEntityData.defineId(GoopDrip.class, EntityDataSerializers.BOOLEAN);

    public GoopDrip(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public GoopDrip(Level world, LivingEntity owner) {
        super(ModEntities.GOOP_DRIP.get(), owner, world);
    }

    public GoopDrip(Level world, double x, double y, double z) {
        super(ModEntities.GOOP_DRIP.get(), x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ModItems.GOOP_DRIP.get();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TRAP, false);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTrap(compoundTag.getBoolean("Trap"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Trap", this.isTrap());
    }

    public boolean isTrap() {
        return this.entityData.get(TRAP);
    }

    public void setTrap(boolean value) {
        this.entityData.set(TRAP, value);
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(ModDamageSource.GOOP_DRIP, 5.0F);
            livingEntity.addEffect(new MobEffectInstance(ModEffects.STICKY.get(), 100, 1));
        }
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level.isClientSide()) {
            BlockPos pos = new BlockPos(blockHitResult.getLocation());
            if (level.getBlockState(pos).getMaterial().isReplaceable() && this.isTrap()) {
                level.setBlockAndUpdate(pos, ModBlocks.GOOP_TRAP.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER));
            }
        }
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Environment(EnvType.CLIENT)
    private ParticleOptions getParticleParameters() {
        return new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ModItems.GOOP_DRIP.get(), 1));
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
}
