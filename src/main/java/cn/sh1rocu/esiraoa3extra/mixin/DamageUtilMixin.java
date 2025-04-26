package cn.sh1rocu.esiraoa3extra.mixin;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.tslat.aoa3.util.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DamageUtil.class, remap = false)
public class DamageUtilMixin {
    @Inject(method = "isPlayerEnvironmentallyProtected", at = @At("TAIL"), cancellable = true)
    private static void esir$isPlayerEnvironmentallyProtected(ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        NonNullList<ItemStack> armors = player.inventory.armor;
        ItemStack helmet = armors.get(EquipmentSlotType.HEAD.getIndex());
        ItemStack chest = armors.get(EquipmentSlotType.CHEST.getIndex());
        ItemStack legs = armors.get(EquipmentSlotType.LEGS.getIndex());
        ItemStack feet = armors.get(EquipmentSlotType.FEET.getIndex());
        cir.setReturnValue(cir.getReturnValue() ||
                EsirUtil.isPlayerEnvironmentallyProtected(helmet) ||
                EsirUtil.isPlayerEnvironmentallyProtected(chest) ||
                EsirUtil.isPlayerEnvironmentallyProtected(legs) ||
                EsirUtil.isPlayerEnvironmentallyProtected(feet)
        );
    }
}
