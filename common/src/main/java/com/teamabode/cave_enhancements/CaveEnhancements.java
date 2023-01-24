package com.teamabode.cave_enhancements;

import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import com.teamabode.cave_enhancements.core.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaveEnhancements {
    public static final String MODID = "cave_enhancements";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static void init() {
        ModEntities.init();
        ModBlocks.init();
        ModItems.init();

        ModBlockEntities.init();
        ModEffects.init();
        ModSounds.init();
        ModParticles.init();
        RegistryHelper.registerBannerPattern("goop");
    }

    public static void postInit() {

    }
}
