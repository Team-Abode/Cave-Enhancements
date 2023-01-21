package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class ModSounds {

    public static void init() {}

    // Glow Paste
    public static final Supplier<SoundEvent> ITEM_GLOW_PASTE_PLACE = RegistryHelper.registerSoundEvent("item.glow_paste.place");
    public static final Supplier<SoundEvent> ITEM_AMETHYST_FLUTE_USE = RegistryHelper.registerSoundEvent("item.amethyst_flute.use");

    // Goop Blocks
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_BREAK = RegistryHelper.registerSoundEvent("block.goop_block.break");
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_PLACE = RegistryHelper.registerSoundEvent("block.goop_block.place");
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_HIT = RegistryHelper.registerSoundEvent("block.goop_block.hit");
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_STEP = RegistryHelper.registerSoundEvent("block.goop_block.step");
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_FALL = RegistryHelper.registerSoundEvent("block.goop_block.fall");
    public static final Supplier<SoundEvent> BLOCK_GOOP_BLOCK_SLIDE = RegistryHelper.registerSoundEvent("block.goop_block.slide");
    public static final Supplier<SoundEvent> BLOCK_GOOP_DECORATION_BREAK = RegistryHelper.registerSoundEvent("block.goop_decoration.break");
    public static final Supplier<SoundEvent> BLOCK_GOOP_DECORATION_PLACE = RegistryHelper.registerSoundEvent("block.goop_decoration.place");
    public static final Supplier<SoundEvent> BLOCK_GOOP_DECORATION_HIT = RegistryHelper.registerSoundEvent("block.goop_decoration.hit");
    public static final Supplier<SoundEvent> BLOCK_GOOP_DECORATION_STEP = RegistryHelper.registerSoundEvent("block.goop_decoration.step");
    public static final Supplier<SoundEvent> BLOCK_GOOP_DECORATION_FALL = RegistryHelper.registerSoundEvent("block.goop_decoration.fall");

    // Goop
    public static final Supplier<SoundEvent> ENTITY_GOOP_HURT = RegistryHelper.registerSoundEvent("entity.goop.hurt");
    public static final Supplier<SoundEvent> ENTITY_GOOP_DEATH = RegistryHelper.registerSoundEvent("entity.goop.death");
    public static final Supplier<SoundEvent> ITEM_GOOP_THROW = RegistryHelper.registerSoundEvent("item.goop.throw");

    // Dripstone Tortoise
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_HURT = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.hurt");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_DEATH = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.death");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_STEP = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.step");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_IDLE = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.idle");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_BABY_HURT = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.baby_hurt");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_BABY_DEATH = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.baby_death");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_BREAK_EGG = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.break_egg");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_CRACK_EGG = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.crack_egg");
    public static final Supplier<SoundEvent> ENTITY_DRIPSTONE_TORTOISE_HATCH_EGG = RegistryHelper.registerSoundEvent("entity.dripstone_tortoise.hatch_egg");

    // Cruncher
    public static final Supplier<SoundEvent> ENTITY_CRUNCHER_IDLE = RegistryHelper.registerSoundEvent("entity.cruncher.idle");
    public static final Supplier<SoundEvent> ENTITY_CRUNCHER_HURT = RegistryHelper.registerSoundEvent("entity.cruncher.hurt");
    public static final Supplier<SoundEvent> ENTITY_CRUNCHER_DEATH = RegistryHelper.registerSoundEvent("entity.cruncher.death");
    public static final Supplier<SoundEvent> ENTITY_CRUNCHER_STEP = RegistryHelper.registerSoundEvent("entity.cruncher.step");

    // Misc
    public static final Supplier<SoundEvent> ITEM_BUCKET_FILL_GOOP = RegistryHelper.registerSoundEvent("item.bucket.fill.goop");
    public static final Supplier<SoundEvent> ITEM_BUCKET_EMPTY_GOOP = RegistryHelper.registerSoundEvent("item.bucket.empty.goop");
    public static final Supplier<SoundEvent> BLOCK_ROSE_QUARTZ_CHIMES_CHIME = RegistryHelper.registerSoundEvent("block.rose_quartz_chimes.chime");
    public static final Supplier<SoundEvent> EFFECT_REVERSAL_REVERSE = RegistryHelper.registerSoundEvent("effect.reversal.reverse");

    // Glow Berry Juice
    public static final Supplier<SoundEvent> ITEM_GLOW_BERRY_JUICE_DRINK = RegistryHelper.registerSoundEvent("item.glow_berry_juice.drink");

    // Music
    public static final Supplier<SoundEvent> MUSIC_BIOME_GOOP_CAVES = RegistryHelper.registerSoundEvent("music.biome.goop_caves");
    public static final Supplier<SoundEvent> MUSIC_BIOME_ROSE_QUARTZ_CAVES = RegistryHelper.registerSoundEvent("music.biome.rose_quartz_caves");

}
