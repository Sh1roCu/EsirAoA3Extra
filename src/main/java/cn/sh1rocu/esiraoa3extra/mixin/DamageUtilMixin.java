package cn.sh1rocu.esiraoa3extra.mixin;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.util.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DamageUtil.class, remap = false)
public class DamageUtilMixin {
    @Inject(method = "isPlayerEnvironmentallyProtected", at = @At("TAIL"), cancellable = true)
    private static void esir$isPlayerEnvironmentallyProtected(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        NonNullList<ItemStack> armors = player.inventory.armor;
        ItemStack helmet = armors.get(EquipmentSlot.HEAD.getIndex());
        ItemStack chest = armors.get(EquipmentSlot.CHEST.getIndex());
        ItemStack legs = armors.get(EquipmentSlot.LEGS.getIndex());
        ItemStack feet = armors.get(EquipmentSlot.FEET.getIndex());
        cir.setReturnValue(cir.getReturnValue() ||
                EsirUtil.isPlayerEnvironmentallyProtected(helmet) ||
                EsirUtil.isPlayerEnvironmentallyProtected(chest) ||
                EsirUtil.isPlayerEnvironmentallyProtected(legs) ||
                EsirUtil.isPlayerEnvironmentallyProtected(feet)
        );
    }
}
