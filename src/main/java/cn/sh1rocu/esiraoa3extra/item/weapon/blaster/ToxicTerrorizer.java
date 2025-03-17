package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.ToxicShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ToxicTerrorizer extends BaseBlaster {
    public ToxicTerrorizer(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_MAGIC_GUN_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        ToxicShotEntity toxicShot1 = new ToxicShotEntity(shooter, this, 60);
        ToxicShotEntity toxicShot2 = new ToxicShotEntity(shooter, this, 60, -0.05f, -0.05f, 0f);
        ToxicShotEntity toxicShot3 = new ToxicShotEntity(shooter, this, 60, 0.05f, -0.05f, 0f);
        ToxicShotEntity toxicShot4 = new ToxicShotEntity(shooter, this, 60, 0, -0.05f, -0.05f);
        ToxicShotEntity toxicShot5 = new ToxicShotEntity(shooter, this, 60, 0, -0.05f, 0.05f);
        createEnergyShot(blaster, shooter, toxicShot1, toxicShot2, toxicShot3, toxicShot4, toxicShot5);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        if (target instanceof LivingEntity)
            EntityUtil.applyPotions(target, new EffectBuilder(Effects.POISON, 185).level(2));

        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.POISONS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
