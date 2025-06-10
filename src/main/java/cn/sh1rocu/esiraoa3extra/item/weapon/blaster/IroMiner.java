package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.capability.volatilestack.VolatileStackCapabilityHandles;
import net.tslat.aoa3.content.capability.volatilestack.VolatileStackCapabilityProvider;
import net.tslat.aoa3.content.entity.projectile.blaster.IroMinerShotEntity;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.List;

public class IroMiner extends BaseBlaster {
    public IroMiner(double dmg, int durability, int fireDelayTicks, float energyCost) {
        super(dmg, durability, fireDelayTicks, energyCost);
    }

    @Nullable
    @Override
    public SoundEvent getFiringSound() {
        return AoASounds.ITEM_MOON_SHINER_FIRE.get();
    }

    @Override
    public void fire(ItemStack blaster, LivingEntity shooter) {
        IroMinerShotEntity iroMinerShot = new IroMinerShotEntity(shooter, this, 60);
        createEnergyShot(blaster, shooter, iroMinerShot);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        ItemStack heldStack = shooter.getMainHandItem();
        float damageMod = 1;

        if (heldStack.getItem() == this) {
            VolatileStackCapabilityHandles cap = VolatileStackCapabilityProvider.getOrDefault(heldStack, null);

            if (cap.getObject() != null && target.getUUID().equals(cap.getObject())) {
                damageMod = cap.getValue() + 0.02f;
                cap.setValue(damageMod);
            } else {
                cap.setObject(target.getUUID());
                cap.setValue(1.0f);
            }
        }
        CompoundTag nbt = shot.getPersistentData();
        float extraDmgMod = Math.max(1, nbt.getFloat("extraDmgMod"));
        float rechargeMod = 1 + 0.04f * nbt.getInt("rechargeLevel");

        return DamageUtil.dealBlasterDamage(shooter, target, shot, (float) getDamage() * damageMod * extraDmgMod * rechargeMod, false);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        stack.getOrCreateTag().putInt("HideFlags", ItemStack.TooltipPart.MODIFIERS.getMask());

        return new VolatileStackCapabilityProvider();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
