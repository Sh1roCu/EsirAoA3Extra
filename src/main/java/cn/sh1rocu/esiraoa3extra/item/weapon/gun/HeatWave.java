package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.HotShotEntity;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class HeatWave extends BaseGun {
    public HeatWave(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_CANNON_FIRE_1_SHORT.get();
    }

    @Override
    public boolean isFullAutomatic() {
        return false;
    }

    @Override
    public Item getAmmoItem() {
        return AoAItems.METAL_SLUG.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new HotShotEntity(shooter, this, hand, 120, 0);
    }

    @Override
    protected void doImpactEffect(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        AreaEffectCloud cloud = new AreaEffectCloud(bullet.level, (target.getX() + bullet.getX()) / 2d, (target.getY() + bullet.getY()) / 2d, (target.getZ() + bullet.getZ()) / 2d);

        cloud.setOwner(shooter);
        cloud.setParticle(ParticleTypes.FLAME);
        cloud.setRadius(1f);
        cloud.setDuration(5);
        cloud.setRadiusPerTick(0.4f);
        cloud.setWaitTime(0);

        bullet.level.addFreshEntity(cloud);

        for (LivingEntity entity : bullet.level.getEntitiesOfClass(LivingEntity.class, cloud.getBoundingBox().inflate(2, 1, 2), EntityUtil.Predicates.HOSTILE_MOB)) {
            entity.setSecondsOnFire(4);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));

        super.appendHoverText(stack, world, tooltip, flag);
    }
}
