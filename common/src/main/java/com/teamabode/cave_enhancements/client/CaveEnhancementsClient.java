package com.teamabode.cave_enhancements.client;

import com.teamabode.cave_enhancements.core.registry.ModPotions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

public class CaveEnhancementsClient {

    public static void init() {
    }

    public static void addPotionTooltip(ItemStack itemStack, TooltipFlag tooltipFlag, List<Component> componentList) {
        if (PotionUtils.getPotion(itemStack) == ModPotions.REVERSAL.get() || PotionUtils.getPotion(itemStack) == ModPotions.LONG_REVERSAL.get()) {
            for (int i = 0; i < componentList.size(); i++) {
                Component component = componentList.get(i);
                if (component.contains(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE))) {
                    componentList.remove(i);
                    break;
                }
            }
        }
    }
}
