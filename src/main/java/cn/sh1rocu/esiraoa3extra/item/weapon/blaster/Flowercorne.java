package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.network.chat.Component;
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
import net.tslat.aoa3.content.entity.projectile.blaster.FlowerShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Flowercorne extends BaseBlaster {
    public Flowercorne(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_SPRAYER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot flowerShot1 = new FlowerShotEntity(shooter, this, 60, 0, 0.25f, 0);
        BaseEnergyShot flowerShot2 = new FlowerShotEntity(shooter, this, 60, 0, 0f, 0);
        createEnergyShot(blaster, shooter, flowerShot1, flowerShot2);
    }

    @Override
    protected void doImpactEffect(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        if (target instanceof LivingEntity)
            EntityUtil.applyPotions(target, new EffectBuilder(MobEffects.WEAKNESS, 100));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.WEAKENS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
