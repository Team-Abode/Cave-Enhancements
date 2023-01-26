package com.teamabode.cave_enhancements.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.CruncherModel;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class CruncherMossCapLayer extends RenderLayer<Cruncher, CruncherModel> {

    private static final ResourceLocation MOSS_CAP = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/cruncher/moss_cap.png");
    private final CruncherModel cruncherModel;

    public CruncherMossCapLayer(RenderLayerParent<Cruncher, CruncherModel> renderLayerParent, EntityModelSet set) {
        super(renderLayerParent);
        this.cruncherModel = new CruncherModel(set.bakeLayer(CruncherModel.LAYER_LOCATION));
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Cruncher livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!livingEntity.isSheared()) {
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.cruncherModel, MOSS_CAP, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, 1.0F, 1.0F, 1.0F);
        }
    }
}
