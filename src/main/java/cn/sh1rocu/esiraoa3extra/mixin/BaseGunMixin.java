package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.tslat.aoa3.content.item.weapon.gun.BaseGun;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BaseGun.class, remap = false)
public class BaseGunMixin {
    @Unique
    ItemStack esiraoa3extra$itemStack;

    @Inject(method = "onUsingTick", at = @At("HEAD"))
    private void onUsing(ItemStack stack, LivingEntity shooter, int count, CallbackInfo ci) {
        this.esiraoa3extra$itemStack = stack;
    }

    @ModifyVariable(
            method = "onUsingTick",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    private int reduceCD(int origin) {
        if (esiraoa3extra$itemStack.getOrCreateTag().contains("CD")) {
            double cdMod = esiraoa3extra$itemStack.getOrCreateTag().getDouble("CD");
            return (int) (origin * (1 - cdMod));
        }
        return origin;
    }
}
