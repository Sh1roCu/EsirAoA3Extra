package cn.sh1rocu.esiraoa3extra.item.weapon.greatblade;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.tslat.aoa3.library.constant.AttackSpeed;
import net.tslat.aoa3.util.LocaleUtil;
import net.tslat.aoa3.util.RandomUtil;

import javax.annotation.Nullable;
import java.util.List;

public class HauntedGreatblade extends BaseGreatblade {
    public HauntedGreatblade() {
        super(26, AttackSpeed.GREATBLADE, 1875);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide && isSelected && entity instanceof Player && RandomUtil.oneInNChance(12000)) {
            MobEffect effect = MobEffect.byId(random.nextInt(ForgeRegistries.POTIONS.getValues().size()));

            while (effect == null) {
                effect = MobEffect.byId(random.nextInt(ForgeRegistries.POTIONS.getValues().size()));
            }

            ((Player) entity).addEffect(new MobEffectInstance(effect, 600, 0, false, true));

            TranslatableComponent component = LocaleUtil.getLocaleMessage("item.esiraoa3extra.haunted_greatblade.message." + (1 + random.nextInt(16)), ChatFormatting.DARK_PURPLE);

            component.getStyle().withItalic(true);
            entity.sendMessage(component, Util.NIL_UUID);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(LocaleUtil.getFormattedItemDescriptionText(this, LocaleUtil.ItemDescriptionType.UNIQUE, 1));
    }
}
