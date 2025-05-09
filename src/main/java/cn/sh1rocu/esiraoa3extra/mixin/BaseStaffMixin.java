package cn.sh1rocu.esiraoa3extra.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.content.item.weapon.staff.BaseStaff;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BaseStaff.class)
public abstract class BaseStaffMixin {
    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"
            )
    )
    private void reduceCD(ItemCooldowns instance, Item item, int origin, @Local ItemStack stack) {
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            int result = (int) (origin * (1 - cdMod));
            instance.addCooldown(item, Math.max(result, 0));
        } else {
            instance.addCooldown(item, origin);
        }
    }
}
