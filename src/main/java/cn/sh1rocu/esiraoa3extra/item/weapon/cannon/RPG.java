package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.advent.AdventOfAscension;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.common.registration.AoAWeapons;
import net.tslat.aoa3.content.entity.projectile.cannon.RPGEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.AdvancementUtil;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class RPG extends BaseCannon {
    private final double dmg;
    private final int firingDelay;

    public RPG(double dmg, int durability, int firingDelayTicks, float recoil) {
        super(dmg, durability, firingDelayTicks, recoil);
        this.dmg = dmg;
        this.firingDelay = firingDelayTicks;
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_RPG_FIRE.get();
    }

    @Override
    public Item getAmmoItem() {
        return AoAWeapons.GRENADE.get();
    }

    @Override
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, Hand hand) {
        return new RPGEntity(shooter, this, hand, 120, 0);
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            if (target instanceof LivingEntity)
                bulletDmgMultiplier *= 1 + (((LivingEntity) target).getAttribute(Attributes.ARMOR).getValue() * 6.66) / 100;

            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) dmg * bulletDmgMultiplier) && shooter instanceof ServerPlayerEntity) {
                if (target instanceof LivingEntity && ((LivingEntity) target).getHealth() == 0 && target.hasImpulse) {
                    if (target.level.isEmptyBlock(target.blockPosition().below()) && target.level.isEmptyBlock(target.blockPosition().below(2)))
                        AdvancementUtil.completeAdvancement((ServerPlayerEntity) shooter, new ResourceLocation(AdventOfAscension.MOD_ID, "overworld/surface_to_air"), "rpg_air_kill");
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));

        super.appendHoverText(stack, world, tooltip, flag);
    }
}
