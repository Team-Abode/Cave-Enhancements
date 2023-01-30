package com.teamabode.cave_enhancements.common.entity.dripstone_tortoise;

import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals.DripstoneTortoiseAttackGoal;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals.DripstoneTortoiseBreedGoal;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals.DripstoneTortoiseLayEggGoal;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.goals.DripstoneTortoiseOccasionalStompGoal;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DripstoneTortoise extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(DripstoneTortoise.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STOMP_TIME = SynchedEntityData.defineId(DripstoneTortoise.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PREGNANT = SynchedEntityData.defineId(DripstoneTortoise.class, EntityDataSerializers.BOOLEAN);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(10, 22);

    public final AnimationState stompingAnimationState = new AnimationState();
    private int occasionalStompCooldown;
    @Nullable private UUID persistentAngerTarget;

    public DripstoneTortoise(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
        this.setOccasionalStompCooldown(1200);
        this.xpReward = 15;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(STOMP_TIME, 0);
        this.entityData.define(PREGNANT, false);
    }

    // NBT
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        addPersistentAngerSaveData(compound);
        compound.putBoolean("Pregnant", this.isPregnant());
        compound.putInt("OccasionalStompCooldown", this.getOccasionalStompCooldown());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        readPersistentAngerSaveData(this.level, compound);
        this.setPregnant(compound.getBoolean("Pregnant"));
        this.setOccasionalStompCooldown(compound.getInt("OccasionalStompCooldown"));
    }

    // Sounds
    protected SoundEvent getDeathSound() {
        return this.isBaby() ? ModSounds.ENTITY_DRIPSTONE_TORTOISE_BABY_DEATH.get() : ModSounds.ENTITY_DRIPSTONE_TORTOISE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return this.isBaby() ? ModSounds.ENTITY_DRIPSTONE_TORTOISE_BABY_HURT.get() : ModSounds.ENTITY_DRIPSTONE_TORTOISE_HURT.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isBaby() ? null : ModSounds.ENTITY_DRIPSTONE_TORTOISE_IDLE.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent sound = this.isBaby() ? SoundEvents.WOLF_STEP : ModSounds.ENTITY_DRIPSTONE_TORTOISE_STEP.get();
        this.playSound(sound, 0.15F, 1.0F);
    }

    // Goals
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(1, new DripstoneTortoiseAttackGoal(this));
        this.goalSelector.addGoal(1, new DripstoneTortoiseBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new DripstoneTortoiseLayEggGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new DripstoneTortoiseOccasionalStompGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createDripstoneTortoiseAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.125D)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 5)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource == DamageSource.STALAGMITE || damageSource == DamageSource.FALLING_STALACTITE || damageSource.isProjectile() || damageSource.getEntity() instanceof DripstoneTortoise) return true;
        return super.isInvulnerableTo(damageSource);
    }

    public float getScale() {
        return this.isBaby() ? 0.5F : 1.0F;
    }

    public void tick() {
        if (this.level.isClientSide) {
            if (this.getStompTime() > 0) {
                stompingAnimationState.startIfStopped(this.tickCount);
            } else {
                stompingAnimationState.stop();
            }
        }
        super.tick();
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(Items.BROWN_MUSHROOM);
    }

    public void aiStep() {
        if (this.getStompTime() > 0) {
            this.setStompTime(this.getStompTime() - 1);
        }
        if (tickCount % 20 == 0 && this.getOccasionalStompCooldown() > 0) {
            this.setOccasionalStompCooldown(this.getOccasionalStompCooldown() - 1);
        }
        super.aiStep();
    }
    public boolean canBeLeashed(Player player) {
        return false;
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor serverLevelAccessor, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevelAccessor.getBrightness(LightLayer.SKY, blockPos) > randomSource.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensionType = serverLevelAccessor.dimensionType();
            int i = dimensionType.monsterSpawnBlockLightLimit();
            if (i < 15 && serverLevelAccessor.getBrightness(LightLayer.BLOCK, blockPos) > i) {
                return false;
            } else {
                int j = serverLevelAccessor.getLevel().isThundering() ? serverLevelAccessor.getMaxLocalRawBrightness(blockPos, 10) : serverLevelAccessor.getMaxLocalRawBrightness(blockPos);
                return j <= dimensionType.monsterSpawnLightTest().sample(randomSource);
            }
        }
    }

    public static boolean checkDripstoneTortoiseSpawnRules(EntityType<? extends Animal> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource);
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 10.0F;
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, remainingPersistentAngerTime);
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public int getStompTime() {
        return this.entityData.get(STOMP_TIME);
    }

    public void setStompTime(int value) {
        this.entityData.set(STOMP_TIME, value);
    }

    public int getOccasionalStompCooldown() {
        return occasionalStompCooldown;
    }

    public void setOccasionalStompCooldown(int value) {
        occasionalStompCooldown = value;
    }

    public boolean isPregnant() {
        return this.entityData.get(PREGNANT);
    }

    public void setPregnant(boolean value) {
        this.entityData.set(PREGNANT, value);
    }

    public void summonPike(double x, double z, double minY, double maxY){
        BlockPos blockPos = new BlockPos(x, maxY, z);
        boolean finishedCalculation = false;
        double d = 0.0D;

        do {
            BlockPos belowPos = blockPos.below();
            BlockState state = level.getBlockState(belowPos);
            if (state.isFaceSturdy(level, belowPos, Direction.UP)) {
                if (!level.isEmptyBlock(blockPos)) {
                    BlockState blockState2 = level.getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(level, blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.max(Direction.Axis.Y);
                    }
                }

                finishedCalculation = true;
                break;
            }
            blockPos = blockPos.below();
        } while (blockPos.getY() >= Mth.floor(minY) - 1);

        if (finishedCalculation) {
            this.setStompTime(5);
            this.setDeltaMovement(0.0D, 0.25D, 0.0D);
            DripstonePike dripstonePike = new DripstonePike(level, x, this.getY() + d, z, this);
            level.addFreshEntity(dripstonePike);
        }
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.DRIPSTONE_TORTOISE.get().create(level);
    }
}
