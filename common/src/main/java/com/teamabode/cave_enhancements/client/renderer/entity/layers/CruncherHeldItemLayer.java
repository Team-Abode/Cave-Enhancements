package com.teamabode.cave_enhancements.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamabode.cave_enhancements.client.model.CruncherModel;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class CruncherHeldItemLayer extends RenderLayer<Cruncher, CruncherModel> {

    private final ItemInHandRenderer heldItemRenderer;

    public CruncherHeldItemLayer(RenderLayerParent<Cruncher, CruncherModel> featureRendererContext, ItemInHandRenderer heldItemRenderer) {
        super(featureRendererContext);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Cruncher cruncher, float f, float g, float h, float j, float k, float l) {
        matrixStack.pushPose();

        matrixStack.translate(0, 1.25, 0);

        matrixStack.mulPose(Vector3f.YP.rotation(getParentModel().root().getChild("body").getChild("head").yRot));
        matrixStack.mulPose(Vector3f.XP.rotation(getParentModel().root().getChild("body").getChild("head").xRot));

        matrixStack.translate(0, -.2, -.5);

        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        ItemStack itemStack = cruncher.getItemBySlot(EquipmentSlot.MAINHAND);

        this.heldItemRenderer.renderItem(cruncher, itemStack, TransformType.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.popPose();

    }
}