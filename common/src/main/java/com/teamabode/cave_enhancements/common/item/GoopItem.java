package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.common.entity.goop.ThrownGoop;
import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class GoopItem extends Item {

    public GoopItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.ITEM_GOOP_THROW.get(), SoundSource.NEUTRAL, 0.5F, 1.0F);
        if (!level.isClientSide) {
            ThrownGoop thrownGoop = new ThrownGoop(level, player);
            thrownGoop.setItem(itemStack);
            thrownGoop.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownGoop);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    public InteractionResult useOn(UseOnContext context) {
        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(context);
        if (!blockPlaceContext.canPlace()) return InteractionResult.FAIL;

        Level level = blockPlaceContext.getLevel();
        BlockPos pos = blockPlaceContext.getClickedPos();
        Player player = blockPlaceContext.getPlayer();
        ItemStack itemStack = blockPlaceContext.getItemInHand();

        BlockState state = ModBlocks.GOOP_SPLAT.get().getStateForPlacement(blockPlaceContext);
        BlockState clickedState = level.getBlockState(pos);

        if (state != null) {
            RandomSource randomSource = level.getRandom();
            level.setBlockAndUpdate(pos, state);
            level.playSound(player, pos, ModSounds.BLOCK_GOOP_DECORATION_PLACE.get(), SoundSource.BLOCKS, 0.5F, randomSource.nextFloat() * 0.2F + 0.9F);
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, clickedState));

            if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos, itemStack);
            clickedState.getBlock().setPlacedBy(level, pos, clickedState, player, itemStack);

            if (player == null || !player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
