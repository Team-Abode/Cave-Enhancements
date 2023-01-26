package com.teamabode.cave_enhancements.core.fabric;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class CaveEnhancementsFabric implements ModInitializer {

    public void onInitialize() {
        CaveEnhancements.init();
        CaveEnhancements.queuedWork();

        FabricDefaultAttributeRegistry.register(ModEntities.GOOP.get(), Goop.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CRUNCHER.get(), Cruncher.createCruncherAttributes());
    }
}
