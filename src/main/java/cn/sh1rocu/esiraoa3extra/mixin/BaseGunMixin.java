package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.item.ItemStack;
import net.tslat.aoa3.content.item.weapon.gun.BaseGun;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = BaseGun.class, remap = false)
public abstract class BaseGunMixin {
    @ModifyVariable(
            method = "onUsingTick",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    private int reduceCD(int origin, ItemStack stack) {
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            int result = (int) (origin * (1 - cdMod));
            return Math.max(result, 0);
        }
        return origin;
    }
}
