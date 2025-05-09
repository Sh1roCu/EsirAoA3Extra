package cn.sh1rocu.esiraoa3extra.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
                    target = "Lnet/tslat/aoa3/content/item/weapon/crossbow/BaseCrossbow;fireProjectiles(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;FF)V"
            )
    )
    public void esir$preFired(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        EnchHooks.preArrowFired(player.getItemInHand(hand));
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            )
    )
    public void esir$addCharges(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
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
