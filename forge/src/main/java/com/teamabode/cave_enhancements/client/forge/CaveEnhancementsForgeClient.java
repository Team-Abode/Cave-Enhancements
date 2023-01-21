package com.teamabode.cave_enhancements.client.forge;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.particle.*;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaveEnhancements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveEnhancementsForgeClient {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.THROWN_GOOP.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.register(ModParticles.SMALL_GOOP_DRIP.get(), SmallGoopDripParticle.Factory::new);
        event.register(ModParticles.GOOP_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.register(ModParticles.CHARGE.get(), StagnantParticle.Factory::new);
        event.register(ModParticles.SHOCKWAVE.get(), ShockwaveParticle.Factory::new);
        event.register(ModParticles.HARMONIC_WAVE.get(), HarmonicWaveParticle.Factory::new);
        event.register(ModParticles.SHIMMER.get(), ShimmerParticle.Factory::new);
        event.register(ModParticles.STAGNANT_SHIMMER.get(), StagnantParticle.Factory::new);
        event.register(ModParticles.ROSE_CHIME.get(), RoseChimeParticle.Factory::new);
        event.register(ModParticles.SOOTHING_NOTE.get(), SoothingNoteParticle.Factory::new);
    }
}
