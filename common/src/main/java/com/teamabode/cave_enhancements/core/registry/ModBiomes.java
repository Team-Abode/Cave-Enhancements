package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {

    public static final ResourceKey<Biome> GOOP_CAVES = createKey("goop_caves");
    public static final ResourceKey<Biome> ROSE_QUARTZ_CAVES = createKey("rose_quartz_caves");

    public static void init() {
        RegistryHelper.registerBiome("goop_caves", OverworldBiomes::lushCaves);
        RegistryHelper.registerBiome("rose_quartz_caves", OverworldBiomes::lushCaves);
    }

    private static ResourceKey<Biome> createKey(String id) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CaveEnhancements.MODID, id));
    }
}
