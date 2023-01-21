package com.teamabode.cave_enhancements.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        BlockPos endPos = this.getEndPos(level, context.getClickedPos());
        SoundEvent breakSound = this.getBlock().getSoundType(this.getBlock().defaultBlockState()).getPlaceSound();

        if (level.getBlockState(endPos).getMaterial().isReplaceable()) {
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
