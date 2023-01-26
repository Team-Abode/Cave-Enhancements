package com.teamabode.cave_enhancements.client.model;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstonePike;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DripstonePikeModel extends HierarchicalModel<DripstonePike> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "dripstone_pike"), "main");
	private final ModelPart root;

	public DripstonePikeModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition root = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 3.14159F, 0.0F, 0.0F));
		PartDefinition plane1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 48.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.785398F, 0.0F));
		PartDefinition plane2 = root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 48.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.35619F, 0.0F));

		return LayerDefinition.create(meshDefinition, 32, 48);
	}

	public void setupAnim(DripstonePike entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.y = Mth.clamp(ageInTicks * 6, 0, 24);
	}

	public ModelPart root() {
		return this.root;
	}
}