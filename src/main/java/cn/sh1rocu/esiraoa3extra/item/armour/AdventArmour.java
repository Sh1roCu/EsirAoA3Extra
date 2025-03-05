package cn.sh1rocu.esiraoa3extra.item.armour;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.*;
import net.tslat.aoa3.common.registration.AoAItemGroups;
import net.tslat.aoa3.player.ServerPlayerDataManager;
import net.tslat.aoa3.util.LocaleUtil;

import javax.annotation.Nullable;
import java.util.HashSet;

public abstract class AdventArmour extends net.tslat.aoa3.content.item.armour.AdventArmour {

    public static final String HELMET_MODIFIER_UUID = "dd109257-9a75-463a-85b5-45176d64c7f4";
    public static final String CHESTPLATE_MODIFIER_UUID = "764ed831-1f73-4c1c-84aa-7ba7224b768c";
    public static final String LEGS_MODIFIER_UUID = "74f00914-1e93-4f8a-9ed0-0a12e098671e";
    public static final String BOOTS_MODIFIER_UUID = "9261dfbe-1ee3-4789-94c8-8500a2bc6960";

    public AdventArmour(IArmorMaterial material, EquipmentSlotType slot) {
        this(material, slot, Rarity.COMMON);
    }

    public AdventArmour(IArmorMaterial material, EquipmentSlotType slot, Rarity rarity) {
        this(material, slot, new Item.Properties().tab(AoAItemGroups.ARMOUR).durability(material.getDurabilityForSlot(slot)).rarity(rarity));
    }

    public AdventArmour(IArmorMaterial material, EquipmentSlotType slot, Item.Properties properties) {
        super(material, slot, properties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return null;
    }

    public abstract Type setType();

    /**
     * Called when the player equips the item or has it placed on their body by a third party method.
     *
     * @param plData the {@code PlayerDataManager} container for the player wearing the armour.
     * @param slot   the {@code EquipmentSlotType} slot that the item was equipped into. A null slot in this context is used to indicate that the full set was equipped.<br>
     *               For example, if a player has the boots, legs, and chestplate of a set equipped, and equips the helmet, this event will be fired twice. Once with the head slot, once with a null slot for completing the set.
     */
    public void onEquip(ServerPlayerDataManager plData, @Nullable EquipmentSlotType slot) {
    }

    /**
     * Called when the player unequips the item or has it removed from their body by a third party method.
     *
     * @param plData the {@code PlayerDataManager} container for the player wearing the armour.
     * @param slot   the {@code EquipmentSlotType} slot that the item was unequipped from. A null slot in this context is used to indicate that the full set status was broken.<br>
     *               For example, if a player has the boots, legs, chestplate, and helmet equipped, then unequips the helmet, this event will be fired twice. Once with the head slot, once with a null slot for breaking the full set.
     */
    public void onUnequip(ServerPlayerDataManager plData, @Nullable EquipmentSlotType slot) {
    }

    /**
     * Called each tick during the player's tick cycle for each piece equipped. A null slot indicates that the armour is being ticked for the set's effect.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     */
    public void onEffectTick(ServerPlayerDataManager plData, @Nullable HashSet<EquipmentSlotType> slots) {
    }

    /**
     * Called when the player successfully lands an attack on another entity via any method. This method is called prior the final damage being dealt to the entity.<br>
     * Use to augment the final damage output, or apply/activate any additional effects for damaging an entity.<br>
     * At this stage, the attack is considered to be successful, with only the final damage amount to be confirmed.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingHurtEvent} that was thrown as part of this attack
     */
    public void onDamageDealt(ServerPlayerDataManager
                                      plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
    }

    /**
     * Called as soon as the player receives an incoming attack. At this stage, the attack isn't considered confirmed, and may still be cancelled or interrupted.<br>
     * This method is called at the beginning stages of the attack, and should only be used for monitoring purposes, or if you intend to cancel the event outright.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingAttackEvent} that was thrown as part of this attack
     */
    public void onPreAttackReceived(ServerPlayerDataManager
                                            plData, @Nullable HashSet<EquipmentSlotType> slots, LivingAttackEvent event) {
    }

    /**
     * Called when an attack is successfully landed on the player. At this stage, the attack is considered final and confirmed, with only the final damage to be confirmed.<br>
     * Use this method if you intend to modify the amount of damage to be received by the player. Triggering effects caused by receiving an attack should be done in onPostAttackReceived.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingHurtEvent} that was thrown as part of this attack
     */
    public void onAttackReceived(ServerPlayerDataManager
                                         plData, @Nullable HashSet<EquipmentSlotType> slots, LivingHurtEvent event) {
    }

    /**
     * Called when an attack is considered successful. Last-second damage modifications can still be made however. Armour damage, enchantments, and potion effect calculations have already taken place.<br>
     * Use this method to trigger effects that are to happen when the player receives an attack.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingDamageEvent} that was thrown as part of this attack
     */
    public void onPostAttackReceived(ServerPlayerDataManager
                                             plData, @Nullable HashSet<EquipmentSlotType> slots, LivingDamageEvent event) {
    }

    /**
     * Called when the player lands from a fall of any height. A fall may not necessarily imply damage being dealt, just that the player has landed.<br>
     * This event can be used to modify fall damage, or trigger effects from falling.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingFallEvent} that was thrown as part of this attack
     */
    public void onPlayerLandingFall(ServerPlayerDataManager
                                            plData, @Nullable HashSet<EquipmentSlotType> slots, LivingFallEvent event) {
    }

    /**
     * Called when the player dies with one or more pieces of Advent Armour equipped.<br>
     * This event is not cancellable and should only be used for triggering effects or for any additional tasks to be completed at the time of death.
     *
     * @param plData the {@code PlayerDataManager} container for the player that the armour is worn by.
     * @param slots  the collection of {@code EquipmentSlotType}s that the player is wearing of this type. A null parameter in this context represents the method being called for set-effects.<br>
     *               For example, if a player is wearing boots and legs from the same set, this method will be called with a 4-element HashSet containing FEET, LEGS, BODY, HEAD. It will then be called again with null slots, representing the set-effect call.
     * @param event  the {@code LivingDeathEvent} that was thrown as part of this attack
     */
    public void onPlayerDeath(ServerPlayerDataManager
                                      plData, @Nullable HashSet<EquipmentSlotType> slots, LivingDeathEvent event) {
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairMaterial) {
        return false;
    }

    public boolean isSetHelmet(AdventArmour helmet) {
        return this.setType() == helmet.setType() || helmet.setType() == Type.ALL;
    }

    protected TranslationTextComponent setEffectHeader() {
        return LocaleUtil.getLocaleMessage(LocaleUtil.Constants.ARMOUR_SET_HEADER, TextFormatting.GOLD);
    }

    protected TranslationTextComponent pieceEffectHeader() {
        return LocaleUtil.getLocaleMessage(LocaleUtil.Constants.ARMOUR_PIECE_HEADER, TextFormatting.GRAY);
    }

    protected TranslationTextComponent anySetEffectHeader() {
        return LocaleUtil.getLocaleMessage(LocaleUtil.Constants.ARMOUR_ANY_SET_HEADER, TextFormatting.DARK_AQUA);
    }

    public boolean isHelmetAirTight(ServerPlayerEntity player) {
        return false;
    }
}