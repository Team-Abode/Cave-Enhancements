package com.teamabode.cave_enhancements.common.item;

import com.teamabode.cave_enhancements.core.registry.ModParticles;
import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AmethystFluteItem extends Item {

    public AmethystFluteItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        int cooldown = 0;
        ItemStack itemStack = player.getItemInHand(usedHand);
        List<Allay> allays = level.getEntitiesOfClass(Allay.class, player.getBoundingBox().inflate(64.0D), allay -> {
            Optional<UUID> optional = allay.getBrain().getMemory(MemoryModuleType.LIKED_PLAYER);
            return optional.isPresent() && player.getUUID() == optional.get();
        });
        if (allays.isEmpty()) {
            cooldown=10;
        }
        else {
            for (Allay allay : allays) {
                allay.teleportTo(player.getX(), player.getY(), player.getZ());
                if (level instanceof ServerLevel server) {
                    server.sendParticles(ModParticles.SOOTHING_NOTE.get(), allay.getX() + allay.getRandom().nextGaussian() * 0.5, allay.getY() + 1.5D, allay.getZ() + allay.getRandom().nextGaussian() * 0.5, 1, 0.0D, 0.25D, 0.0D, 0.0D);
                }
                cooldown+=20;
            }
        }
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.USING_ITEM.trigger(serverPlayer, itemStack);
        }
        level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.blockPosition());
        level.playSound(null, player.blockPosition(), ModSounds.ITEM_AMETHYST_FLUTE_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        player.getCooldowns().addCooldown(this, Math.min(cooldown, 100));
        return InteractionResultHolder.success(itemStack);
    }
}
