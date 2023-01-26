package com.teamabode.cave_enhancements.common.block.weathering;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;
import java.util.function.Supplier;

public interface WeatheringBlock extends ChangeOverTimeBlock<WeatherState> {

    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(HashBiMap::create);

    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    Supplier<BiMap<Block, Block>> WAX_MAP = Suppliers.memoize(HashBiMap::create);

    Supplier<BiMap<Block, Block>> UNWAX_MAP = Suppliers.memoize(HashBiMap::create);

    static void addWeatherBlockPair(Block firstBlock, Block secondBlock) {
        NEXT_BY_BLOCK.get().put(firstBlock, secondBlock);
    }

    static void addWaxBlockPair(Block firstBlock, Block secondBlock) {
        WAX_MAP.get().put(firstBlock, secondBlock);
    }

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }

    static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(block));
    }

    static Block getFirst(Block block) {
        Block firstBlock = block;

        for (Block blockIterator = PREVIOUS_BY_BLOCK.get().get(block); blockIterator != null; blockIterator = PREVIOUS_BY_BLOCK.get().get(blockIterator)) {
            firstBlock = blockIterator;
        }
        return firstBlock;
    }

    default Optional<BlockState> getNext(BlockState blockState) {
        return getNext(blockState.getBlock()).map(block -> block.withPropertiesOf(blockState));
    }

    static Optional<BlockState> getPrevious(BlockState blockState) {
        return getPrevious(blockState.getBlock()).map(block -> block.withPropertiesOf(blockState));
    }

    static BlockState getFirst(BlockState blockState) {
        return getFirst(blockState.getBlock()).withPropertiesOf(blockState);
    }

    static Optional<Block> getNextWaxedBlock(Block block) {
        return Optional.ofNullable(WAX_MAP.get().get(block));
    }

    static Optional<BlockState> getNextWaxedState(BlockState blockState) {
        return getNextWaxedBlock(blockState.getBlock()).map(block -> block.withPropertiesOf(blockState));
    }

    static Optional<Block> getNextUnwaxedBlock(Block block) {
        return Optional.ofNullable(WAX_MAP.get().inverse().get(block));
    }

    static Optional<BlockState> getNextUnwaxedState(BlockState blockState) {
        return getNextUnwaxedBlock(blockState.getBlock()).map(block -> block.withPropertiesOf(blockState));
    }

    default Optional<InteractionResult> interactLogic(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (itemStack.getItem() instanceof AxeItem) {
            if (this.getAge().isWaxed()) {
                if (getNextUnwaxedState(blockState).isEmpty()) return Optional.empty();

                level.playSound(player, blockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3004, blockPos, 0);
                level.setBlockAndUpdate(blockPos, getNextUnwaxedState(blockState).get());
            }
            else {
                if (getPrevious(blockState).isEmpty()) return Optional.empty();

                level.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, blockPos, 0);
                level.setBlockAndUpdate(blockPos, getPrevious(blockState).get());
            }
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
            }
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            itemStack.hurtAndBreak(1, player, pPlayer -> pPlayer.broadcastBreakEvent(interactionHand));
            return Optional.of(InteractionResult.SUCCESS);
        }
        if (itemStack.getItem() instanceof HoneycombItem) {
            if (getNextWaxedState(blockState).isEmpty()) return Optional.empty();

            level.levelEvent(player, 3003, blockPos, 0);
            level.setBlockAndUpdate(blockPos, getNextWaxedState(blockState).get());
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
            }
            if (!player.getAbilities().instabuild) itemStack.shrink(1);
            return Optional.of(InteractionResult.SUCCESS);
        }
        return Optional.empty();
    }

    default float getChanceModifier() {
        return this.getAge() == WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }
}
