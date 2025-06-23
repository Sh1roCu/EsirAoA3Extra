package cn.sh1rocu.esiraoa3extra.mixin.compat.enhanced_celestials;

import cn.sh1rocu.esiraoa3extra.network.EsirBotNetwork;
import cn.sh1rocu.esiraoa3extra.network.packet.LunarEventNotificationPacket;
import com.llamalad7.mixinextras.sugar.Local;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarTextComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LunarContext.class)
public abstract class LunarContextMixin {
    @Inject(
            remap = false,
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    ordinal = 0
            )
    )
    private void esir$sendEndNotificationToBukkit(
            Level world,
            CallbackInfo ci,
            @Local(ordinal = 0, name = "players") List<ServerPlayer> players,
            @Local(ordinal = 0) LunarTextComponents.Notification endNotification
    ) {
        if (endNotification != null)
            for (ServerPlayer player : players) {
                if (player != null && !player.hasDisconnected()) {
                    EsirBotNetwork.BUKKIT_PLUGIN_CHANNEL.sendTo(
                            new LunarEventNotificationPacket(endNotification.getCustomTranslationTextComponent().getString()),
                            player.connection.getConnection(),
                            NetworkDirection.PLAY_TO_CLIENT);
                    break;
                }
            }
    }

    @Inject(
            remap = false,
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    ordinal = 1
            )
    )
    private void esir$sendStartNotificationToBukkit(
            Level world,
            CallbackInfo ci,
            @Local(ordinal = 0, name = "players") List<ServerPlayer> players,
            @Local(ordinal = 1) LunarTextComponents.Notification startNotification
    ) {
        if (startNotification != null)
            for (ServerPlayer player : players) {
                if (player != null && !player.hasDisconnected()) {
                    EsirBotNetwork.BUKKIT_PLUGIN_CHANNEL.sendTo(
                            new LunarEventNotificationPacket(startNotification.getCustomTranslationTextComponent().getString()),
                            player.connection.getConnection(),
                            NetworkDirection.PLAY_TO_CLIENT);
                    break;
                }
            }
    }
}
