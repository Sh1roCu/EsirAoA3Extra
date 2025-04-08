package cn.sh1rocu.esiraoa3extra.mixin;

import cn.sh1rocu.esiraoa3extra.registration.EsirAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void addCustomAttributes(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> cir) {
        AttributeModifierMap.MutableAttribute mutableAttribute = cir.getReturnValue().add(EsirAttributes.MAGIC_DAMAGE.get());
        cir.setReturnValue(mutableAttribute);
    }
}