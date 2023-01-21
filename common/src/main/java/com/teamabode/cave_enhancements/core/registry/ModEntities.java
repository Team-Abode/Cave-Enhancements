package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.entity.HarmonicArrow;
import com.teamabode.cave_enhancements.common.entity.goop.ThrownGoop;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ModEntities {

    public static void init() {}

    public static final Supplier<EntityType<ThrownGoop>> THROWN_GOOP = RegistryHelper.registerEntityType("thrown_goop", () -> thrownGoopBuilder().build("thrown_goop"));
    public static final Supplier<EntityType<HarmonicArrow>> HARMONIC_ARROW = RegistryHelper.registerEntityType("harmonic_arrow", () -> harmonicArrowBuilder().build("harmonic_arrow"));

    private static EntityType.Builder<ThrownGoop> thrownGoopBuilder() {
        return EntityType.Builder.<ThrownGoop>of(ThrownGoop::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10);
    }

    private static EntityType.Builder<HarmonicArrow> harmonicArrowBuilder() {
        return EntityType.Builder.<HarmonicArrow>of(HarmonicArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10);
    }
}
