package cn.sh1rocu.esiraoa3extra.network.packet;

import cn.sh1rocu.esiraoa3extra.network.EsirBotNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LunarEventNotificationPacket {
    private final String NOTIFICATION;

    public LunarEventNotificationPacket(String notification) {
        this.NOTIFICATION = notification;
    }

    public static void encode(LunarEventNotificationPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.NOTIFICATION);
    }

    public static LunarEventNotificationPacket decode(FriendlyByteBuf buf) {
        return new LunarEventNotificationPacket(buf.readUtf());
    }

    public static void handle(LunarEventNotificationPacket packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> EsirBotNetwork.BUKKIT_PLUGIN_CHANNEL.sendToServer(new LunarEventNotificationPacket(packet.NOTIFICATION)));
        }
        ctx.get().setPacketHandled(true);
    }
}
