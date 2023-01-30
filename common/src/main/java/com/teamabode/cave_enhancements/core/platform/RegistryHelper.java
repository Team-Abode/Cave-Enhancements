package com.teamabode.cave_enhancements.core.platform;

import com.teamabode.cave_enhancements.common.block.DrippingGoopBlock;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class RegistryHelper {

    @ExpectPlatform
    public static <T extends Item>Supplier<T> registerItem(String id, Supplier<T> itemSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String mobName, Supplier<EntityType<T>> entityType, int baseColor, int overlayColor) {
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
    public static Supplier<DrippingGoopBlock> registerDrippingGoop() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Entity>Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entitySupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity>Supplier<BlockEntityType<T>> registerBlockEntityType(String id, Supplier<BlockEntityType<T>> blockEntityTypeSupplier) {
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
    public static <T extends FeatureConfiguration> Supplier<Feature<T>> registerFeature(String id, Supplier<Feature<T>> featureSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<MobEffect> registerEffect(String id, Supplier<MobEffect> mobEffectSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Potion> registerPotion(String id, Supplier<Potion> potionSupplier) {
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
