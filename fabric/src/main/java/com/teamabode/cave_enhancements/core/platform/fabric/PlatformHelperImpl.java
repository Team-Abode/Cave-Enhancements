package com.teamabode.cave_enhancements.core.platform.fabric;

import com.teamabode.cave_enhancements.core.platform.PlatformHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PlatformHelperImpl {

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public static boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(PlatformHelper.BlockEntitySupplier<T> blockEntitySupplier, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(blockEntitySupplier::create, blocks).build();
    }
}
