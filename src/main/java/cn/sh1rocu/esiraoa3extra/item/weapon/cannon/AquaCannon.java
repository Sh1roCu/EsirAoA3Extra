package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.cannon.HeavyBlueCannonballEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class AquaCannon extends BaseCannon {
    public AquaCannon(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_BALL_CANNON_FIRE.get();
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        super.doImpactDamage(target, shooter, bullet, bulletDmgMultiplier);

        if (target instanceof LivingEntity)
            EntityUtil.healEntity(shooter, 2.0f);
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new HeavyBlueCannonballEntity(shooter, this, hand, 120, 0);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
