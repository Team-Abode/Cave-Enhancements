package com.teamabode.cave_enhancements.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class GoopModel extends EntityModel<Goop> {
	public static final ModelLayerLocation ENTITY_MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "goop"), "main");
	private final ModelPart root;

	public GoopModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition root = partDefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 2.0F, -4.0F, 10.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(-14, 14).addBox(-8.0F, 8.0F, -7.0F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	public void setupAnim(Goop entity, float f, float g, float h, float i, float j) {
		float degToRad = 0.0174533F;

		this.root.y = entity.isHanging() ? 16.5F : 16.0F;
		this.root.xRot = entity.isHanging() ? 180F * degToRad : 0.0F;
	}
}