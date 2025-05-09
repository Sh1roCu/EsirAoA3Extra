package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class VortexBlaster extends BaseBlaster {
    public VortexBlaster(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GRAVITY_BLASTER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        float x = -Mth.sin(shooter.yRot / 180.0F * (float) Math.PI) * Mth.cos(shooter.xRot / 180.0F * (float) Math.PI);
        float y = -Mth.sin(shooter.xRot / 180.0F * (float) Math.PI);
        float z = Mth.cos(shooter.yRot / 180.0F * (float) Math.PI) * Mth.cos(shooter.xRot / 180.0F * (float) Math.PI);

        for (LivingEntity entity : shooter.level.getEntitiesOfClass(LivingEntity.class, shooter.getBoundingBox().inflate(x * 7 + 1, y * 7 + 1, z * 7 + 1))) {
            DamageUtil.doScaledKnockback(entity, shooter, 4f, shooter.getX() - entity.getX(), shooter.getZ() - entity.getZ());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
