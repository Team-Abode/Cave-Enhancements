package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {
    // Banner Pattern Tags
    public static final TagKey<BannerPattern> PATTERN_ITEM_GOOP = TagKey.create(Registry.BANNER_PATTERN_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "goop_pattern_item"));

    // Entity Type Tags
    public static final TagKey<EntityType<?>> GOOP_TRAP_IMMUNE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "goop_trap_immune"));
    public static final TagKey<EntityType<?>> AMETHYST_FLUTE_IMMUNE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "amethyst_flute_immune"));

    public static final TagKey<Block> PIKE_DESTROYABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "pike_destroyables"));

    public static final TagKey<Block> CRUNCHER_CONSUMABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "cruncher_consumables"));
    public static final TagKey<Block> CRUNCHER_SEARCHABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "cruncher_searchables"));
    public static final TagKey<Block> CRUNCHERS_SPAWNABLE_ON = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, "crunchers_spawnable_on"));
}
