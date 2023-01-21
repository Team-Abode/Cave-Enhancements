package com.teamabode.cave_enhancements.core.registry.misc;

import com.teamabode.cave_enhancements.core.registry.ModSounds;
import net.minecraft.world.level.block.SoundType;

public class ModSoundType {

    public static final SoundType GOOP_BLOCK = new SoundType(
            1.0F,
            1.0F,
            ModSounds.BLOCK_GOOP_BLOCK_BREAK.get(),
            ModSounds.BLOCK_GOOP_BLOCK_STEP.get(),
            ModSounds.BLOCK_GOOP_BLOCK_PLACE.get(),
            ModSounds.BLOCK_GOOP_BLOCK_HIT.get(),
            ModSounds.BLOCK_GOOP_BLOCK_FALL.get()
    );

    public static final SoundType GOOP_DECORATION = new SoundType(
            1.0F,
            1.0F,
            ModSounds.BLOCK_GOOP_DECORATION_BREAK.get(),
            ModSounds.BLOCK_GOOP_DECORATION_STEP.get(),
            ModSounds.BLOCK_GOOP_DECORATION_PLACE.get(),
            ModSounds.BLOCK_GOOP_DECORATION_HIT.get(),
            ModSounds.BLOCK_GOOP_DECORATION_FALL.get()
    );
}
