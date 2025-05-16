package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import cn.sh1rocu.esiraoa3extra.item.misc.BaseMagazine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tslat.aoa3.util.ItemUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemUtil.class)
public class ItemUtilMixin {
    @Inject(remap = false, method = "findInventoryItem", at = @At("HEAD"), cancellable = true)
    private static void esir$consumeItemInMagazine(Player player, ItemStack stack, boolean consumeItem, int amount, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isEmpty()) cir.setReturnValue(false);
        else if (player.isCreative())
            cir.setReturnValue(true);
        else {
            String bulletName = stack.getItem().getRegistryName().getPath();
            if ("cannonball".equals(bulletName) || "limonite_bullet".equals(bulletName) || "metal_slug".equals(bulletName) || "spreadshot".equals(bulletName)) {
                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    ItemStack check = player.inventory.getItem(i);
                    if (check.isEmpty() || !(check.getItem() instanceof BaseMagazine)) continue;
                    BaseMagazine baseMagazine = (BaseMagazine) check.getItem();
                    if (baseMagazine.getBulletName(baseMagazine.getType()).replace("item.aoa3.", "").equals(bulletName)) {
                        CompoundTag nbt = check.getOrCreateTag();
                        int count = nbt.getInt("remaining_bullets");
                        if (count >= amount) {
                            if (consumeItem) nbt.putInt("remaining_bullets", count - amount);
                            cir.setReturnValue(true);
                            break;
                        }
                    }
                }
            }
        }
    }
}
