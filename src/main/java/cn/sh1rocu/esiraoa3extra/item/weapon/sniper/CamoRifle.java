package cn.sh1rocu.esiraoa3extra.item.weapon.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class CamoRifle extends BaseSniper {
    public CamoRifle(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_SNIPER_METALLIC_FIRE_LONG.get();
    }

    @Override
    public ResourceLocation getScopeTexture(ItemStack stack) {
        return SCOPE_2;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity holder, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && isSelected && holder.isShiftKeyDown() && holder instanceof LivingEntity)
            EntityUtil.applyPotions(holder, new EffectBuilder(MobEffects.INVISIBILITY, 5));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
