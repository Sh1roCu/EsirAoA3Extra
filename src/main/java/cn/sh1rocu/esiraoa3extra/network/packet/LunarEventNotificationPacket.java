package cn.sh1rocu.esiraoa3extra.network.packet;

import net.minecraft.network.FriendlyByteBuf;

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
}
