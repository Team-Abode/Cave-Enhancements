package com.teamabode.cave_enhancements.client.forge;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.CruncherModel;
import com.teamabode.cave_enhancements.client.model.GoopModel;
import com.teamabode.cave_enhancements.client.particle.*;
import com.teamabode.cave_enhancements.client.renderer.block.RoseQuartzChimesRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.CruncherRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.GoopRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.HarmonicArrowRenderer;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import com.teamabode.cave_enhancements.core.registry.ModBlockEntities;
import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CaveEnhancements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveEnhancementsForgeClient {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOOP_SPLAT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIPPING_GOOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GLOW_SPLOTCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JAGGED_ROSE_QUARTZ.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROSE_QUARTZ_CHIMES.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GOOP.get(), GoopRenderer::new);
        event.registerEntityRenderer(ModEntities.CRUNCHER.get(), CruncherRenderer::new);
        event.registerEntityRenderer(ModEntities.HARMONIC_ARROW.get(), HarmonicArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_GOOP.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.GOOP_DRIP.get(), ThrownItemRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.ROSE_QUARTZ_CHIMES.get(), RoseQuartzChimesRenderer::new);
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GoopModel.ENTITY_MODEL_LAYER, GoopModel::createLayer);
        event.registerLayerDefinition(CruncherModel.LAYER_LOCATION, CruncherModel::createBodyLayer);

        event.registerLayerDefinition(RoseQuartzChimesRenderer.MODEL_LAYER, RoseQuartzChimesRenderer::createLayer);
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
