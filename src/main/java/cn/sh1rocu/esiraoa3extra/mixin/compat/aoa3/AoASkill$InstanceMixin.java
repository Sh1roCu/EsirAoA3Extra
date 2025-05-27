package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import cn.sh1rocu.esiraoa3extra.util.EsirUtil;
import net.minecraft.nbt.CompoundTag;
import net.tslat.aoa3.player.skill.AoASkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AoASkill.Instance.class)
public abstract class AoASkill$InstanceMixin {
    @Inject(remap = false, method = "addCycle", at = @At(value = "RETURN", ordinal = 0))
    private void esir$modifyAttribute(CallbackInfoReturnable<Boolean> cir) {
        EsirUtil.applyAoASkillBuff((AoASkill.Instance) (Object) this);
    }

    @Inject(remap = false, method = "setCycle", at = @At("TAIL"))
    private void esir$modifyAttribute(int cycle, CallbackInfo ci) {
        EsirUtil.applyAoASkillBuff((AoASkill.Instance) (Object) this);
    }

    @Inject(remap = false, method = "loadFromNbt", at = @At("TAIL"))
    private void esir$modifyAttribute(CompoundTag skillData, CallbackInfo ci) {
        EsirUtil.applyAoASkillBuff((AoASkill.Instance) (Object) this);
    }
}
