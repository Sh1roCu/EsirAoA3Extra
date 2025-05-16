package cn.sh1rocu.esiraoa3extra.listener;

import cn.sh1rocu.esiraoa3extra.EsirAoA3Extra;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EsirAoA3Extra.MODID)
public class CommandHandler {
    @SubscribeEvent
    public static void onCmd(RegisterCommandsEvent event) {
        //尝试让数据包能用该命令来执行CustomNpcs的相关指令
        event.getDispatcher().register(Commands.literal("esirforce")
                .requires(source -> source.hasPermission(2)).then(Commands.argument("cmd", StringArgumentType.greedyString()).executes(
                        context -> event.getDispatcher().execute(StringArgumentType.getString(context, "cmd"), context.getSource())
                ).build()));
    }
}
