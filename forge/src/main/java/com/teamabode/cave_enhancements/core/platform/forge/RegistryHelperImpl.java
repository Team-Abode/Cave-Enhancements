package com.teamabode.cave_enhancements.core.platform.forge;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.block.DrippingGoopBlock;
import com.teamabode.cave_enhancements.common.item.DrippingGoopItem;
import com.teamabode.cave_enhancements.core.registry.misc.BlockProperties;
import com.teamabode.cave_enhancements.core.registry.misc.ItemProperties;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CaveEnhancements.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CaveEnhancements.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CaveEnhancements.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CaveEnhancements.MODID);
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, CaveEnhancements.MODID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, CaveEnhancements.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CaveEnhancements.MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CaveEnhancements.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, CaveEnhancements.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CaveEnhancements.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CaveEnhancements.MODID);

    public static <T extends Entity>Supplier<EntityType<T>> registerEntityType(String id, Supplier<EntityType<T>> entitySupplier) {
        return ENTITY_TYPES.register(id, entitySupplier);
    }

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> itemSupplier) {
        return ITEMS.register(id, itemSupplier);
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String mobName, Supplier<EntityType<T>> entityType, int baseColor, int overlayColor) {
        return ITEMS.register(mobName + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, baseColor, overlayColor, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }

    public static <T extends Block>Supplier<T> registerBlock(String id, Supplier<T> blockSupplier) {
        return BLOCKS.register(id, blockSupplier);
    }

    public static <T extends Block>Supplier<T> registerBlockWithItem(String id, Supplier<T> blockSupplier, CreativeModeTab tab) {
        var block = BLOCKS.register(id, blockSupplier);
        ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        return block;
    }

    public static Supplier<DrippingGoopBlock> registerDrippingGoop() {
        var block = BLOCKS.register("dripping_goop", () -> new DrippingGoopBlock(BlockProperties.goop(true).lightLevel(state -> 2)));
        ITEMS.register("dripping_goop", () -> new DrippingGoopItem(block.get(), ItemProperties.DEFAULT.tab(CreativeModeTab.TAB_DECORATIONS)));
        return block;
    }

    public static <T extends BlockEntity>Supplier<BlockEntityType<T>> registerBlockEntityType(String id, Supplier<BlockEntityType<T>> blockEntityTypeSupplier) {
        return BLOCK_ENTITY_TYPES.register(id, blockEntityTypeSupplier);
    }

    public static Supplier<BannerPattern> registerBannerPattern(String id) {
        return BANNER_PATTERNS.register(id, () -> new BannerPattern(id));
    }

    public static Supplier<Biome> registerBiome(String id, Supplier<Biome> biomeSupplier) {
        return BIOMES.register(id, biomeSupplier);
    }

    public static <T extends FeatureConfiguration> Supplier<Feature<T>> registerFeature(String id, Supplier<Feature<T>> featureSupplier) {
        return FEATURES.register(id, featureSupplier);
    }

    public static Supplier<MobEffect> registerEffect(String id, Supplier<MobEffect> mobEffectSupplier) {
        return MOB_EFFECTS.register(id, mobEffectSupplier);
    }

    public static Supplier<Potion> registerPotion(String id, Supplier<Potion> potionSupplier) {
        return POTIONS.register(id, potionSupplier);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String id) {
        return SOUND_EVENTS.register(id, () -> new SoundEvent(new ResourceLocation(CaveEnhancements.MODID, id)));
    }

    public static Supplier<SimpleParticleType> registerParticle(String id) {
        return PARTICLE_TYPES.register(id, () -> new SimpleParticleType(false));
    }

    public static <T extends CriterionTrigger<?>> T registerCriteriaTrigger(T criteriaTrigger) {
        return CriteriaTriggers.register(criteriaTrigger);
    }
}
