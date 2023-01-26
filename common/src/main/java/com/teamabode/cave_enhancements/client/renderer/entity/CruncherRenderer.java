package com.teamabode.cave_enhancements.client.renderer.entity;

import com.teamabode.cave_enhancements.CaveEnhancements;
import com.teamabode.cave_enhancements.client.model.CruncherModel;
import com.teamabode.cave_enhancements.client.renderer.entity.layers.CruncherHeldItemLayer;
import com.teamabode.cave_enhancements.client.renderer.entity.layers.CruncherMossCapLayer;
import com.teamabode.cave_enhancements.common.entity.cruncher.Cruncher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CruncherRenderer extends MobRenderer<Cruncher, CruncherModel> {
    public CruncherRenderer(EntityRendererProvider.Context context) {
        super(context, new CruncherModel(context.bakeLayer(CruncherModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CruncherHeldItemLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new CruncherMossCapLayer(this, context.getModelSet()));
    }

    public ResourceLocation getTextureLocation(@NotNull Cruncher entity) {
        return new ResourceLocation(CaveEnhancements.MODID, "textures/entity/cruncher/cruncher.png");
    }
}