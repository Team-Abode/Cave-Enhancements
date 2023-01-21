package com.teamabode.cave_enhancements.common.block;

import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class VolatileGoopBlock extends Block {


    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public VolatileGoopBlock(Properties properties) {
        super(properties);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isCrouching()) {
            return this.defaultBlockState().setValue(FACING, context.getClickedFace());
        }
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {

            activate(state, level, pos);
            if (!player.isCreative()) {
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));
                } else {
                    stack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            activate(state, level, pos);
        }
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            activate(state, level, pos);
        }
    }

    public void activate(BlockState state, Level level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        for (int i = 1; i < 7; i++) {
            if (level instanceof ServerLevel server) {
                server.playSound(null, pos.relative(direction, i), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0F, 1.0F);
                server.sendParticles(ModParticles.GOOP_EXPLOSION.get(), pos.relative(direction, i).getX() + 0.5, pos.relative(direction, i).getY() + 0.5, pos.relative(direction, i).getZ() + 0.5, 1, 0, 0, 0, 0);
            }
            level.removeBlock(pos, false);
            this.explode(level, null, pos.relative(direction, i).getX() + 0.5, pos.relative(direction, i).getY() + 0.5, pos.relative(direction, i).getZ() + 0.5, 1.5F, false, Explosion.BlockInteraction.BREAK);
        }
    }

    public void explode(Level level, @Nullable Entity exploder, double x, double y, double z, float size, boolean causesFire, Explosion.BlockInteraction mode) {
        Explosion explosion = new Explosion(level, exploder, x, y, z, size, causesFire, mode);
        explosion.explode();
        explosion.finalizeExplosion(false);
    }

}
