package cn.sh1rocu.esiraoa3extra.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.content.item.weapon.gun.BaseGun;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BaseGun.class)
public abstract class BaseGunMixin {
    @ModifyArg(
            method = "onUsingTick",
            at = @At(
                    remap = true,
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"
            ),
            index = 1,
            remap = false
    )
    private int reduceCD(int origin, @Local(ordinal = 1) int nextFireDelay, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            int result = (int) (origin * (1 - cdMod));
            return Math.max(result, 0);
        }
        return origin;
    }
}
