package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;
import net.tslat.aoa3.content.item.weapon.crossbow.BaseCrossbow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.ench.asm.EnchHooks;

@Mixin(BaseCrossbow.class)
public abstract class BaseCrossbowMixin {
    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/tslat/aoa3/content/item/weapon/crossbow/BaseCrossbow;fireProjectiles(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FF)V",
                    remap = false
            )
    )
    public void esir$preFired(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        EnchHooks.preArrowFired(player.getItemInHand(hand));
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            )
    )
    public void esir$addCharges(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        EnchHooks.onArrowFired(player.getItemInHand(hand));
    }

    @Inject(
            method = "createArrow",
            at = @At(value = "RETURN"),
            remap = false
    )
    private void esir$markArrows(LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack, CallbackInfoReturnable<CustomArrowEntity> cir) {
        EnchHooks.markGeneratedArrows(cir.getReturnValue(), crossbowStack);
    }
}
