package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.common.block.entity.RoseQuartzChimesBlockEntity;
import com.teamabode.cave_enhancements.common.block.entity.SpectacleCandleBlockEntity;
import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static void init() {

    }

    public static final Supplier<BlockEntityType<SpectacleCandleBlockEntity>> SPECTACLE_CANDLE = RegistryHelper.registerBlockEntityType("spectacle_candle", () -> BlockEntityType.Builder.of(SpectacleCandleBlockEntity::new, ModBlocks.SPECTACLE_CANDLE.get(), ModBlocks.SPECTACLE_CANDLE_CAKE.get()).build(null));
    public static final Supplier<BlockEntityType<RoseQuartzChimesBlockEntity>> ROSE_QUARTZ_CHIMES = RegistryHelper.registerBlockEntityType("rose_quartz_chimes", () -> BlockEntityType.Builder.of(RoseQuartzChimesBlockEntity::new,  ModBlocks.ROSE_QUARTZ_CHIMES.get()).build(null));

}
