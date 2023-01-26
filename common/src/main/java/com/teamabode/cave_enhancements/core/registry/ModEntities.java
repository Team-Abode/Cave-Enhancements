package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.entity.HarmonicArrow;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstonePike;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.common.entity.goop.GoopDrip;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.common.entity.goop.ThrownGoop;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ModEntities {

    public static final Supplier<EntityType<Goop>> GOOP = RegistryHelper.registerEntityType("goop", () ->
            EntityType.Builder.of(Goop::new, MobCategory.MONSTER)
                    .sized(0.7F, 1.0F)
                    .clientTrackingRange(10)
                    .build(dataFixer("goop"))
    );

    public static final Supplier<EntityType<Cruncher>> CRUNCHER = RegistryHelper.registerEntityType("cruncher", () ->
            EntityType.Builder.of(Cruncher::new, MobCategory.CREATURE)
                    .sized(0.8F, 0.8F)
                    .clientTrackingRange(10)
                    .build(dataFixer("cruncher")));

    public static final Supplier<EntityType<DripstoneTortoise>> DRIPSTONE_TORTOISE = RegistryHelper.registerEntityType("dripstone_tortoise", () ->
            EntityType.Builder.of(DripstoneTortoise::new, MobCategory.CREATURE)
                    .sized(1.3F, 0.8F)
                    .clientTrackingRange(10)
                    .build(dataFixer("dripstone_tortoise"))
    );

    public static final Supplier<EntityType<ThrownGoop>> THROWN_GOOP = RegistryHelper.registerEntityType("thrown_goop", () ->
            EntityType.Builder.<ThrownGoop>of(ThrownGoop::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(dataFixer("thrown_goop"))
    );

    public static final Supplier<EntityType<GoopDrip>> GOOP_DRIP = RegistryHelper.registerEntityType("goop_drip", () ->
            EntityType.Builder.<GoopDrip>of(GoopDrip::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(dataFixer("goop_drip"))
    );

    public static final Supplier<EntityType<DripstonePike>> DRIPSTONE_PIKE = RegistryHelper.registerEntityType("dripstone_pike", () ->
            EntityType.Builder.<DripstonePike>of(DripstonePike::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(4)
                    .clientTrackingRange(10)
                    .build(dataFixer("dripstone_pike"))
    );

    public static final Supplier<EntityType<HarmonicArrow>> HARMONIC_ARROW = RegistryHelper.registerEntityType("harmonic_arrow", () ->
            EntityType.Builder.<HarmonicArrow>of(HarmonicArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(dataFixer("thrown_goop"))

    );

    public static void init() {

    }

    private static String dataFixer(String mobName) {
        return CaveEnhancements.MODID + ":" + mobName;
    }

}
