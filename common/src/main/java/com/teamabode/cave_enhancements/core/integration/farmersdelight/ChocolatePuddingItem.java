package com.teamabode.cave_enhancements.core.integration.farmersdelight;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChocolatePuddingItem extends Item {

    public ChocolatePuddingItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        if (!level.isClientSide()) {
            ArrayList<MobEffect> harmfulEffects = new ArrayList<>();
            RandomSource random = livingEntity.getRandom();
            for (MobEffectInstance effectInstance : livingEntity.getActiveEffects()) {
                if (effectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                    harmfulEffects.add(effectInstance.getEffect());
                }
            }
            if (harmfulEffects.size() > 0) livingEntity.removeEffect(harmfulEffects.get(random.nextInt(harmfulEffects.size())));
        }
        return livingEntity instanceof Player player && player.getAbilities().instabuild ? super.finishUsingItem(itemStack, level, livingEntity) : new ItemStack(Items.BOWL);
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        Component textComponent = Component.translatable("farmersdelight.tooltip.hot_cocoa").withStyle(ChatFormatting.BLUE);
        list.add(textComponent);
    }
}
