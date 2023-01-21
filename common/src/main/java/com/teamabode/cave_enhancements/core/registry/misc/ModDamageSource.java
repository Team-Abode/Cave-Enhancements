package com.teamabode.cave_enhancements.core.registry.misc;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSource extends DamageSource {

    public static final DamageSource GOOP_DRIP = new ModDamageSource("goop_drip").setMagic();
    public static final DamageSource VISCOUS = new ModDamageSource("viscous").setMagic();

    protected ModDamageSource(String string) {
        super(string);
    }
}
