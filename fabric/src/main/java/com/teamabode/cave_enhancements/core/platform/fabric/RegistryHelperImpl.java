package com.teamabode.cave_enhancements.core.platform.fabric;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.block.DrippingGoopBlock;
import com.teamabode.cave_enhancements.common.item.DrippingGoopItem;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import com.teamabode.cave_enhancements.core.registry.misc.BlockProperties;
import com.teamabode.cave_enhancements.core.registry.misc.ItemProperties;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class RegistryHelperImpl {

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> itemSupplier) {
        var item = Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, id), itemSupplier.get());
        return () -> item;
    }

    public static Supplier<SpawnEggItem> registerSpawnEgg(String mobName, EntityType<? extends Mob> entityType, int baseColor, int overlayColor) {
        var item = Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, mobName + "_spawn_egg"), new SpawnEggItem(entityType, baseColor, overlayColor, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return () -> item;
    }

    public static <T extends Block>Supplier<T> registerBlock(String id, Supplier<T> blockSupplier) {
        var block = Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, id), blockSupplier.get());
        return () -> block;
    }

    public static <T extends Block>Supplier<T> registerBlockWithItem(String id, Supplier<T> blockSupplier, CreativeModeTab tab) {
        var block = Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, id), blockSupplier.get());
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, id), new BlockItem(block, new Item.Properties().tab(tab)));
        return () -> block;
    }

    public static Supplier<DrippingGoopBlock> registerDrippingGoop() {
        var block = Registry.register(Registry.BLOCK, new ResourceLocation(CaveEnhancements.MODID, "dripping_goop"), new DrippingGoopBlock(BlockProperties.goop(true).lightLevel(state -> 2)));
        Registry.register(Registry.ITEM, new ResourceLocation(CaveEnhancements.MODID, "dripping_goop"), new DrippingGoopItem(block, ItemProperties.DEFAULT.tab(CreativeModeTab.TAB_DECORATIONS)));
        return () -> block;
    }

    public static <T extends EntityType<? extends Entity>>Supplier<T> registerEntityType(String id, Supplier<T> entitySupplier) {
        var entityType = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, id), entitySupplier.get());
        return () -> entityType;
    }

    public static <T extends BlockEntityType<? extends BlockEntity>>Supplier<T> registerBlockEntityType(String id, Supplier<T> blockEntityTypeSupplier) {
        var blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(CaveEnhancements.MODID, id), blockEntityTypeSupplier.get());
        return () -> blockEntityType;
    }

    public static Supplier<BannerPattern> registerBannerPattern(String id) {
        var bannerPattern = Registry.register(Registry.BANNER_PATTERN, new ResourceLocation(CaveEnhancements.MODID, id), new BannerPattern(id));
        return () -> bannerPattern;
    }

    public static Supplier<Biome> registerBiome(String id, Supplier<Biome> biomeSupplier) {
        var biome = Registry.register(BuiltinRegistries.BIOME, new ResourceLocation(CaveEnhancements.MODID, id), biomeSupplier.get());
        return () -> biome;
    }

    public static Supplier<MobEffect> registerEffect(String id, Supplier<MobEffect> mobEffectSupplier) {
        var mobEffect = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(CaveEnhancements.MODID, id), mobEffectSupplier.get());
        return () -> mobEffect;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id) {
        var location = new ResourceLocation(CaveEnhancements.MODID, id);
        var soundEvent = Registry.register(Registry.SOUND_EVENT, location, new SoundEvent(location));
        return () -> soundEvent;
    }

    public static Supplier<SimpleParticleType> registerParticle(String id) {
        var particleType = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(CaveEnhancements.MODID, id), FabricParticleTypes.simple());
        return () -> particleType;
    }
}
