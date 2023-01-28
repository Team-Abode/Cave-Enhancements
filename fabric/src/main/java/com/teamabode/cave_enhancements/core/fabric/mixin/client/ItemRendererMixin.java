package com.teamabode.cave_enhancements.core.fabric.mixin.client;

import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @ParametersAreNonnullByDefault
    @ModifyVariable(method = "render", at = @At(value = "HEAD"), index = 8, argsOnly = true)
    private BakedModel renderItem(BakedModel model, ItemStack stack, ItemTransforms.TransformType renderMode) {
        if (stack.is(ModItems.AMETHYST_FLUTE.get())) {
            if (renderMode == ItemTransforms.TransformType.GUI || renderMode == ItemTransforms.TransformType.GROUND || renderMode == ItemTransforms.TransformType.FIXED) {
                model = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("cave_enhancements:amethyst_flute#inventory"));
            } else {
                model = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("cave_enhancements:amethyst_flute_in_hand#inventory"));
            }
        }
        return model;
    }
}
