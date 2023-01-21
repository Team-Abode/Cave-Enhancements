package com.teamabode.cave_enhancements.core.registry.misc;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlockProperties {

    public static BlockBehaviour.Properties getDefault(Material material) {
        return BlockBehaviour.Properties.of(material);
    }

    public static BlockBehaviour.Properties goop(boolean isDecoration) {
        BlockBehaviour.Properties properties = getDefault(Material.CLAY).color(MaterialColor.SAND).sound(!isDecoration ? ModSoundType.GOOP_BLOCK : ModSoundType.GOOP_DECORATION);
        if (isDecoration) properties.noCollission().noCollission().instabreak();
        return properties;
    }

    public static final BlockBehaviour.Properties ROSE_QUARTZ_BLOCKS = BlockBehaviour.Properties.of(Material.STONE)
            .strength(1.0F, 10.0F)
            .requiresCorrectToolForDrops()
            .color(MaterialColor.COLOR_PINK)
            .sound(SoundType.CALCITE);

    public static final BlockBehaviour.Properties LIGHTNING_ANCHOR = BlockBehaviour.Properties.of(Material.METAL)
            .strength(4.0F, 100.0F)
            .requiresCorrectToolForDrops()
            .color(MaterialColor.COLOR_ORANGE)
            .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties ROSE_QUARTZ_LAMP = BlockBehaviour.Properties.of(Material.STONE)
            .strength(1.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.LANTERN)
            .lightLevel(value -> 15);
}
