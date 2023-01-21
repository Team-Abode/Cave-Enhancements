package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.effect.StickyMobEffect;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

public class ModEffects {

    public static void init() {}

    public static final Supplier<MobEffect> STICKY = RegistryHelper.registerEffect("sticky", () -> new StickyMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "89266f72-4f61-4151-ac06-104ea9a17f22", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, "202186ab-317b-4064-a731-135065f562c8", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
}
