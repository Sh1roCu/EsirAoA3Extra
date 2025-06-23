package cn.sh1rocu.esiraoa3extra.item.weapon.cannon;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.advent.AdventOfAscension;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.common.registration.AoAWeapons;
import net.tslat.aoa3.content.entity.projectile.cannon.FloroRPGEntity;
import net.tslat.aoa3.content.entity.projectile.gun.BaseBullet;
import net.tslat.aoa3.util.AdvancementUtil;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class FloroRPG extends BaseCannon {
    private final double dmg;
    private final int firingDelay;

    public FloroRPG(double dmg, int durability, int firingDelayTicks, float recoil) {
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
    public BaseBullet createProjectileEntity(LivingEntity shooter, ItemStack gunStack, InteractionHand hand) {
        return new FloroRPGEntity(shooter, this, hand, 120, 0);
    }

    @Override
    public void doImpactDamage(Entity target, LivingEntity shooter, BaseBullet bullet, float bulletDmgMultiplier) {
        if (target != null) {
            if (target instanceof LivingEntity)
                bulletDmgMultiplier *= 1 + (((LivingEntity) target).getAttribute(Attributes.ARMOR).getValue() * 6.66) / 100;
            float shellMod = 1;
            CompoundTag nbt = bullet.getPersistentData();
            shellMod += 0.1f * nbt.getInt("shellLevel");
            float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
            if (DamageUtil.dealGunDamage(target, shooter, bullet, (float) dmg * bulletDmgMultiplier * shellMod * extraDmgMod) && shooter instanceof ServerPlayer) {
                if (target instanceof LivingEntity && ((LivingEntity) target).getHealth() == 0 && target.hasImpulse) {
                    if (target.level.isEmptyBlock(target.blockPosition().below()) && target.level.isEmptyBlock(target.blockPosition().below(2)))
                        AdvancementUtil.completeAdvancement((ServerPlayer) shooter, new ResourceLocation(AdventOfAscension.MOD_ID, "overworld/surface_to_air"), "rpg_air_kill");
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));

        super.appendHoverText(stack, world, tooltip, flag);
    }
}
