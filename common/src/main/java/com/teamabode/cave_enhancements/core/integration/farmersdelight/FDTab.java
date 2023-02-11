package com.teamabode.cave_enhancements.core.integration.farmersdelight;

import com.teamabode.cave_enhancements.core.platform.PlatformHelper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;

public class FDTab {

    @Nullable
    public static CreativeModeTab getFDTab() {
        if (!PlatformHelper.isModLoaded("farmersdelight")) {
            return null;
        }
        return getPlatformTab();
    }

    @ExpectPlatform
    public static CreativeModeTab getPlatformTab() {
        throw new AssertionError();
    }
}
