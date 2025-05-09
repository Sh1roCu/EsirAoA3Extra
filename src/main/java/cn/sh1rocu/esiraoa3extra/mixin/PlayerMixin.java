package cn.sh1rocu.esiraoa3extra.mixin;

import cn.sh1rocu.esiraoa3extra.registration.EsirAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void addCustomAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder mutableAttribute = cir.getReturnValue().add(EsirAttributes.MAGIC_DAMAGE.get());
        cir.setReturnValue(mutableAttribute);
    }
}