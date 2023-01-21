package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.block.*;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import com.teamabode.cave_enhancements.core.registry.misc.BlockProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class ModBlocks {

    public static void init() {}

    public static final Supplier<Block> GOOP_BLOCK = RegistryHelper.registerBlockWithItem("goop_block", () -> new Block(BlockProperties.goop(false).destroyTime(0.5F).jumpFactor(0.9F)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> GOOP_TRAP = RegistryHelper.registerBlockWithItem("goop_trap", () -> new GoopTrapBlock(BlockProperties.goop(false).destroyTime(2.0F).speedFactor(0.1F)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> GOOP_SPLAT = RegistryHelper.registerBlock("goop_splat", () -> new SplatBlock(BlockProperties.goop(true), ModItems.GOOP.get()));
    public static final Supplier<Block> DRIPPING_GOOP = RegistryHelper.registerDrippingGoop(() -> new DrippingGoopBlock(BlockProperties.goop(true).lightLevel(state -> 2)));
    public static final Supplier<Block> VOLATILE_GOOP = RegistryHelper.registerBlockWithItem("volatile_goop", () -> new VolatileGoopBlock(BlockProperties.goop(false).destroyTime(2.0F)), CreativeModeTab.TAB_REDSTONE);

    public static final Supplier<Block> ROSE_QUARTZ_BLOCK = RegistryHelper.registerBlockWithItem("rose_quartz_block", () -> new Block(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final Supplier<Block> POLISHED_ROSE_QUARTZ = RegistryHelper.registerBlockWithItem("polished_rose_quartz", () -> new Block(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> POLISHED_ROSE_QUARTZ_SLAB = RegistryHelper.registerBlockWithItem("polished_rose_quartz_slab", () -> new SlabBlock(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> POLISHED_ROSE_QUARTZ_STAIRS = RegistryHelper.registerBlockWithItem("polished_rose_quartz_stairs", () -> new ModStairBlock(ModBlocks.POLISHED_ROSE_QUARTZ.get().defaultBlockState(), BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> POLISHED_ROSE_QUARTZ_WALL = RegistryHelper.registerBlockWithItem("polished_rose_quartz_wall", () -> new WallBlock(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final Supplier<Block> ROSE_QUARTZ_TILES = RegistryHelper.registerBlockWithItem("rose_quartz_tiles", () -> new Block(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> ROSE_QUARTZ_TILE_SLAB = RegistryHelper.registerBlockWithItem("rose_quartz_tile_slab", () -> new SlabBlock(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> ROSE_QUARTZ_TILE_STAIRS = RegistryHelper.registerBlockWithItem("rose_quartz_tile_stairs", () -> new ModStairBlock(ModBlocks.ROSE_QUARTZ_TILES.get().defaultBlockState(), BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final Supplier<Block> ROSE_QUARTZ_TILE_WALL = RegistryHelper.registerBlockWithItem("rose_quartz_tile_wall", () -> new WallBlock(BlockProperties.ROSE_QUARTZ_BLOCKS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final Supplier<Block> JAGGED_ROSE_QUARTZ = RegistryHelper.registerBlockWithItem("jagged_rose_quartz", () -> new JaggedRoseQuartzBlock(BlockProperties.getDefault(Material.STONE).destroyTime(0.8F).requiresCorrectToolForDrops().sound(SoundType.CALCITE).color(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
    public static final Supplier<Block> ROSE_QUARTZ_LAMP = RegistryHelper.registerBlockWithItem("rose_quartz_lamp", () -> new RoseQuartzLampBlock(BlockProperties.ROSE_QUARTZ_LAMP.color(MaterialColor.COLOR_PINK)), CreativeModeTab.TAB_DECORATIONS);
    public static final Supplier<Block> SOUL_ROSE_QUARTZ_LAMP = RegistryHelper.registerBlockWithItem("soul_rose_quartz_lamp", () -> new RoseQuartzLampBlock(BlockProperties.ROSE_QUARTZ_LAMP.color(MaterialColor.COLOR_CYAN)), CreativeModeTab.TAB_DECORATIONS);

    public static final Supplier<Block> GLOW_SPLOTCH = RegistryHelper.registerBlock("glow_splotch", () -> new SplatBlock(BlockProperties.getDefault(Material.CLAY).color(MaterialColor.COLOR_CYAN).sound(SoundType.HONEY_BLOCK).noCollission().noOcclusion().instabreak().lightLevel(state -> 8), ModItems.GLOW_PASTE.get()));

    public static final Supplier<Block> LIGHTNING_ANCHOR = RegistryHelper.registerBlockWithItem("lightning_anchor", () -> new LightningAnchorBlock(BlockProperties.LIGHTNING_ANCHOR), CreativeModeTab.TAB_REDSTONE);
    public static final Supplier<Block> CHARGED_LIGHTNING_ANCHOR = RegistryHelper.registerBlockWithItem("charged_lightning_anchor", () -> new LightningAnchorBlock(BlockProperties.LIGHTNING_ANCHOR), CreativeModeTab.TAB_REDSTONE);
}
