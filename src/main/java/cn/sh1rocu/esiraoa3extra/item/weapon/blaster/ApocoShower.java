package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.HeavyShowerShotEntity;
import net.tslat.aoa3.content.entity.projectile.blaster.ShowerShotEntity;
import net.tslat.aoa3.content.entity.projectile.blaster.WeightedShowerShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ApocoShower extends BaseBlaster {
    public ApocoShower(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_SPIRIT_SHOWER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        BaseEnergyShot showerShot = new ShowerShotEntity(shooter, this, 60);
        BaseEnergyShot weightedShowerShot = new WeightedShowerShotEntity(shooter, this, 60);
        BaseEnergyShot heavyShowerShot = new HeavyShowerShotEntity(shooter, this, 60);
        createEnergyShot(blaster, shooter, showerShot, weightedShowerShot, heavyShowerShot);
    }

    @Override
    public void doBlockImpact(BaseEnergyShot shot, Vector3d hitPos, LivingEntity shooter) {
        WorldUtil.createExplosion(shooter, shot.level, shot, 2.5f);
    }

    @Override
    protected void doImpactEffect(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        WorldUtil.createExplosion(shooter, shot.level, shot, 2.5f);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
