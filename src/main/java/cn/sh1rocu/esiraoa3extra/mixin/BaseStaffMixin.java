package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tslat.aoa3.content.item.weapon.staff.BaseStaff;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseStaff.class)
public class BaseStaffMixin {
    @Unique
    ItemStack esiraoa3extra$itemStack;

    @Inject(method = "use", at = @At("HEAD"))
    private void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        this.esiraoa3extra$itemStack = player.getItemInHand(hand);
    }

    @ModifyConstant(
            method = "use",
            constant = @Constant(intValue = 24)
    )
    private int reduceCD(int origin) {
        if (esiraoa3extra$itemStack.getOrCreateTag().contains("CD")) {
            double cdMod = esiraoa3extra$itemStack.getOrCreateTag().getDouble("CD");
            return (int) (origin * (1 - cdMod));
        }
        return origin;
    }
}
