package cn.sh1rocu.esiraoa3extra.network;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class VersionCheckNetwork {
    public static SimpleChannel CHANNEL;
    private static final String VERSION = "1.5.1";

    public static void init() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(EsirAoA3Extra.MODID, "channel_check"),
                () -> VERSION,
                (v) -> true,    //mod只装在客户端时不检查服务端
                (v) -> v.equals(VERSION)); //服务端检查客户端版本
    }
}