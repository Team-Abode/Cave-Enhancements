package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

@SuppressWarnings("deprecation")
public class LightningAnchorBlock extends Block {

    public LightningAnchorBlock(BlockBehaviour.Properties settings){
        super(settings);
    }

    public boolean isCharged() {
        return this == ModBlocks.CHARGED_LIGHTNING_ANCHOR.get();
    }

    private boolean isPowered(Level level, BlockPos blockPos){
        for (Direction dir : Direction.values()) {
            BlockPos relativePos = blockPos.relative(dir);
            BlockState state = level.getBlockState(relativePos);

            if (level.getSignal(relativePos, dir) > 0 && !state.is(Blocks.LIGHTNING_ROD)) {
                return true;
            }
        }
        return false;
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (this.isCharged()) {
            this.shockwave(level, blockPos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.isCharged() && this.isPowered(level, blockPos)) this.shockwave(level, blockPos);
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos neighborPos, boolean bl) {
        if (!this.isCharged()) {
            BlockState relativeState = level.getBlockState(blockPos.above());
            if (relativeState.is(Blocks.LIGHTNING_ROD) && relativeState.getValue(BlockStateProperties.POWERED) && relativeState.getValue(BlockStateProperties.FACING) == Direction.UP) {
                level.setBlockAndUpdate(blockPos, ModBlocks.CHARGED_LIGHTNING_ANCHOR.get().defaultBlockState());
            }
        } else {
            if (this.isPowered(level, blockPos)) this.shockwave(level, blockPos);
        }
    }

    private void knockBack(LivingEntity entity, BlockPos pos) {
        double d = entity.getX() - pos.getX();
        double e = entity.getZ() - pos.getZ();
        double f = Math.max(Math.sqrt(d * d + e * e), 0.001D);
        entity.setOnGround(false);
        entity.push((d / f) * 0.9D, 0.5D, (e / f) * 0.9D);
    }

    private void shockwave(Level level, BlockPos pos) {
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(4.0D));
        for (LivingEntity entity : list) {
            if (level instanceof ServerLevel serverLevel) {
                LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                level.addFreshEntity(bolt);
                bolt.discard();
                entity.thunderHit(serverLevel, bolt);
            }
            entity.hurt(DamageSource.LIGHTNING_BOLT, 8.0F);
            entity.hurtMarked = true;
            this.knockBack(entity, pos);
        }
        if (level instanceof ServerLevel server) server.sendParticles(ModParticles.SHOCKWAVE.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);

        level.setBlockAndUpdate(pos, ModBlocks.LIGHTNING_ANCHOR.get().defaultBlockState());
        level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (this.isCharged()) spawnParticles(level, blockPos);
    }

    private static void spawnParticles(Level level, BlockPos pos) {
        RandomSource randomSource = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.relative(direction);
            if (!level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.7 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.7 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.7 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                if (randomSource.nextInt(4) == 0) {
                    level.addParticle(ModParticles.CHARGE.get(), (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
