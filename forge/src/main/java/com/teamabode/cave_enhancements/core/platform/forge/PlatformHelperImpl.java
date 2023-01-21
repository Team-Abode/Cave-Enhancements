package com.teamabode.cave_enhancements.core.platform.forge;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class PlatformHelperImpl {

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static boolean isDevEnvironment() {
        return FMLLoader.isProduction();
    }
}
