package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise;

import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModTags;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DripstonePike extends Entity {

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    public DripstonePike(EntityType<? extends DripstonePike> entityType, Level world) {
        super(entityType, world);
    }

    public DripstonePike(Level level, double x, double y, double z, LivingEntity owner) {
        this(ModEntities.DRIPSTONE_PIKE.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner == null ? null : owner.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.ownerUUID);

            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }
    }

    public void tick() {
        if (this.tickCount % 4 == 0) {
            if (level.getBlockState(blockPosition().below()).is(ModTags.PIKE_DESTROYABLES)) {
                if (random.nextInt(4) == 0) {
                    ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.POINTED_DRIPSTONE));
                    level.addFreshEntity(itemEntity);
                }
                level.playSound(null, blockPosition(), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                level.broadcastEntityEvent(this, (byte) 45);
                this.remove(RemovalReason.DISCARDED);
            }

            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
            for (LivingEntity livingEntity : list) {
                this.dealDamageTo(livingEntity);
            }
        }
        if (this.tickCount % 10 == 0) {
            level.playSound(null, blockPosition(), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            level.broadcastEntityEvent(this, (byte) 45);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public void handleEntityEvent(byte id) {
        if (id == 45) {
            for (int i = 0; i <= 5; i++) {
                this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DRIPSTONE_BLOCK.defaultBlockState()), this.getX(), this.getY() + 0.5, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        } else {
            super.handleEntityEvent(id);
        }

    }

    private void dealDamageTo(LivingEntity target) {
        LivingEntity owner = this.getOwner();

        if (target.isAlive() && !target.isInvulnerable() && target != owner && !(target instanceof DripstoneTortoise))  {
            target.hurt(DamageSource.STALAGMITE, 2.0F);
        }
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    protected void defineSynchedData() {
    }
}
