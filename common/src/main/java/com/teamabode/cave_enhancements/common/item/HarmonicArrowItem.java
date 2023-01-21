package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.common.entity.HarmonicArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HarmonicArrowItem extends ArrowItem {

    public HarmonicArrowItem(Properties settings) {
        super(settings);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter) {
        return new HarmonicArrow(world, shooter);
    }
}
