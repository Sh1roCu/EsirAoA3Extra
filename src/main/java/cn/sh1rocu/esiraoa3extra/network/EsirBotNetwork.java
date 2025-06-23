package cn.sh1rocu.esiraoa3extra.network;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import cn.sh1rocu.esiraoa3extra.network.packet.LunarEventNotificationPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class EsirBotNetwork {
    private static final String VERSION = "1";
    private static int ID = 0;

    public static SimpleChannel BUKKIT_PLUGIN_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(EsirAoA3Extra.MODID, "plugin"),
            () -> VERSION,
            (v) -> true,
            (v) -> true);


    public static void init() {
        BUKKIT_PLUGIN_CHANNEL.registerMessage(
                ID++,
                LunarEventNotificationPacket.class,
                LunarEventNotificationPacket::encode,
                LunarEventNotificationPacket::decode,
                (packet, context) -> {
                }
        );
    }
}
