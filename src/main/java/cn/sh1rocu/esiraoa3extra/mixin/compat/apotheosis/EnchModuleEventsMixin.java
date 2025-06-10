package cn.sh1rocu.esiraoa3extra.mixin.compat.apotheosis;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.EnchModuleEvents;

@Mixin(EnchModuleEvents.class)
public class EnchModuleEventsMixin {
    @Inject(remap = false, method = "livingHurt", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingHurtEvent;setAmount(F)V"), cancellable = true)
    private void esir$onLivingHurt(LivingHurtEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
