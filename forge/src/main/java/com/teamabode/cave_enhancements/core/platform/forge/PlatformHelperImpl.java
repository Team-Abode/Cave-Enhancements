package com.teamabode.cave_enhancements.core.platform.forge;

import com.teamabode.cave_enhancements.core.platform.PlatformHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class PlatformHelperImpl {

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static boolean isDevEnvironment() {
        return FMLLoader.isProduction();
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(PlatformHelper.BlockEntitySupplier<T> blockEntitySupplier, Block... blocks) {
        return BlockEntityType.Builder.of(blockEntitySupplier::create, blocks).build(null);
    }
}
