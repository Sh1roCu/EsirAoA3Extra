package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.content.entity.projectile.staff.FireflyShotEntity;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FireflyStaff extends BaseStaff<Object> {
    public FireflyStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_FIREFLY_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.WIND_RUNE.get(), 2);
        runes.put(AoAItems.STRIKE_RUNE.get(), 2);
        runes.put(AoAItems.FIRE_RUNE.get(), 1);
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, Object args) {
        FireflyShotEntity fireflyShot = new FireflyShotEntity(caster, this, 60);
        createEnergyShot(world, staff, caster, fireflyShot);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        CompoundTag nbt = shot.getPersistentData();
        float archMageMod = 1;
        archMageMod += 0.1f * nbt.getInt("archMageLevel");
        float totalMod = archMageMod * nbt.getFloat("extraDmgMod");
        if (DamageUtil.dealMagicDamage(shot, shooter, target, getDmg() * totalMod, false)) {
            target.setSecondsOnFire(5);

            UUID targetUUID = target.getUUID();

            if (targetUUID.equals(((FireflyShotEntity) shot).lastTargetUUID))
                return true;

            for (int i = 0; i < RandomUtil.randomNumberBetween(1, 7); i++) {
                shot.level.addFreshEntity(new FireflyShotEntity(shooter, this, (FireflyShotEntity) shot, targetUUID, random.nextGaussian() * 0.35, 1.4f, random.nextGaussian() * 0.35));
            }

            return true;
        }

        return false;
    }

    @Override
    public float getDmg() {
        return 22;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
