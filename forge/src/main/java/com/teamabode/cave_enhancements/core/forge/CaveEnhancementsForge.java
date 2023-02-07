package com.teamabode.cave_enhancements.core.forge;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.CaveEnhancementsClient;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.core.platform.forge.RegistryHelperImpl;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CaveEnhancements.MODID)
public class CaveEnhancementsForge {

    public CaveEnhancementsForge() {
        CaveEnhancements.init();

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryHelperImpl.SOUND_EVENTS.register(eventBus);
        RegistryHelperImpl.PARTICLE_TYPES.register(eventBus);
        RegistryHelperImpl.ENTITY_TYPES.register(eventBus);
        RegistryHelperImpl.ITEMS.register(eventBus);
        RegistryHelperImpl.BLOCKS.register(eventBus);
        RegistryHelperImpl.BLOCK_ENTITY_TYPES.register(eventBus);
        RegistryHelperImpl.BIOMES.register(eventBus);
        RegistryHelperImpl.FEATURES.register(eventBus);
        RegistryHelperImpl.MOB_EFFECTS.register(eventBus);
        RegistryHelperImpl.POTIONS.register(eventBus);
        RegistryHelperImpl.BANNER_PATTERNS.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerEntityAttributes);

        if (FMLLoader.getDist() == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(this::addTooltipEvent);
        }


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CaveEnhancements.queuedWork();
            SpawnPlacements.register(ModEntities.GOOP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Goop::checkGoopSpawnRules);
            SpawnPlacements.register(ModEntities.CRUNCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Cruncher::checkCruncherSpawnRules);
            SpawnPlacements.register(ModEntities.DRIPSTONE_TORTOISE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, DripstoneTortoise::checkDripstoneTortoiseSpawnRules);
        });
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GOOP.get(), Goop.createGoopAttributes().build());
        event.put(ModEntities.CRUNCHER.get(), Cruncher.createCruncherAttributes().build());
        event.put(ModEntities.DRIPSTONE_TORTOISE.get(), DripstoneTortoise.createDripstoneTortoiseAttributes().build());
    }

    private void addTooltipEvent(ItemTooltipEvent event) {
        CaveEnhancementsClient.addPotionTooltip(event.getItemStack(), event.getFlags(), event.getToolTip());
    }
}
