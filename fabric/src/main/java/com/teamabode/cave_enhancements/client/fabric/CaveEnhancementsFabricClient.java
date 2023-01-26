package com.teamabode.cave_enhancements.client.fabric;

import com.teamabode.cave_enhancements.client.model.CruncherModel;
import com.teamabode.cave_enhancements.client.model.GoopModel;
import com.teamabode.cave_enhancements.client.particle.*;
import com.teamabode.cave_enhancements.client.renderer.block.RoseQuartzChimesRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.CruncherRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.GoopRenderer;
import com.teamabode.cave_enhancements.client.renderer.entity.HarmonicArrowRenderer;
import com.teamabode.cave_enhancements.core.registry.ModBlockEntities;
import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import com.teamabode.cave_enhancements.core.registry.ModEntities;
import com.teamabode.cave_enhancements.core.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class CaveEnhancementsFabricClient implements ClientModInitializer {

    public void onInitializeClient() {
        registerParticleFactories();
        registerBlockRenderLayers();
        registerRenderers();
        registerModelLayer();
    }

    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_GOOP_DRIP.get(), SmallGoopDripParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GOOP_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.CHARGE.get(), StagnantParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHOCKWAVE.get(), ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.HARMONIC_WAVE.get(), HarmonicWaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHIMMER.get(), ShimmerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.STAGNANT_SHIMMER.get(), StagnantParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROSE_CHIME.get(), RoseChimeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOTHING_NOTE.get(), SoothingNoteParticle.Factory::new);
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.GOOP.get(), GoopRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRUNCHER.get(), CruncherRenderer::new);
        EntityRendererRegistry.register(ModEntities.GOOP_DRIP.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.THROWN_GOOP.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.HARMONIC_ARROW.get(), HarmonicArrowRenderer::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.ROSE_QUARTZ_CHIMES.get(), RoseQuartzChimesRenderer::new);
    }

    public static void registerModelLayer() {
        EntityModelLayerRegistry.registerModelLayer(GoopModel.ENTITY_MODEL_LAYER, GoopModel::createLayer);
        EntityModelLayerRegistry.registerModelLayer(CruncherModel.LAYER_LOCATION, CruncherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RoseQuartzChimesRenderer.MODEL_LAYER, RoseQuartzChimesRenderer::createLayer);
    }

    public static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GOOP_SPLAT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIPPING_GOOP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLOW_SPLOTCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JAGGED_ROSE_QUARTZ.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROSE_QUARTZ_CHIMES.get(), RenderType.cutout());
    }
}
