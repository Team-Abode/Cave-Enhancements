package com.teamabode.cave_enhancements.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class RoseChimeParticle extends SimpleAnimatedParticle {

    RoseChimeParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.xd = 0.1 * (random.nextIntBetweenInclusive(0, 2) * 2 - 1);
        this.yd = 0.1 * (random.nextIntBetweenInclusive(0, 2) * 2 - 1);
        this.zd = 0.1 * (random.nextIntBetweenInclusive(0, 2) * 2 - 1);
        this.quadSize = 0.2F;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.lifetime = 8;
        this.setSpriteFromAge(spriteProvider);
    }

    public void tick() {
        super.tick();

        this.xd /= 3;
        this.yd /= 3;
        this.zd /= 3;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }



        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {

            int velocity = clientWorld.random.nextIntBetweenInclusive(-1, 1);


            return new RoseChimeParticle(clientWorld, d + (velocity * 0.5F), e + (velocity * 0.5F), f + (velocity * 0.5F), 0.0D, 0.0D, 0.0D, this.spriteProvider);
        }
    }
}
