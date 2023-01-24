package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class DrippingGoopItem extends BlockItem {
    public DrippingGoopItem(Block block, Properties properties) {
        super(block, properties);
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        if (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).is(this.getBlock())) {
            return useOnDrippingGoop(useOnContext);
        }
        return super.useOn(useOnContext);
    }

    public InteractionResult useOnDrippingGoop(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos endPos = this.getEndPos(level, clickedPos);
        SoundEvent breakSound = this.getBlock().getSoundType(this.getBlock().defaultBlockState()).getPlaceSound();

        if (level.getBlockState(endPos).getMaterial().isReplaceable() && endPos.getY() > level.getMinBuildHeight() - 1) {
            if (player instanceof ServerPlayer serverPlayer)
                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, clickedPos, context.getItemInHand());

            if (player == null || !player.getAbilities().instabuild)
                context.getItemInHand().shrink(1);

            level.gameEvent(player, GameEvent.BLOCK_PLACE, clickedPos);
            level.playSound(null, context.getClickedPos(), breakSound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(endPos, this.getBlock().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(endPos).getType() == Fluids.WATER));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public BlockPos getEndPos(Level level, BlockPos pos) {
        Block block = this.getBlock();
        while (level.getBlockState(pos).is(block)) {
            pos = pos.below();
        }
        return pos;
    }
}
