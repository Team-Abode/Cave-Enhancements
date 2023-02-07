package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.item.*;
import com.teamabode.cave_enhancements.core.integration.farmersdelight.ChocolatePuddingItem;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import com.teamabode.cave_enhancements.core.registry.misc.ItemProperties;
import com.teamabode.cave_enhancements.core.registry.misc.ModFoods;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

public class ModItems {

    public static final Supplier<Item> AMETHYST_FLUTE = RegistryHelper.registerItem("amethyst_flute", () -> new AmethystFluteItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));
    public static final Supplier<Item> GOOP = RegistryHelper.registerItem("goop", () -> new GoopItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> ROSE_QUARTZ = RegistryHelper.registerItem("rose_quartz", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> GLOW_BERRY_JUICE = RegistryHelper.registerItem("glow_berry_juice", () -> new GlowBerryJuiceItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).stacksTo(16).food(ModFoods.GLOW_BERRY_JUICE)));
    public static final Supplier<Item> GLOW_PASTE = RegistryHelper.registerItem("glow_paste", () -> new GlowPasteItem(ItemProperties.tool(32)));
    public static final Supplier<Item> GOOP_BANNER_PATTERN = RegistryHelper.registerItem("goop_banner_pattern", () -> new BannerPatternItem(ModTags.PATTERN_ITEM_GOOP, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> GOOP_BUCKET_ITEM = RegistryHelper.registerItem("goop_bucket", () -> new GoopBucketItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static final Supplier<Item> HARMONIC_ARROW = RegistryHelper.registerItem("harmonic_arrow", () -> new HarmonicArrowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final Supplier<Item> GOOP_DRIP = RegistryHelper.registerItem("big_goop_drip", () -> new Item(new Item.Properties()));

    public static final Supplier<SpawnEggItem> GOOP_SPAWN_EGG = RegistryHelper.registerSpawnEgg("goop", ModEntities.GOOP, 13946012, 11637089);
    public static final Supplier<SpawnEggItem> CRUNCHER_SPAWN_EGG = RegistryHelper.registerSpawnEgg("cruncher", ModEntities.CRUNCHER, 11127234, 5757312);
    public static final Supplier<SpawnEggItem> DRIPSTONE_TORTOISE_SPAWN_EGG = RegistryHelper.registerSpawnEgg("dripstone_tortoise", ModEntities.DRIPSTONE_TORTOISE, 8156236, 6967114);

    public static void init() {

    }
}
