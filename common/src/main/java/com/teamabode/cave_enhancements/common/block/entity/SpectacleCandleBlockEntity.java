package com.teamabode.cave_enhancements.common.block.entity;

import com.teamabode.cave_enhancements.core.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SpectacleCandleBlockEntity extends BlockEntity {
    public SpectacleCandleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPECTACLE_CANDLE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state) {
        if (!state.getValue(BlockStateProperties.LIT)) return;

        int range = 2;
        if (state.hasProperty(BlockStateProperties.CANDLES)) {
            range = state.getValue(BlockStateProperties.CANDLES) * 2;
        }
        if (level.getGameTime() % 40L == 0) {
            AABB box = new AABB(pos).inflate(range);
            List<Player> list = level.getEntitiesOfClass(Player.class, box);
            for (Player player : list) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));
            }
        }
    }
}
