package com.teamabode.cave_enhancements.common.entity.goop;

import com.teamabode.cave_enhancements.core.registry.ModItems;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class Goop extends Monster {
    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DRIP_COOLDOWN = SynchedEntityData.defineId(Goop.class, EntityDataSerializers.INT);
    private static final IntProvider TIME_UNTIL_DRIP = TimeUtil.rangeOfSeconds(80, 120);

    public Goop(EntityType<? extends Goop> entityType, Level level) {
        super(entityType, level);
        this.setDripCooldown(TIME_UNTIL_DRIP.sample(this.random));
    }

    public static AttributeSupplier.Builder createGoopAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 15);
    }

    public static boolean checkGoopSpawnRules(EntityType<? extends Goop> entityType, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random) {
        return serverLevel.getRawBrightness(blockPos, 0) == 0;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HANGING, false);
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(DRIP_COOLDOWN, 0);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setHanging(compoundTag.getBoolean("IsHanging"));
        this.setFromBucket(compoundTag.getBoolean("FromBucket"));
        this.setDripCooldown(compoundTag.getInt("DripCooldown"));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("IsHanging", this.isHanging());
        compoundTag.putBoolean("FromBucket", this.isFromBucket());
        compoundTag.putInt("DripCooldown", this.getDripCooldown());
    }

    public boolean isHanging() {
        return this.entityData.get(HANGING);
    }

    public void setHanging(boolean value) {
        this.entityData.set(HANGING, value);
    }

    public boolean isFromBucket() { return this.entityData.get(FROM_BUCKET); }

    public void setFromBucket(boolean value) { this.entityData.set(FROM_BUCKET, value); }

    public int getDripCooldown() {
        return entityData.get(DRIP_COOLDOWN);
    }

    public void setDripCooldown(int value) {
        entityData.set(DRIP_COOLDOWN, value);
    }

    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.getDirectEntity() instanceof Snowball) this.setHanging(false);
        if (damageSource.getDirectEntity() instanceof Frog) amount+=10;
        return super.hurt(damageSource, amount);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType == MobSpawnType.NATURAL) {
            Vec3 highestPos = this.findHighestPoint(this.blockPosition());
            if (highestPos != null) {
                this.setPos(highestPos);
                this.setHanging(true);
            }
        } else {
            this.setHanging(level.getBlockState(blockPosition().above()).entityCanStandOnFace(level, blockPosition(), this, Direction.UP));
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    // Called every tick when AI is enabled.
    public void aiStep() {
        super.aiStep();
        if (this.isHanging()) {
            if (!level.getBlockState(blockPosition().above()).entityCanStandOnFace(level, blockPosition(), this, Direction.UP)) {
                this.setHanging(false);
            }

            if (this.tickCount % 40 == 0) {
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class,
                        new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY() - 20, this.getZ()),
                        LivingEntity::canBeSeenAsEnemy
                );
                if (!list.isEmpty())
                    this.drip(false);
            }

            if (this.getDripCooldown() > 0) {
                this.setDripCooldown(this.getDripCooldown() - 1);
            } else {
                drip(true);
            }
        }
    }

    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        return false;
    }

    // Disables entity motion/velocity when hanging from a block.
    public void setDeltaMovement(Vec3 vec3) {
        if (!this.isHanging()) {
            super.setDeltaMovement(vec3);
        }
    }

    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        return scoopUp(player, interactionHand).orElse(super.mobInteract(player, interactionHand));
    }

    // Handles all bucket logic
    private Optional<InteractionResult> scoopUp(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.BUCKET) && this.isAlive()) {
            this.playSound(ModSounds.ITEM_BUCKET_FILL_GOOP.get(), 1.0F, 1.0F);
            ItemStack bucketStack = new ItemStack(ModItems.GOOP_BUCKET_ITEM.get());
            this.saveDataToBucket(bucketStack);
            ItemStack filledStack = ItemUtils.createFilledResult(itemStack, player, bucketStack, false);
            player.setItemInHand(interactionHand, filledStack);

            Level playerLevel = player.getLevel();

            if (!playerLevel.isClientSide()) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketStack);
            }
            this.discard();
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        } else {
            return Optional.empty();
        }
    }

    // Save data to bucket when scooped up.
    private void saveDataToBucket(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();

        if (this.hasCustomName()) {
            itemStack.setHoverName(this.getCustomName());
        }
        if (this.isNoAi()) {
            compoundTag.putBoolean("NoAI", this.isNoAi());
        }
        if (this.isSilent()) {
            compoundTag.putBoolean("Silent", this.isSilent());
        }
        if (this.isNoGravity()) {
            compoundTag.putBoolean("NoGravity", this.isNoGravity());
        }
        if (this.hasGlowingTag()) {
            compoundTag.putBoolean("Glowing", this.hasGlowingTag());
        }
        if (this.isInvulnerable()) {
            compoundTag.putBoolean("Invulnerable", this.isInvulnerable());
        }
        compoundTag.putFloat("Health", this.getHealth());
        compoundTag.putInt("DripCooldown", this.getDripCooldown());
    }

    // Load bucket data when placed by a bucket.
    public void loadDataFromBucket(CompoundTag compoundTag) {
        if (compoundTag.getBoolean("NoAI")) {
            this.setNoAi(compoundTag.getBoolean("NoAI"));
        }
        if (compoundTag.getBoolean("Silent")) {
            this.setSilent(compoundTag.getBoolean("Silent"));
        }
        if (compoundTag.getBoolean("NoGravity")) {
            this.setNoGravity(compoundTag.getBoolean("NoGravity"));
        }
        if (compoundTag.getBoolean("Glowing")) {
            this.setGlowingTag(compoundTag.getBoolean("Glowing"));
        }
        if (compoundTag.getBoolean("Invulnerable")) {
            this.setInvulnerable(compoundTag.getBoolean("Invulnerable"));
        }
        if (compoundTag.contains("Health", 99)) {
            this.setHealth(compoundTag.getFloat("Health"));
        }
        this.setDripCooldown(compoundTag.getInt("DripCooldown"));
    }

    // Spawns a goop drip at the entity's position.
    private void drip(boolean isTrap) {
        this.setDripCooldown(TIME_UNTIL_DRIP.sample(this.random));
        GoopDrip goopDrip = new GoopDrip(level, this);
        goopDrip.setTrap(isTrap);
        goopDrip.setPos(this.position());
        level.addFreshEntity(goopDrip);
    }

    // Finds the highest point that the entity can hang onto unless the world sky is open.
    private Vec3 findHighestPoint(BlockPos pos) {
        if (level.canSeeSky(pos)) return null;
        while (!level.getBlockState(pos.above()).entityCanStandOnFace(level, pos, this, Direction.UP)) {
            pos = pos.above();
        }
        return new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ENTITY_GOOP_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_GOOP_DEATH.get();
    }
}
