package cn.sh1rocu.esiraoa3extra.item.weapon.crossbow;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Constants;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;

import java.util.List;

public class BaseCrossbow extends net.tslat.aoa3.content.item.weapon.crossbow.BaseCrossbow {
    protected double damage;

    public BaseCrossbow(double damage, int durability) {
        super(damage, durability);

        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    protected void fireProjectile(LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack projectileStack, float soundPitch, boolean isCreative, float velocity, float inaccuracy, float projectileAngle) {
        Level world = shooter.getCommandSenderWorld();

        if (!world.isClientSide) {
            boolean isFirework = projectileStack.getItem() == Items.FIREWORK_ROCKET;
            Projectile projectile;

            if (isFirework) {
                projectile = new FireworkRocketEntity(world, projectileStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
            } else {
                projectile = createArrow(shooter, crossbowStack, projectileStack);

                if (isCreative || projectileAngle != 0.0F)
                    ((AbstractArrow) projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            if (shooter instanceof CrossbowAttackMob) {
                CrossbowAttackMob crossbowUser = (CrossbowAttackMob) shooter;

                crossbowUser.shootCrossbowProjectile(crossbowUser.getTarget(), crossbowStack, projectile, projectileAngle);
            } else {
                Vec3 vecUp = shooter.getUpVector(1.0F);
                Quaternion angle = new Quaternion(new Vector3f(vecUp), projectileAngle, true);
                Vector3f lookVec = new Vector3f(shooter.getViewVector(1.0F));

                lookVec.transform(angle);
                projectile.shoot(lookVec.x(), lookVec.y(), lookVec.z(), velocity, inaccuracy);
            }

            if (projectile instanceof CustomArrowEntity)
                doArrowMods((CustomArrowEntity) projectile, shooter, 0);

            float extraDmg = 0;
            float amplifierLevel = 0;
            float starLevel = 0;
            if (hand.equals(InteractionHand.MAIN_HAND)) {
                float[] attribute = EsirUtil.getAttribute(crossbowStack);
                if (attribute[0] != -1) {
                    extraDmg = attribute[0];
                    amplifierLevel = (int) attribute[1];
                    starLevel = (int) attribute[2];
                }
            }
            float extraDmgMod = (1 + extraDmg) * (1 + (0.04f * (amplifierLevel + (12.5F * starLevel))));

            CompoundTag nbt = projectile.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            world.addFreshEntity(projectile);
            crossbowStack.hurtAndBreak(isFirework ? 3 : 1, shooter, (user) -> user.broadcastBreakEvent(hand));
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
        }
    }

    protected List<ItemStack> getChargedProjectiles(ItemStack crossbowStack) {
        List<ItemStack> projectiles = Lists.newArrayList();
        CompoundTag tag = crossbowStack.getTag();

        if (tag != null && tag.contains("ChargedProjectiles", Constants.NBT.TAG_LIST)) {
            ListTag projectileNbt = tag.getList("ChargedProjectiles", Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < projectileNbt.size(); ++i) {
                projectiles.add(ItemStack.of(projectileNbt.getCompound(i)));
            }
        }

        return projectiles;
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public double getArrowDamage(CustomArrowEntity arrow, Entity target, double currentDamage, float drawStrength, boolean isCritical) {
        double damage = currentDamage * 0.5d * (drawStrength / 3f);

        if (isCritical)
            damage += damage + (damage * 0.1f * random.nextGaussian());

        return damage * arrow.getPersistentData().getFloat("extraDmgMod");
    }
}
