package com.teamabode.cave_enhancements.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.animation.DripstoneTortoiseAnimation;
import com.teamabode.cave_enhancements.common.entity.dripstone_tortoise.DripstoneTortoise;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class DripstoneTortoiseModel<E extends Mob> extends HierarchicalModel<DripstoneTortoise> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CaveEnhancements.MODID, "dripstone_tortoise"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	private final ModelPart left_hind_leg;
	private final ModelPart right_hind_leg;
	private final ModelPart front_spike;
	private final ModelPart middle_spike;
	private final ModelPart hind_spike;


	public DripstoneTortoiseModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.left_front_leg = this.body.getChild("left_front_leg");
		this.right_front_leg = this.body.getChild("right_front_leg");
		this.left_hind_leg = this.body.getChild("left_hind_leg");
		this.right_hind_leg = this.body.getChild("right_hind_leg");
		this.front_spike = this.root.getChild("spike0");
		this.middle_spike = this.root.getChild("spike1");
		this.hind_spike = this.root.getChild("spike2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, 1.0F, -7.0F, 15.0F, 22.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 30).addBox(-5.5F, 3.0F, -10.0F, 11.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition left_front_leg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(28, 44).mirror().addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 2.0F, -7.0F));

		PartDefinition right_front_leg = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(28, 44).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 2.0F, -7.0F));

		PartDefinition left_hind_leg = body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(28, 44).mirror().addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 22.0F, -7.0F));

		PartDefinition right_hind_leg = body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(28, 44).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 22.0F, -7.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 30).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -10.0F));

		PartDefinition spike0 = root.addOrReplaceChild("spike0", CubeListBuilder.create(), PartPose.offset(1.0F, -14.0F, -3.5F));

		PartDefinition cube_r1 = spike0.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 58).addBox(-2.5F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = spike0.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 58).addBox(-2.5F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition spike1 = root.addOrReplaceChild("spike1", CubeListBuilder.create(), PartPose.offset(-1.0F, -14.0F, 3.5F));

		PartDefinition cube_r3 = spike1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 65).addBox(-2.5F, -8.0F, 0.0F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r4 = spike1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 65).addBox(-2.5F, -8.0F, 0.0F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition spike2 = root.addOrReplaceChild("spike2", CubeListBuilder.create(), PartPose.offset(1.0F, -14.0F, 10.25F));

		PartDefinition cube_r5 = spike2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(14, 58).addBox(-2.5F, -6.0F, 0.25F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r6 = spike2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 58).addBox(-2.5F, -6.0F, 0.25F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			poseStack.pushPose();
			poseStack.scale(0.4F, 0.4F, 0.4F);
			poseStack.translate(0.0F, 2.2F, 0.0F);
			root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			poseStack.popPose();
		} else {
			root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}

	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(DripstoneTortoise entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);

		this.front_spike.visible = !this.young;
		this.middle_spike.visible = !this.young;
		this.hind_spike.visible = !this.young;

		this.head.xRot = headPitch * 0.017453292F;
		this.head.yRot = netHeadYaw * 0.017453292F;
		this.right_hind_leg.xRot = Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
		this.left_hind_leg.xRot = Mth.cos(limbSwing * 0.662F + 3.1415927F) * limbSwingAmount;
		this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
		this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.animate(entity.stompingAnimationState, DripstoneTortoiseAnimation.STOMPING, ageInTicks);
	}
}