package cn.sh1rocu.esiraoa3extra.mixin.compat.apotheosis;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.enchantments.ReboundingEnchant;

@Mixin(ReboundingEnchant.class)
public class ReboundingEnchantMixin {
    @Inject(remap = false, method = "doPostHurt", at = @At("HEAD"), cancellable = true)
    private void esir$doPostHurt(LivingEntity user, Entity attacker, int level, CallbackInfo ci) {
        ci.cancel();
    }
}
