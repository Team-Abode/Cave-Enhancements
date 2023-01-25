package com.teamabode.cave_enhancements.core.forge;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.core.platform.forge.RegistryHelperImpl;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        RegistryHelperImpl.MOB_EFFECTS.register(eventBus);
        RegistryHelperImpl.POTIONS.register(eventBus);
        RegistryHelperImpl.BANNER_PATTERNS.register(eventBus);

        eventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CaveEnhancements::queuedWork);
    }
}
