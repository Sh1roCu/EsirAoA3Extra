package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.blaster.SwarmShotEntity;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Swarmotron extends BaseBlaster {
    public Swarmotron(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_SWARMOTRON_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        shooter.level.addFreshEntity(new SwarmShotEntity(shooter, this, 60, 0, 0, 0));
        shooter.level.addFreshEntity(new SwarmShotEntity(shooter, this, 60, -0.125f, 0f, -0.125f));
        shooter.level.addFreshEntity(new SwarmShotEntity(shooter, this, 60, -0.125f, 0, 0));
        shooter.level.addFreshEntity(new SwarmShotEntity(shooter, this, 60, 0.125f, -0.125f, 0.125f));
        shooter.level.addFreshEntity(new SwarmShotEntity(shooter, this, 60, 0.125f, 0.125f, 0.125f));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
