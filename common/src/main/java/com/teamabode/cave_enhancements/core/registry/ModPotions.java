package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class ModPotions {

    public static final Supplier<Potion> REVERSAL = RegistryHelper.registerPotion("reversal", () -> new Potion(new MobEffectInstance(ModEffects.REVERSAL.get(), 1800)));
    public static final Supplier<Potion> LONG_REVERSAL = RegistryHelper.registerPotion("long_reversal", () -> new Potion(new MobEffectInstance(ModEffects.REVERSAL.get(), 3600)));

    public static void init() {

    }
}
