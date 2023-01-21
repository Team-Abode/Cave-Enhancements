package com.teamabode.cave_enhancements.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class ShockwaveParticle extends Particle {
    protected TextureAtlasSprite sprite;
    protected float scale;

    ShockwaveParticle(ClientLevel world, double x, double y, double z, double d) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.scale = 1;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.lifetime = 5;
        this.setColor(1, 1, 1);
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 cameraPos = camera.getPosition();

        float x = (float)(Mth.lerp(tickDelta, this.xo, this.x) - cameraPos.x());
        float y = (float)(Mth.lerp(tickDelta, this.yo, this.y) - cameraPos.y());
        float z = (float)(Mth.lerp(tickDelta, this.zo, this.z) - cameraPos.z());

        float size = (this.age + tickDelta) / 20F * 25F;

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();

        //Makes it fully bright
        int light = 15728640;

        float xVO = 0.5f;
        float yVO = 1F/16F;

        addBand(vertexConsumer, xVO, yVO, size, x, y, z - 0.5f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, size, x, y, z + 0.5f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, size, x - 0.5f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, size, x + 0.5f * size, y, z, minU, maxU, minV, maxV, light, 90f);

        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x, y, z - 0.75f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x, y, z + 0.75f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x - 0.75f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, 1.5f * size, x + 0.75f * size, y, z, minU, maxU, minV, maxV, light, 90f);

        addBand(vertexConsumer, xVO, yVO, 2f * size, x, y, z - 1f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x, y, z + 1f * size, minU, maxU, minV, maxV, light, 0f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x - 1f * size, y, z, minU, maxU, minV, maxV, light, 90f);
        addBand(vertexConsumer, xVO, yVO, 2f * size, x + 1f * size, y, z, minU, maxU, minV, maxV, light, 90f);
    }

    public void addBand(VertexConsumer vertexConsumer, float xVO, float yVO, float size, float x, float y, float z, float minU, float maxU, float minV, float maxV, int light, float rotation){
        Vector3f[] vertices = new Vector3f[]{new Vector3f(-xVO * size, -yVO, 0.0F), new Vector3f(-xVO * size, yVO, 0.0F), new Vector3f(xVO * size, yVO, 0.0F), new Vector3f(xVO * size, -yVO, 0.0F)};

        Quaternion quaternion = Quaternion.fromXYZDegrees(new Vector3f(0, rotation, 0));

        for(int i = 0; i < 4; ++i) {
            Vector3f vec3f = vertices[i];
            vec3f.transform(quaternion);
            vec3f.add(x, y, z);
        }

        float UE = maxU - minU;
        maxU = 15F / 16F * UE + minU;
        minU += 1F / 16F * UE;

        float VE = maxV - minV;
        maxV =  9F / 16F * VE + minV;
        minV += 7F / 16F * VE;

        vertexConsumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();

        vertices = new Vector3f[]{new Vector3f(xVO * size, -yVO, 0.0F), new Vector3f(xVO * size, yVO, 0.0F), new Vector3f(-xVO * size, yVO, 0.0F), new Vector3f(-xVO * size, -yVO, 0.0F)};

        for(int i = 0; i < 4; ++i) {
            Vector3f vec3f = vertices[i];
            vec3f.transform(quaternion);
            vec3f.add(x, y, z);
        }

        vertexConsumer.vertex(vertices[0].x(), vertices[0].y(), vertices[0].z()).uv(maxU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[1].x(), vertices[1].y(), vertices[1].z()).uv(maxU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[2].x(), vertices[2].y(), vertices[2].z()).uv(minU, minV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
        vertexConsumer.vertex(vertices[3].x(), vertices[3].y(), vertices[3].z()).uv(minU, maxV).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        return this.scale;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            ShockwaveParticle shockwave = new ShockwaveParticle(clientWorld, d, e, f, g);
            shockwave.setSprite(this.spriteProvider);
            return shockwave;
        }
    }

    protected void setSprite(TextureAtlasSprite sprite) {
        this.sprite = sprite;
    }

    protected float getMinU() {
        return this.sprite.getU0();
    }

    protected float getMaxU() {
        return this.sprite.getU1();
    }

    protected float getMinV() {
        return this.sprite.getV0();
    }

    protected float getMaxV() {
        return this.sprite.getV1();
    }

    public void setSprite(SpriteSet spriteProvider) {
        this.setSprite(spriteProvider.get(this.random));
    }
}
