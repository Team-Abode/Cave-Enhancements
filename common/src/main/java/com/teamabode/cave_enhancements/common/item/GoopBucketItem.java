package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class GoopBucketItem extends Item {

    public GoopBucketItem(Properties properties) {
        super(properties);
    }

    private void spawnGoop(ServerLevel level, ItemStack stack, BlockPos pos) {
        Goop goop = (Goop) ModEntities.GOOP.get().spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (goop != null) {
            goop.loadDataFromBucket(stack.getOrCreateTag());
            goop.setFromBucket(true);
        }
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            Player player = useOnContext.getPlayer();
            InteractionHand hand = useOnContext.getHand();

            BlockPos blockPos2;
            if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.relative(direction);
            }

            if (player != null && !player.isCreative()) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
            this.spawnGoop((ServerLevel)level, itemStack, blockPos2);
            level.playSound(null, blockPos, ModSounds.ITEM_BUCKET_EMPTY_GOOP.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(useOnContext.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            return InteractionResult.CONSUME;
        }
    }
}
