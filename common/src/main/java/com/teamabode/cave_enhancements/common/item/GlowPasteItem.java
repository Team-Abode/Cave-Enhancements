package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GlowPasteItem extends Item {

    public GlowPasteItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {

        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(context);
        if (!blockPlaceContext.canPlace()) return InteractionResult.FAIL;

        Level level = blockPlaceContext.getLevel();
        BlockPos pos = blockPlaceContext.getClickedPos();
        Player player = blockPlaceContext.getPlayer();
        ItemStack itemStack = blockPlaceContext.getItemInHand();

        BlockState state = ModBlocks.GLOW_SPLOTCH.get().getStateForPlacement(blockPlaceContext);
        BlockState clickedState = level.getBlockState(pos);

        int itemDamage = itemStack.getDamageValue();
        if (itemDamage == itemStack.getMaxDamage() - 1 && player.getOffhandItem().is(Items.GLOW_INK_SAC) && !player.getAbilities().instabuild) return InteractionResult.PASS;

        if (state != null) {
            RandomSource randomSource = level.getRandom();

            level.setBlockAndUpdate(pos, state);
            level.playSound(player, pos, ModSounds.ITEM_GLOW_PASTE_PLACE.get(), SoundSource.BLOCKS, 0.5F, randomSource.nextFloat() * 0.2F + 0.9F);
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, clickedState));

            if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos, itemStack);
            clickedState.getBlock().setPlacedBy(level, pos, clickedState, player, itemStack);

            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.GLOW_SQUID_INK, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1, 0.0D, 0.0D, 0.0D, 0);
            }

            if (player == null || !player.getAbilities().instabuild) {
                itemStack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(blockPlaceContext.getHand()));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        ItemStack offHandItemStack = player.getOffhandItem();
        int itemDurability = itemStack.getDamageValue();

        if (itemDurability == itemStack.getMaxDamage() - 1 && offHandItemStack.is(Items.GLOW_INK_SAC) && !player.getAbilities().instabuild) {
            level.playSound(player, player.getOnPos(), SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemStack.setDamageValue(0);
            offHandItemStack.shrink(1);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}