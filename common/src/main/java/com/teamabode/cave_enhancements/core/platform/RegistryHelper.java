package com.teamabode.cave_enhancements.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Supplier;

public class RegistryHelper {

    @ExpectPlatform
    public static <T extends Item>Supplier<T> registerItem(String id, Supplier<T> itemSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Block>Supplier<T> registerBlock(String id, Supplier<T> blockSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Block>Supplier<T> registerBlockWithItem(String id, Supplier<T> blockSupplier, CreativeModeTab tab) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Block>Supplier<T> registerDrippingGoop(Supplier<T> blockSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends EntityType<? extends Entity>>Supplier<T> registerEntityType(String id, Supplier<T> entitySupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<BannerPattern> registerBannerPattern(String id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Biome> registerBiome(String id, Supplier<Biome> biomeSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<MobEffect> registerEffect(String id, Supplier<MobEffect> mobEffectSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSoundEvent(String id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<SimpleParticleType> registerParticle(String id) {
        throw new AssertionError();
    }
}
