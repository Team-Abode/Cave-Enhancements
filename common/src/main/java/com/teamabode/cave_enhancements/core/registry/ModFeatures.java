package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.feature.GoopStrandFeature;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Supplier;

public class ModFeatures {

    public static final Supplier<Feature<NoneFeatureConfiguration>> GOOP_STRAND = RegistryHelper.registerFeature("goop_strand", GoopStrandFeature::new);

    public static void init() {

    }
}
