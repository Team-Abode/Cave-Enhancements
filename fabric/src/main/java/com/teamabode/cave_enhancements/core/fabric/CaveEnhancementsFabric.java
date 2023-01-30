package com.teamabode.cave_enhancements.core.fabric;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class CaveEnhancementsFabric implements ModInitializer {

    public void onInitialize() {
        CaveEnhancements.init();
        CaveEnhancements.queuedWork();

        FabricDefaultAttributeRegistry.register(ModEntities.GOOP.get(), Goop.createGoopAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CRUNCHER.get(), Cruncher.createCruncherAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRIPSTONE_TORTOISE.get(), DripstoneTortoise.createDripstoneTortoiseAttributes());

        SpawnRestrictionAccessor.callRegister(ModEntities.GOOP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Goop::checkGoopSpawnRules);
        SpawnRestrictionAccessor.callRegister(ModEntities.CRUNCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);
        SpawnRestrictionAccessor.callRegister(ModEntities.DRIPSTONE_TORTOISE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.MONSTER, ModEntities.CRUNCHER.get(), 10, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), MobCategory.MONSTER, ModEntities.DRIPSTONE_TORTOISE.get(), 50, 1, 1);

        ItemTooltipCallback.EVENT.register(CaveEnhancements::addPotionTooltip);
    }
}
