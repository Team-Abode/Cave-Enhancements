package com.teamabode.cave_enhancements.client.renderer;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.GoopModel;
import com.teamabode.cave_enhancements.common.entity.goop.Goop;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoopRenderer extends MobRenderer<Goop, GoopModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CaveEnhancements.MODID, "textures/entity/goop.png");

    public GoopRenderer(EntityRendererProvider.Context context) {
        super(context, new GoopModel(context.bakeLayer(GoopModel.ENTITY_MODEL_LAYER)), 0.5f);
    }

    public ResourceLocation getTextureLocation(Goop entity) {
        return TEXTURE;
    }
}