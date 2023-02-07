package com.teamabode.cave_enhancements.core.registry.misc;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties GLOW_BERRY_JUICE = new FoodProperties.Builder().nutrition(3).saturationMod(FoodConstants.FOOD_SATURATION_NORMAL).fast().build();
}
