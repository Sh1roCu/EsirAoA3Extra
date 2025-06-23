package cn.sh1rocu.esiraoa3extra.mixin.compat.enhanced_celestials;

import cn.sh1rocu.esiraoa3extra.network.EsirBotNetwork;
import cn.sh1rocu.esiraoa3extra.network.packet.LunarEventNotificationPacket;
import com.llamalad7.mixinextras.sugar.Local;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import corgitaco.enhancedcelestials.api.lunarevent.LunarTextComponents;
import corgitaco.enhancedcelestials.network.packet.LunarEventChangedPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LunarEventChangedPacket.class)
public abstract class LunarEventChangedPacketMixin {
    @OnlyIn(Dist.CLIENT)
    @Inject(
            remap = false,
            method = "lambda$handle$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lcorgitaco/enhancedcelestials/LunarContext;setLastEvent(Lcorgitaco/enhancedcelestials/api/lunarevent/LunarEvent;)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void esir$sendEndNotificationToBukkit(LunarEventChangedPacket message, CallbackInfo ci, @Local LunarContext lunarContext) {
        LunarEvent lastEvent = lunarContext.getLastEvent();
        if (lastEvent != null) {
            LunarTextComponents.Notification endNotification = lastEvent.endNotification();
            if (endNotification != null)
                EsirBotNetwork.BUKKIT_PLUGIN_CHANNEL.sendToServer(new LunarEventNotificationPacket(endNotification.getCustomTranslationTextComponent().getString()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Inject(
            remap = false,
            method = "lambda$handle$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lcorgitaco/enhancedcelestials/LunarContext;setCurrentEvent(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void esir$sendStartNotificationToBukkit(LunarEventChangedPacket message, CallbackInfo ci, @Local LunarContext lunarContext) {
        LunarTextComponents.Notification startNotification = lunarContext.getCurrentEvent().startNotification();
        if (startNotification != null)
            EsirBotNetwork.BUKKIT_PLUGIN_CHANNEL.sendToServer(new LunarEventNotificationPacket(startNotification.getCustomTranslationTextComponent().getString()));
    }
}
