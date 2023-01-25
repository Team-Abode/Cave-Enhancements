package com.teamabode.cave_enhancements.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.block.entity.RoseQuartzChimesBlockEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RoseQuartzChimesRenderer implements BlockEntityRenderer<RoseQuartzChimesBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/rose_quartz_chimes.png");
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "rose_quartz_chimes"), "main");
    private final ModelPart chime0;
    private final ModelPart chime1;
    private final ModelPart chime2;
    private final ModelPart chime3;

    public RoseQuartzChimesRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(MODEL_LAYER);
        this.chime0 = modelPart.getChild("chime0");
        this.chime1 = modelPart.getChild("chime1");
        this.chime2 = modelPart.getChild("chime2");
        this.chime3 = modelPart.getChild("chime3");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition chime0 = partDefinition.addOrReplaceChild("chime0", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .texOffs(0, -1).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(7.0F, 10.0F, 7.0F, 3.14159F, 0F, 0F));

        PartDefinition chime1 = partDefinition.addOrReplaceChild("chime1", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .texOffs(0, -1).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(9.0F, 10.0F, 7.0F, 3.14159F, 0F, 0F));

        PartDefinition chime2 = partDefinition.addOrReplaceChild("chime2", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .texOffs(0, -1).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(7.0F, 10.0F, 9.0F, 3.14159F, 0F, 0F));

        PartDefinition chime3 = partDefinition.addOrReplaceChild("chime3", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .texOffs(0, -1).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(9.0F, 10.0F, 9.0F, 3.14159F, 0F, 0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    public void render(RoseQuartzChimesBlockEntity blockEntity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        float animTime = blockEntity.ticks + partialTick;
        float degToRad = 0.0174533F;

        this.chime0.xRot = (180F + (Mth.cos((float) (animTime * 0.18)) * 5.0F)) * degToRad;
        this.chime0.zRot = Mth.sin((float) (animTime * 0.18)) * 5.0F * degToRad;

        this.chime1.xRot = (180F + (Mth.cos((float) (animTime * 0.18)) * -5.0F)) * degToRad;
        this.chime1.zRot = Mth.sin((float) (animTime * 0.18)) * 5.0F * degToRad;

        this.chime2.xRot = (180F + (Mth.cos((float) (animTime * 0.18)) * 5.0F)) * degToRad;
        this.chime2.zRot = Mth.sin((float) (animTime * 0.18)) * -5.0F * degToRad;

        this.chime3.xRot = (180F + (Mth.cos((float) (animTime * 0.18)) * -5.0F)) * degToRad;
        this.chime3.zRot = Mth.sin((float) (animTime * 0.18)) * -5.0F * degToRad;


        VertexConsumer vertexConsumer = pBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.chime0.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
        this.chime1.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
        this.chime2.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
        this.chime3.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
    }
}
