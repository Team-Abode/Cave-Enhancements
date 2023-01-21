package com.teamabode.cave_enhancements.core.mixin;

import com.teamabode.cave_enhancements.core.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void banGlowPaste(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModItems.GLOW_PASTE.get())) cir.setReturnValue(false);
    }
}
