package com.teamabode.cave_enhancements.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.DripstonePikeModel;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstonePike;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DripstonePikeRenderer extends EntityRenderer<DripstonePike> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/dripstone_tortoise/dripstone_pike.png");
    private final DripstonePikeModel model;

    public DripstonePikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DripstonePikeModel(context.bakeLayer(DripstonePikeModel.LAYER_LOCATION));
    }

    public void render(DripstonePike entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float ageInTicks = entity.tickCount + partialTick;
        this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, entity.getYRot(), entity.getXRot());


        if (entity.tickCount >= 8) {
            float initialScale = Mth.clamp(1.0F - ageInTicks / 10, 0, 1.0F);
            poseStack.scale(initialScale, initialScale, initialScale);
        }


        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(DripstonePike entity) {
        return TEXTURE;
    }
}
