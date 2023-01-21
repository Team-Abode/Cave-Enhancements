package com.teamabode.cave_enhancements.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHelper {

    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevEnvironment() {
        throw new AssertionError();
    }
}
