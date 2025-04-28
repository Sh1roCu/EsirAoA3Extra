package cn.sh1rocu.esiraoa3extra.item.weapon.crossbow;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.tslat.aoa3.content.entity.projectile.arrow.CustomArrowEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

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
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    protected ItemStack findAmmo(ItemStack crossbowStack, LivingEntity player, boolean infiniteAmmo) {
        return player.getProjectile(crossbowStack);
    }

    protected boolean hasAmmo(LivingEntity user, ItemStack crossbowStack) {
        int multishot = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbowStack);
        boolean infiniteAmmo = user instanceof PlayerEntity && ((PlayerEntity) user).isCreative();
        ItemStack ammoStack = findAmmo(crossbowStack, user, infiniteAmmo);
        ItemStack ammoStackCopy = ammoStack.copy();

        for (int i = 0; i < 1 + multishot * 2; i++) {
            if (i > 0)
                ammoStack = ammoStackCopy.copy();

            if (ammoStack.isEmpty() && infiniteAmmo) {
                ammoStack = new ItemStack(Items.ARROW);
                ammoStackCopy = ammoStack.copy();
            }

            if (!chargeShot(user, crossbowStack, ammoStack, i > 0, infiniteAmmo))
                return false;
        }

        return true;
    }

    protected boolean chargeShot(LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack, boolean isMultishotProjectile, boolean infiniteAmmo) {
        if (ammoStack.isEmpty())
            return false;

        boolean canUseAmmo = infiniteAmmo && ammoStack.getItem() instanceof ArrowItem;
        ItemStack itemstack;

        if (!canUseAmmo && !infiniteAmmo && !isMultishotProjectile) {
            itemstack = ammoStack.split(1);

            if (ammoStack.isEmpty() && shooter instanceof PlayerEntity)
                ((PlayerEntity) shooter).inventory.removeItem(ammoStack);
        } else {
            itemstack = ammoStack.copy();
        }

        addChargedProjectile(crossbowStack, itemstack);

        return true;
    }

    protected void addChargedProjectile(ItemStack crossbow, ItemStack projectile) {
        CompoundNBT tag = crossbow.getOrCreateTag();
        ListNBT projectilesNbt;

        if (tag.contains("ChargedProjectiles", Constants.NBT.TAG_LIST)) {
            projectilesNbt = tag.getList("ChargedProjectiles", Constants.NBT.TAG_COMPOUND);
        } else {
            projectilesNbt = new ListNBT();
        }

        CompoundNBT projectileTag = new CompoundNBT();

        projectile.save(projectileTag);
        projectilesNbt.add(projectileTag);
        tag.put("ChargedProjectiles", projectilesNbt);
    }

    protected void fireProjectiles(LivingEntity shooter, Hand hand, ItemStack crossbowStack, float baseVelocity, float baseInaccuracy) {
        List<ItemStack> projectiles = getChargedProjectiles(crossbowStack);

        if (projectiles.isEmpty())
            return;

        float[] soundPitches = getRandomSoundPitches(shooter.getRandom(), projectiles.size());
        boolean creativeMode = shooter instanceof PlayerEntity && ((PlayerEntity) shooter).isCreative();
        float spreadModifier = -10f;

        fireProjectile(shooter, hand, crossbowStack, projectiles.get(0), soundPitches[0], creativeMode, baseVelocity, baseInaccuracy, 0);

        for (int i = 1; i < projectiles.size(); i++) {
            ItemStack projectile = projectiles.get(i);

            fireProjectile(shooter, hand, crossbowStack, projectile, soundPitches[i], creativeMode, baseVelocity, baseInaccuracy, spreadModifier);

            spreadModifier = spreadModifier < 0 ? spreadModifier * -1 : spreadModifier / -2f;
        }

        if (shooter instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) shooter;

            CriteriaTriggers.SHOT_CROSSBOW.trigger(player, crossbowStack);
            player.awardStat(Stats.ITEM_USED.get(crossbowStack.getItem()));
        }

        clearProjectiles(crossbowStack);
    }

    protected void fireProjectile(LivingEntity shooter, Hand hand, ItemStack crossbowStack, ItemStack projectileStack, float soundPitch, boolean isCreative, float velocity, float inaccuracy, float projectileAngle) {
        World world = shooter.getCommandSenderWorld();

        if (!world.isClientSide) {
            boolean isFirework = projectileStack.getItem() == Items.FIREWORK_ROCKET;
            ProjectileEntity projectile;

            if (isFirework) {
                projectile = new FireworkRocketEntity(world, projectileStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
            } else {
                projectile = createArrow(shooter, crossbowStack, projectileStack);

                if (isCreative || projectileAngle != 0.0F)
                    ((AbstractArrowEntity) projectile).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            }

            if (shooter instanceof ICrossbowUser) {
                ICrossbowUser crossbowUser = (ICrossbowUser) shooter;

                crossbowUser.shootCrossbowProjectile(crossbowUser.getTarget(), crossbowStack, projectile, projectileAngle);
            } else {
                Vector3d vecUp = shooter.getUpVector(1.0F);
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
            if (hand.equals(Hand.MAIN_HAND)) {
                float[] attribute = EsirUtil.getAttribute(crossbowStack);
                if (attribute[0] != -1) {
                    extraDmg = attribute[0];
                    amplifierLevel = (int) attribute[1];
                    starLevel = (int) attribute[2];
                }
            }
            float extraDmgMod = (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));

            CompoundNBT nbt = projectile.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            world.addFreshEntity(projectile);
            crossbowStack.hurtAndBreak(isFirework ? 3 : 1, shooter, (user) -> user.broadcastBreakEvent(hand));
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    protected CustomArrowEntity createArrow(LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack) {
        ArrowItem arrowItem = (ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
        CustomArrowEntity arrow = CustomArrowEntity.fromArrow(arrowItem.createArrow(shooter.level, ammoStack, shooter), this, shooter, getDamage());

        if (shooter instanceof PlayerEntity)
            arrow.setCritArrow(true);

        arrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        arrow.setShotFromCrossbow(true);
        int piercing = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbowStack);

        if (piercing > 0)
            arrow.setPierceLevel((byte) piercing);

        return arrow;
    }

    protected float getShotVelocity(ItemStack crossbowStack) {
        return crossbowStack.getItem() instanceof BaseCrossbow && containsChargedProjectile(crossbowStack, Items.FIREWORK_ROCKET) ? 1.6f : 3.15f;
    }

    protected float getCharge(ItemStack crossbowStack, int useTime) {
        return Math.min(useTime / (float) getChargeDuration(crossbowStack), 1);
    }

    protected List<ItemStack> getChargedProjectiles(ItemStack crossbowStack) {
        List<ItemStack> projectiles = Lists.newArrayList();
        CompoundNBT tag = crossbowStack.getTag();

        if (tag != null && tag.contains("ChargedProjectiles", Constants.NBT.TAG_LIST)) {
            ListNBT projectileNbt = tag.getList("ChargedProjectiles", Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < projectileNbt.size(); ++i) {
                projectiles.add(ItemStack.of(projectileNbt.getCompound(i)));
            }
        }

        return projectiles;
    }

    protected void clearProjectiles(ItemStack crossbowStack) {
        CompoundNBT tag = crossbowStack.getTag();

        if (tag != null) {
            ListNBT projectilesNbt = tag.getList("ChargedProjectiles", Constants.NBT.TAG_LIST);

            projectilesNbt.clear();
            tag.put("ChargedProjectiles", projectilesNbt);
        }
    }

    protected float[] getRandomSoundPitches(Random rand, int amount) {
        float[] pitches = new float[amount];

        for (int i = 0; i < amount; i++) {
            pitches[i] = 1 / (rand.nextFloat() * 0.5f + 1.8f) + (rand.nextBoolean() ? 0.63f : 0.43f);
        }

        return pitches;
    }

    protected SoundEvent getChargeSound(int quickCharge) {
        switch (quickCharge) {
            case 1:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3:
                return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default:
                return SoundEvents.CROSSBOW_LOADING_START;
        }
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return true;
    }

    public void doArrowMods(CustomArrowEntity arrow, Entity shooter, int useTicksRemaining) {
    }

    public void onEntityHit(CustomArrowEntity arrow, Entity target, Entity shooter, double damage, float drawStrength) {
    }

    public void onBlockHit(CustomArrowEntity arrow, BlockRayTraceResult rayTrace, Entity shooter) {
    }

    public void onArrowTick(CustomArrowEntity arrow, Entity shooter) {
    }

    public double getArrowDamage(CustomArrowEntity arrow, Entity target, double currentDamage, float drawStrength, boolean isCritical) {
        double damage = currentDamage * 0.5d * (drawStrength / 3f);

        if (isCritical)
            damage += damage + (damage * 0.1f * random.nextGaussian());

        return damage * arrow.getPersistentData().getFloat("extraDmgMod");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
