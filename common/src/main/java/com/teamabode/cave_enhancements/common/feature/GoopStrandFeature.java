package com.teamabode.cave_enhancements.common.feature;

import com.teamabode.cave_enhancements.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GoopStrandFeature extends Feature<NoneFeatureConfiguration> {

    public GoopStrandFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource randomSource = context.random();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos relativePos = pos.relative(direction);

            if (!level.getBlockState(relativePos).isAir()) {
                placeStrand(level, relativePos, UniformInt.of(1, 3).sample(randomSource));
            }

        }
        WeightedListInt weightedSelection = new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                .add(UniformInt.of(4, 6), 4)
                .add(ConstantInt.of(8), 6)
                .add(UniformInt.of(5, 10), 1)
                .build()
        );
        placeStrand(level, pos, weightedSelection.sample(randomSource));

        return true;
    }

    public void placeStrand(LevelAccessor level, BlockPos pos, int length) {
        if (!level.getBlockState(pos.below(length)).isAir()) {
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            level.setBlock(pos.below(i), ModBlocks.GOOP_BLOCK.get().defaultBlockState(), 3);

            if (level.getBlockState(pos.below(i)).isAir()) {
                break;
            }
        }
    }
}
