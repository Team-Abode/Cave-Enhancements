package com.teamabode.cave_enhancements.common.block.entity;

import com.teamabode.cave_enhancements.core.registry.ModBlockEntities;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RoseQuartzChimesBlockEntity extends BlockEntity {
    private int chimeCooldown;
    public int ticks;

    public RoseQuartzChimesBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROSE_QUARTZ_CHIMES.get(), pos, state);
        this.chimeCooldown = 600;
        this.ticks = 0;
    }

    public static void tick(Level level, BlockPos pos, RoseQuartzChimesBlockEntity entity) {
        entity.ticks++;
        if (entity.getChimeCooldown() > 0) {
            entity.setChimeCooldown(entity.getChimeCooldown() - 1);
        } else {
            AABB box = new AABB(pos).inflate(8.0D);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity livingEntity : list) {
                applyEffects(level, livingEntity, livingEntity instanceof Monster ? MobEffects.WEAKNESS : MobEffects.REGENERATION);
                level.addParticle(ModParticles.ROSE_CHIME.get(), entity.getBlockPos().getX() + 0.5D, entity.getBlockPos().getY() + 0.3D, entity.getBlockPos().getZ() + 0.5D, 0D, 0D, 0D);
                level.playSound(null, pos, ModSounds.BLOCK_ROSE_QUARTZ_CHIMES_CHIME.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            entity.setChimeCooldown(600);
        }
    }

    private static void applyEffects(Level level, LivingEntity livingEntity, MobEffect effect) {
        livingEntity.addEffect(new MobEffectInstance(effect, 100, level.isRaining() ? 1 : 0, false, true));
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("ChimeCooldown", this.getChimeCooldown());
    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.setChimeCooldown(compoundTag.getInt("ChimeCooldown"));
    }

    public int getChimeCooldown() {
        return chimeCooldown;
    }

    public void setChimeCooldown(int value) {
        chimeCooldown = value;
        this.setChanged();
    }
}
