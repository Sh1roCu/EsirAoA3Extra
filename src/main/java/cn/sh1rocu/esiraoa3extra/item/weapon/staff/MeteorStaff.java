package cn.sh1rocu.esiraoa3extra.item.weapon.staff;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tslat.aoa3.common.registration.AoAItems;
import net.tslat.aoa3.common.registration.AoASounds;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.content.entity.projectile.staff.MeteorFallEntity;
import net.tslat.aoa3.util.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class MeteorStaff extends BaseStaff<BlockPos> {
    public MeteorStaff(int durability) {
        super(durability);
    }

    @Nullable
    @Override
    public SoundEvent getCastingSound() {
        return AoASounds.ITEM_METEOR_STAFF_CAST.get();
    }

    @Override
    protected void populateRunes(HashMap<Item, Integer> runes) {
        runes.put(AoAItems.COMPASS_RUNE.get(), 1);
        runes.put(AoAItems.LUNAR_RUNE.get(), 2);
        runes.put(AoAItems.KINETIC_RUNE.get(), 2);
    }

    @Override
    public BlockPos checkPreconditions(LivingEntity caster, ItemStack staff) {
        BlockPos trace = null;

        if (caster instanceof Player)
            trace = PlayerUtil.getBlockAimingAt((Player) caster, 70);

        return trace;
    }

    @Override
    public void cast(Level world, ItemStack staff, LivingEntity caster, BlockPos args) {
        for (int i = 0; i < 8; i++) {
            MeteorFallEntity meteorFall = new MeteorFallEntity(caster, this, (args.getX() - 4) + RandomUtil.randomValueUpTo(8), args.getY() + 30, (args.getZ() - 4) + RandomUtil.randomValueUpTo(8), 3.0f);
            createEnergyShot(world, staff, caster, meteorFall);
        }
    }

    @Override
    public void doBlockImpact(BaseEnergyShot shot, Vec3 hitPos, LivingEntity shooter) {
        WorldUtil.createExplosion(shooter, shot.level, shot, 1.4f);
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        CompoundTag nbt = shot.getPersistentData();
        float archMageMod = 1;
        archMageMod += 0.1f * nbt.getInt("archMageLevel");
        float totalMod = archMageMod * nbt.getFloat("extraDmgMod");
        if (DamageUtil.dealMagicDamage(shot, shooter, target, getDmg() * totalMod, true)) {
            WorldUtil.createExplosion(shooter, shot.level, shot, 1.4f);

            return true;
        }

        return false;
    }

    @Override
    public float getDmg() {
        return 28;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.BENEFICIAL, 1));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
