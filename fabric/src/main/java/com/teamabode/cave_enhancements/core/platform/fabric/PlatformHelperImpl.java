package com.teamabode.cave_enhancements.core.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformHelperImpl {

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public static boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
