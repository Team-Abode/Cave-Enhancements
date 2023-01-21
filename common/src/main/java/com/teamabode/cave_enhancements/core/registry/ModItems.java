package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.item.*;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import com.teamabode.cave_enhancements.core.registry.misc.ItemProperties;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {

    public static final Supplier<Item> AMETHYST_FLUTE = RegistryHelper.registerItem("amethyst_flute", () -> new AmethystFluteItem(ItemProperties.tool(64)));
    public static final Supplier<Item> GOOP = RegistryHelper.registerItem("goop", () -> new GoopItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> ROSE_QUARTZ = RegistryHelper.registerItem("rose_quartz", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final Supplier<Item> GLOW_BERRY_JUICE = RegistryHelper.registerItem("glow_berry_juice", () -> new GlowBerryJuiceItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final Supplier<Item> GLOW_PASTE = RegistryHelper.registerItem("glow_paste", () -> new GlowPasteItem(ItemProperties.tool(32)));
    public static final Supplier<Item> GOOP_BANNER_PATTERN = RegistryHelper.registerItem("goop_banner_pattern", () -> new BannerPatternItem(ModTags.PATTERN_ITEM_GOOP, new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final Supplier<Item> HARMONIC_ARROW = RegistryHelper.registerItem("harmonic_arrow", () -> new HarmonicArrowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static void init() {

    }
}
