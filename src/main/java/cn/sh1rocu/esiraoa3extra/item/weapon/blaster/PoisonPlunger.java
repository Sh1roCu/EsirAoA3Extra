package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.ColourUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class PoisonPlunger extends BaseBlaster {
    public PoisonPlunger(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GAS_GUN_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        float x = (-Mth.sin(shooter.yRot / 180.0F * (float) Math.PI) * Mth.cos(shooter.xRot / 180.0F * (float) Math.PI)) * 5;
        float y = (-Mth.sin(shooter.xRot / 180.0F * (float) Math.PI)) * 5;
        float z = (Mth.cos(shooter.yRot / 180.0F * (float) Math.PI) * Mth.cos(shooter.xRot / 180.0F * (float) Math.PI)) * 5;

        AreaEffectCloud cloud = new AreaEffectCloud(shooter.level, shooter.getX() + x, shooter.getY() + shooter.getEyeHeight() + y, shooter.getZ() + z);

        cloud.setRadius(4);
        cloud.setWaitTime(0);
        cloud.setDuration(100);
        cloud.setFixedColor(ColourUtil.RGB(51, 102, 0));
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1, false, true));
        cloud.setOwner(shooter);
        shooter.level.addFreshEntity(cloud);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}