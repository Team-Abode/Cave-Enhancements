package com.teamabode.cave_enhancements.common.entity.cruncher;

import com.teamabode.cave_enhancements.common.entity.cruncher.goals.*;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModItems;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import com.teamabode.cave_enhancements.core.registry.ModTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Cruncher extends Animal {

    private static final EntityDataAccessor<Integer> EATING_STATE = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SEARCH_COOLDOWN_TIME = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ORE_POS_Y = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_POS = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Optional<UUID>> FEEDING_PLAYER = SynchedEntityData.defineId(Cruncher.class, EntityDataSerializers.OPTIONAL_UUID);

    public final AnimationState chompAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    public static final Predicate<ItemEntity> GLOW_BERRIES_ONLY = (itemEntity -> itemEntity.isAlive() && !itemEntity.hasPickUpDelay() && itemEntity.getItem().is(Items.GLOW_BERRIES));
    private int ticksSinceEaten = 0;

    public Cruncher(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
        this.setEatingState(0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CruncherEatBlockGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5F));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0F));
        this.goalSelector.addGoal(2, new CruncherOreSearchGoal(this));
        this.goalSelector.addGoal(2, new CruncherTemptGoal(this));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.25F));
        this.goalSelector.addGoal(3, new CruncherLookAtPlayerGoal(this));
        this.goalSelector.addGoal(3, new CruncherMoveToItemGoal(this));
        this.goalSelector.addGoal(3, new CruncherRandomStrollGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    // Save Data
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EATING_STATE, 0);
        this.entityData.define(SHEARED, false);
        this.entityData.define(SEARCH_COOLDOWN_TIME, 0);
        this.entityData.define(ORE_POS_Y, 0);
        this.entityData.define(TARGET_POS, Optional.empty());
        this.entityData.define(FEEDING_PLAYER, Optional.empty());
    }

    public float getScale() {
        return this.isBaby() ? 0.7F : 1.0F;
    }

    public int getEatingState() {
        return this.entityData.get(EATING_STATE);
    }

    public void setEatingState(int value) {
        this.entityData.set(EATING_STATE, value);
    }

    @Nullable
    public BlockPos getTargetPos() {
        return this.entityData.get(TARGET_POS).orElse(null);
    }

    public void setTargetPos(@Nullable BlockPos value) {
        this.entityData.set(TARGET_POS, value == null ? Optional.empty() : Optional.of(value));
    }

    public int getSearchCooldownTime() { return this.entityData.get(SEARCH_COOLDOWN_TIME); }

    public void setSearchCooldownTime(int value) { this.entityData.set(SEARCH_COOLDOWN_TIME, value); }

    public int getOrePosY() { return this.entityData.get(ORE_POS_Y); }

    public void setOrePosY(int value) { this.entityData.set(ORE_POS_Y, value); }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean value) {
        this.entityData.set(SHEARED, value);
    }

    @Nullable
    public Player getFeedingPlayer() {
        var uuid = this.entityData.get(FEEDING_PLAYER);
        return uuid.map(value -> this.level.getPlayerByUUID(value)).orElse(null);
    }

    public void setFeedingPlayer(@Nullable Player player) {
        this.entityData.set(FEEDING_PLAYER, player == null ? Optional.empty() : Optional.of(player.getUUID()));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("EatingState", getEatingState());
        compound.putBoolean("Sheared", isSheared());
        compound.putInt("SearchCooldownTime", getSearchCooldownTime());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setEatingState(compound.getInt("EatingState"));
        this.setSheared(compound.getBoolean("Sheared"));
        this.setSearchCooldownTime(compound.getInt("SearchCooldownTime"));
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby()) {
            if (itemStack.is(Items.SHEARS) && !this.isSheared()) {
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                itemStack.hurtAndBreak(1, player, pPlayer -> pPlayer.broadcastBreakEvent(hand));
                ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.MOSS_CARPET));
                itemEntity.setDeltaMovement(0.0D, 0.4D, 0.0D);
                level.addFreshEntity(itemEntity);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                return InteractionResult.SUCCESS;
            }
            if (itemStack.is(Items.GLOW_BERRIES) && this.getEatingState() == 0) {
                if (this.getSearchCooldownTime() > 0) {
                    particleResponse(false);
                    return InteractionResult.PASS;
                }
                this.setEatingState(1);
                this.setFeedingPlayer(player);
                this.eatingEffects(itemStack);
                if (!player.getAbilities().instabuild) itemStack.shrink(1);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                return InteractionResult.SUCCESS;
            }
            return super.mobInteract(player, hand);
        }
        return super.mobInteract(player, hand);
    }

    public void particleResponse(boolean success) {
        if (level instanceof ServerLevel server) {
            for (int i = 0; i < 7; i++) {
                double gaussX = this.random.nextGaussian() * 0.02;
                double gaussY = this.random.nextGaussian() * 0.02;
                double gaussZ = this.random.nextGaussian() * 0.02;

                server.sendParticles(success ? ParticleTypes.HAPPY_VILLAGER : ParticleTypes.SMOKE, this.getRandomX(0.6D), this.getRandomY() + 0.15, this.getRandomZ(0.6D), 1, gaussX, gaussY, gaussZ, 0.0F);
            }
        }
    }

    private boolean isMovingOnLand() {
        return this.onGround && this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWaterOrBubble();
    }

    public void tick() {
        if (this.getSearchCooldownTime() > 0 && this.tickCount % 20 == 0) {
            this.setSearchCooldownTime(getSearchCooldownTime() - 1);
        }
        if (this.getPose() == Pose.DIGGING) {
            if (level.isClientSide()) {
                chompAnimationState.startIfStopped(this.tickCount);
            }

        } else {
            if (level.isClientSide()) {
                chompAnimationState.stop();
            }
        }
        if (level.isClientSide()) {
            if (isMovingOnLand()) {
                walkAnimationState.startIfStopped(this.tickCount);
            } else {
                walkAnimationState.stop();
            }
        }
        super.tick();
    }

    // Ai Step
    public void aiStep() {
        if (!this.level.isClientSide() && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.is(Items.GLOW_BERRIES) && this.getSearchCooldownTime() == 0) {
                if (this.ticksSinceEaten > 200) {
                    ItemStack itemStack2 = itemStack.finishUsingItem(this.level, this);
                    if (!itemStack2.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack2);
                    }

                    this.ticksSinceEaten = 0;
                    this.setEatingState(1);
                } else if (this.ticksSinceEaten > 160 && this.random.nextBoolean()) {
                    this.eatingEffects(itemStack);
                }
            }
        }
        super.aiStep();
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.GLOW_BERRY_JUICE.get());
    }

    private void eatingEffects(ItemStack itemStack) {
        this.playSound(this.getEatingSound(itemStack), 1.0F, 1.0F);
        this.level.broadcastEntityEvent(this, (byte)45);
    }

    public void handleEntityEvent(byte id) {
        if (id == 45) {
            ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3 vec3 = (new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).xRot(-this.getXRot() * 0.017453292F).yRot(-this.getYRot() * 0.017453292F);
                    this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + this.getLookAngle().x / 2.0, this.getY(), this.getZ() + this.getLookAngle().z / 2.0, vec3.x, vec3.y + 0.05, vec3.z);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() && stack.is(Items.GLOW_BERRIES) && ticksSinceEaten > 0 && this.getEatingState() == 0 && this.getSearchCooldownTime() == 0 && !this.isBaby();
    }

    private void spitOutItem(ItemStack stack) {
        if (!stack.isEmpty() && !this.level.isClientSide) {
            ItemEntity itemEntity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0, this.getZ() + this.getLookAngle().z, stack);
            itemEntity.setPickUpDelay(40);
            itemEntity.setThrower(this.getUUID());

            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level.addFreshEntity(itemEntity);
        }
    }

    private void dropItemStack(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), stack);
        this.level.addFreshEntity(itemEntity);
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (this.canHoldItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItemStack(itemStack.split(i - 1));
            }
            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(itemEntity, itemStack.getCount());
            itemEntity.discard();
            ticksSinceEaten = 0;
        }

    }

    // Spawn Rules
    public static boolean checkCruncherSpawnRules(EntityType<? extends Animal> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getBlockState(blockPos.below()).is(ModTags.CRUNCHERS_SPAWNABLE_ON);
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 10.0F;
    }

    // Attribute Builder
    public static AttributeSupplier.Builder createCruncherAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_CRUNCHER_IDLE.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        SoundEvent sound = ModSounds.ENTITY_CRUNCHER_STEP.get();
        this.playSound(sound, 0.15F, 1.0F);
    }

    protected float nextStep() {
        return super.nextStep();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_CRUNCHER_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_CRUNCHER_HURT.get();
    }

    @Nullable
    public Cruncher getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.CRUNCHER.get().create(level);
    }
}
