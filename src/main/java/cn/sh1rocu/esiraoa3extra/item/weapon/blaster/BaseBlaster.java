package cn.sh1rocu.esiraoa3extra.item.weapon.blaster;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.tslat.aoa3.common.registration.AoAEnchantments;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.common.registration.custom.AoAResources;
import net.tslat.aoa3.content.entity.projectile.staff.BaseEnergyShot;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.DamageUtil;
import net.tslat.aoa3.util.ItemUtil;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.NumberUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseBlaster extends net.tslat.aoa3.content.item.weapon.blaster.BaseBlaster {
    private final Multimap<Attribute, AttributeModifier> attributeModifiers = HashMultimap.create();

    protected final double baseDmg;
    protected final int firingDelay;
    protected final float energyCost;

    public BaseBlaster(Item.Properties properties, final double dmg, final int fireDelayTicks, final float energyCost) {
        super(properties, dmg, fireDelayTicks, energyCost);
        this.baseDmg = dmg;
        this.firingDelay = fireDelayTicks;
        this.energyCost = energyCost;

        attributeModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", AttackSpeed.forAttacksPerSecond(1.2f), AttributeModifier.Operation.ADDITION));
    }

    public BaseBlaster(final double dmg, final int durability, final int fireDelayTicks, final float energyCost) {
        this(new Item.Properties().tab(AoAItemGroups.BLASTERS).durability(durability), dmg, fireDelayTicks, energyCost);
    }

    public double getDamage() {
        return baseDmg;
    }

    public int getFiringDelay() {
        return firingDelay;
    }

    public float getEnergyCost() {
        return energyCost;
    }

    @Nullable
    public SoundEvent getFiringSound() {
        return null;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairMaterial) {
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity player, int useTicksRemaining) {
        ItemUtil.damageItem(stack, player, (72000 - useTicksRemaining - 1) / firingDelay, EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.BRACE.get(), stack) > 0 ? EquipmentSlotType.OFFHAND : EquipmentSlotType.MAINHAND);
    }

    public abstract void fire(ItemStack blaster, LivingEntity shooter);

    public void createEnergyShot(ItemStack blaster, LivingEntity shooter, BaseEnergyShot... shots) {
        float extraDmg = 0;
        float amplifierLevel = 0;
        float starLevel = 0;
        if (getWeaponHand(shooter).equals(Hand.MAIN_HAND)) {
            float[] attribute = EsirUtil.getAttribute(blaster);
            if (attribute[0] != -1) {
                extraDmg = attribute[0];
                amplifierLevel = (int) attribute[1];
                starLevel = (int) attribute[2];
            }
        }
        float extraDmgMod = (1 + extraDmg) * (1 + (0.05f * (amplifierLevel + (10 * starLevel))));

        CompoundNBT nbt;
        for (BaseEnergyShot shot : shots) {
            nbt = shot.getPersistentData();
            nbt.putFloat("extraDmgMod", extraDmgMod);
            shooter.level.addFreshEntity(shot);
        }
    }

    public boolean consumeEnergy(ServerPlayerDataManager plData, ItemStack stack, float cost) {
        return plData.getResource(AoAResources.SPIRIT.get()).consume(cost, false);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.getOrCreateTag().putInt("HideFlags", ItemStack.TooltipDisplayFlags.MODIFIERS.getMask());
        return null;
    }

    @Override
    public Hand getWeaponHand(LivingEntity holder) {
        return Hand.MAIN_HAND;
    }

    @Override
    public void doBlockImpact(BaseEnergyShot shot, Vector3d hitPos, LivingEntity shooter) {
    }

    @Override
    public boolean doEntityImpact(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
        CompoundNBT nbt = shot.getPersistentData();
        float extraDmgMod = nbt.getFloat("extraDmgMod");
        if (DamageUtil.dealBlasterDamage(shooter, target, shot, (float) baseDmg * extraDmgMod, false)) {
            doImpactEffect(shot, target, shooter);

            return true;
        }

        return false;
    }

    protected void doImpactEffect(BaseEnergyShot shot, Entity target, LivingEntity shooter) {
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        if (slot == EquipmentSlotType.MAINHAND)
            return attributeModifiers;

        return super.getAttributeModifiers(slot, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (getDamage() > 0)
            tooltip.add(1, LocaleUtil.getLocaleMessage("items.description.damage.blaster", TextFormatting.DARK_RED, new StringTextComponent(NumberUtil.roundToNthDecimalPlace((float) getDamage(), 1))));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.blaster.fire", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText("items.description.blaster.effect", LocaleUtil.ItemDescriptionType.ITEM_TYPE_INFO));
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.FIRING_SPEED, LocaleUtil.ItemDescriptionType.NEUTRAL, new StringTextComponent(NumberUtil.roundToNthDecimalPlace(20 / (float) getFiringDelay(), 2))));

        float energyConsumption = (1 + (0.3f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.GREED.get(), stack))) * getEnergyCost() * Math.max(0, (1 - 0.15f * EnchantmentHelper.getItemEnchantmentLevel(AoAEnchantments.RECHARGE.get(), stack)));

        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(LocaleUtil.Constants.AMMO_RESOURCE, LocaleUtil.ItemDescriptionType.ITEM_AMMO_COST, new StringTextComponent(NumberUtil.roundToNthDecimalPlace(energyConsumption, 2)), AoAResources.SPIRIT.get().getName()));
    }
}
