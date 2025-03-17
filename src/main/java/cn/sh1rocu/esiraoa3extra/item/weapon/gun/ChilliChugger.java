package cn.sh1rocu.esiraoa3extra.item.weapon.gun;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.gun.ChilliShotEntity;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ChilliChugger extends BaseGun {
    double dmg;
    int firingDelay;

    public ChilliChugger(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(AoAItemGroups.GUNS, dmg, durability, firingDelayTicks, recoil);
        this.dmg = dmg;
        this.firingDelay = firingDelayTicks;
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_GUN_AIR_CANNON_FIRE.get();
    }

    @Override
    protected float getFiringSoundPitchAdjust() {
        return 0.85f;
    }

    @Override
    public Item getAmmoItem() {
        return AoAItems.CHILLI.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new ChilliShotEntity(shooter, this, hand, 120, 0);
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        super.doImpactDamage(target, shooter, bullet, bulletDmgMultiplier);

        if (target instanceof LivingEntity)
            target.setSecondsOnFire(6);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.BURNS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));

        super.appendHoverText(stack, world, tooltip, flag);
    }
}
