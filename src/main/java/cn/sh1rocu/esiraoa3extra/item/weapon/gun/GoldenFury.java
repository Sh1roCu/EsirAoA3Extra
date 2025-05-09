package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GoldenFury extends BaseGun {
    public GoldenFury(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_GENERIC_FIRE_3.get();
    }

    @Override
    protected void doFiringEffects(LivingEntity shooter, BaseBullet bullet, ItemStack stack, InteractionHand hand) {
        super.doFiringEffects(shooter, bullet, stack, hand);

        for (int i = 0; i < 6; i++) {
            ((ServerLevel) shooter.level).sendParticles(ParticleTypes.DRAGON_BREATH, bullet.getX() + random.nextGaussian() / 5d, bullet.getY() + random.nextGaussian() / 5d, bullet.getZ() + random.nextGaussian() / 5d, 1, 0, 0, 0, 0d);
        }
    }

    @Override
    protected void doImpactEffect(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        AreaEffectCloud cloud = new AreaEffectCloud(bullet.level, (target.getX() + bullet.getX()) / 2d, (target.getY() + bullet.getY()) / 2d, (target.getZ() + bullet.getZ()) / 2d);

        cloud.setOwner(shooter);
        cloud.setParticle(ParticleTypes.DRAGON_BREATH);
        cloud.setRadius(1f);
        cloud.setDuration(20);
        cloud.setRadiusPerTick((5.0F - cloud.getRadius()) / (float) cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));

        bullet.level.addFreshEntity(cloud);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
