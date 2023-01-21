package com.teamabode.cave_enhancements.core.fabric;

import com.teamabode.cave_enhancements.CaveEnhancements;
import net.fabricmc.api.ModInitializer;

public class CaveEnhancementsFabric implements ModInitializer {

    public void onInitialize() {
        CaveEnhancements.init();
    }
}
