package com.teamabode.cave_enhancements.core.registry;

import com.teamabode.cave_enhancements.core.platform.RegistryHelper;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticles {

    public static void init() {

    }

    public static final Supplier<SimpleParticleType> SMALL_GOOP_DRIP = RegistryHelper.registerParticle("small_goop_drip");
    public static final Supplier<SimpleParticleType> GOOP_EXPLOSION = RegistryHelper.registerParticle("goop_explosion");
    public static final Supplier<SimpleParticleType> CHARGE = RegistryHelper.registerParticle("charge");
    public static final Supplier<SimpleParticleType> SHOCKWAVE = RegistryHelper.registerParticle("shockwave");
    public static final Supplier<SimpleParticleType> HARMONIC_WAVE = RegistryHelper.registerParticle("harmonic_wave");
    public static final Supplier<SimpleParticleType> SHIMMER = RegistryHelper.registerParticle("shimmer");
    public static final Supplier<SimpleParticleType> STAGNANT_SHIMMER = RegistryHelper.registerParticle("stagnant_shimmer");
    public static final Supplier<SimpleParticleType> ROSE_CHIME = RegistryHelper.registerParticle("rose_chime");
    public static final Supplier<SimpleParticleType> SOOTHING_NOTE = RegistryHelper.registerParticle("soothing_note");
}
