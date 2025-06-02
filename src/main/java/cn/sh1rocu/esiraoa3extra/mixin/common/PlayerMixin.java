package cn.sh1rocu.esiraoa3extra.mixin.common;

import cn.sh1rocu.esiraoa3extra.registration.EsirAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow
    @Final
    public Inventory inventory;

    protected PlayerMixin(EntityType<? extends LivingEntity> arg, Level arg2) {
        super(arg, arg2);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void esir$addCustomAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder mutableAttribute = cir.getReturnValue().add(EsirAttributes.MAGIC_DAMAGE.get());
        cir.setReturnValue(mutableAttribute);
    }

    @SuppressWarnings("all")
    @ModifyVariable(method = "getDigSpeed", at = @At(value = "STORE", ordinal = 1))
    private float esir$modifyEfficiency(float origin, @Local(argsOnly = true) BlockState arg) {
        float base = this.inventory.getDestroySpeed(arg);
        return base * (1 + (0.26F * EnchantmentHelper.getBlockEfficiency(this))) + (MobEffectUtil.hasDigSpeed(this) ? 4 * MobEffectUtil.getDigSpeedAmplification(this) + 1 : 0);
    }
}