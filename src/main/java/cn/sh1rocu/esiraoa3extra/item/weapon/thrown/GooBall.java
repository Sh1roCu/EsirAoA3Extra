package cn.sh1rocu.esiraoa3extra.item.weapon.thrown;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.content.entity.projectile.thrown.GooBallEntity;
import net.tslat.aoa3.library.builder.EffectBuilder;
import net.tslat.aoa3.util.EntityUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class GooBall extends BaseThrownWeapon {
    public GooBall() {
        super(3.5f, 7);
    }

    @Override
    protected float getFiringSoundPitchAdjust() {
        return 0.5f;
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new GooBallEntity(shooter, this);
    }

    @Override
    protected void doImpactEffect(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        EntityUtil.applyPotions(target, new EffectBuilder(MobEffects.MOVEMENT_SLOWDOWN, 60).level(2));
        shooter.level.playSound(null, bullet.getX(), bullet.getY(), bullet.getZ(), AoASounds.GOO_BALL_IMPACT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.SLOWS_TARGETS, LocaleUtil.ItemDescriptionType.BENEFICIAL));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
