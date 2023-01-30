package com.teamabode.cave_enhancements.core.registry.misc;

import com.teamabode.cave_enhancements.common.block.VolatileGoopBlock;
import com.teamabode.cave_enhancements.common.entity.HarmonicArrow;
import com.teamabode.cave_enhancements.common.entity.goop.ThrownGoop;
import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.ParametersAreNonnullByDefault;

public class ModDispenserBehavior {

    public static void init() {
        DispenserBlock.registerBehavior(ModItems.HARMONIC_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @ParametersAreNonnullByDefault
            protected Projectile getProjectile(Level world, Position position, ItemStack stack) {
                AbstractArrow persistentProjectileEntity = new HarmonicArrow(world, position.x(), position.y(), position.z());
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.ALLOWED;
                return persistentProjectileEntity;
            }
        });
        DispenserBlock.registerBehavior(ModItems.GOOP.get(), new AbstractProjectileDispenseBehavior() {
            @ParametersAreNonnullByDefault
            protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
                return Util.make(new ThrownGoop(level, position.x(), position.y(), position.z()), (goop) -> goop.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(ModBlocks.VOLATILE_GOOP.get(), new OptionalDispenseItemBehavior() {
            @ParametersAreNonnullByDefault
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                this.setSuccess(true);
                Level level = source.getLevel();

                if (!level.getBlockState(source.getPos()).is(Blocks.AIR)) {
                    Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);

                    BlockPos blockPos = source.getPos().relative(direction);

                    if (level.getBlockState(blockPos).getMaterial().isReplaceable()) {
                        BlockState blockState = ModBlocks.VOLATILE_GOOP.get().defaultBlockState().setValue(VolatileGoopBlock.FACING, direction);

                        level.setBlockAndUpdate(blockPos, blockState);
                        level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                        stack.shrink(1);
                    } else {
                        this.setSuccess(false);
                    }
                }

                return stack;
            }
        });
    }
}
