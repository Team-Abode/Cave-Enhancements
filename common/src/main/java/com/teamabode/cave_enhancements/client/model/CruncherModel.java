package com.teamabode.cave_enhancements.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.animation.CruncherAnimation;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CruncherModel extends HierarchicalModel<Cruncher> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "cruncher"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart upperJaw;
	private final ModelPart lowerJaw;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;

	public CruncherModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.body.getChild("head");
		this.upperJaw = this.head.getChild("upper_jaw");
		this.lowerJaw = this.head.getChild("lower_jaw");
		this.leftFrontLeg = this.body.getChild("left_front_leg");
		this.rightFrontLeg = this.body.getChild("right_front_leg");
		this.leftHindLeg = this.body.getChild("left_hind_leg");
		this.rightHindLeg = this.body.getChild("right_hind_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F,
				-2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition upperJaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -3.0F, -10.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -3.0F, 4.0F));

		PartDefinition lowerJaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 13).addBox(
				-4.0F, 0.0F, -10.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));

		PartDefinition leftFrontLeg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 18)
				.addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.25F, -0.5F, -2.25F));

		PartDefinition rightFrontLeg = body.addOrReplaceChild("right_front_leg",
				CubeListBuilder.create().texOffs(0, 18).mirror()
						.addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-2.25F, -0.5F, -2.25F));

		PartDefinition rightHindLeg = body.addOrReplaceChild("right_hind_leg",
				CubeListBuilder.create().texOffs(0, 18).mirror()
						.addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-2.25F, -0.5F, 2.25F));

		PartDefinition leftHindLeg = body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 18)
				.addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.25F, -0.5F, 2.25F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			poseStack.pushPose();
			poseStack.scale(0.75F, 0.75F, 0.75F);
			poseStack.translate(0.0F, 0.5F, 0.0F);
			root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

			poseStack.popPose();
		} else {
			root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

	}

	public void setupAnim(Cruncher entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float k = Math.min((float)entity.getDeltaMovement().lengthSqr() * 200.0F, 8.0F);
		this.animate(entity.chompAnimationState, CruncherAnimation.CHOMPING, ageInTicks);
		this.animate(entity.walkAnimationState, CruncherAnimation.WALKING, ageInTicks, k);


		this.head.xRot = headPitch * 0.017453292F;
		this.head.yRot = netHeadYaw * 0.017453292F;
		//this.rightHindLeg.xRot = Mth.cos(limbSwing * 1.5708F) * 1.4F * limbSwingAmount;
		//this.leftHindLeg.xRot = Mth.cos(limbSwing * 1.5708F + 3.1415927F) * 1.4F * limbSwingAmount;
		//this.rightFrontLeg.xRot = Mth.cos(limbSwing * 1.5708F + 3.1415927F) * 1.4F * limbSwingAmount;
		//this.leftFrontLeg.xRot = Mth.cos(limbSwing * 1.5708F) * 1.4F * limbSwingAmount;
	}

	public ModelPart root() {
		return this.root;
	}
}