package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
        SwarmShotEntity swarmShot1 = new SwarmShotEntity(shooter, this, 60, 0, 0, 0);
        SwarmShotEntity swarmShot2 = new SwarmShotEntity(shooter, this, 60, -0.125f, 0f, -0.125f);
        SwarmShotEntity swarmShot3 = new SwarmShotEntity(shooter, this, 60, -0.125f, 0, 0);
        SwarmShotEntity swarmShot4 = new SwarmShotEntity(shooter, this, 60, 0.125f, -0.125f, 0.125f);
        SwarmShotEntity swarmShot5 = new SwarmShotEntity(shooter, this, 60, 0.125f, 0.125f, 0.125f);
        createEnergyShot(blaster, shooter, swarmShot1, swarmShot2, swarmShot3, swarmShot4, swarmShot5);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
