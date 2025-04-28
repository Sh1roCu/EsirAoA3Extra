package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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
                    target = "Lnet/minecraft/util/CooldownTracker;addCooldown(Lnet/minecraft/item/Item;I)V"
            )
    )
    private void reduceCD(CooldownTracker instance, Item item, int origin, World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getOrCreateTag().contains("CD")) {
            double cdMod = stack.getOrCreateTag().getDouble("CD");
            int result = (int) (origin * (1 - cdMod));
            instance.addCooldown(item, Math.max(result, 0));
        } else {
            instance.addCooldown(item, origin);
        }
    }
}
