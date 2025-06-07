package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.tslat.aoa3.event.AoAPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AoAPlayerEvents.class)
public class AoAPlayerEventsMixin {
    @ModifyArg(
            remap = false,
            method = "preInit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/eventbus/api/IEventBus;addListener(Lnet/minecraftforge/eventbus/api/EventPriority;ZLjava/lang/Class;Ljava/util/function/Consumer;)V",
                    ordinal = 14
            ),
            index = 0
    )
    private static EventPriority esir$modifyEventPriority(EventPriority priority) {
        return EventPriority.LOWEST;
    }

    @Inject(remap = false, method = "onBlockBreak", at = @At("HEAD"), cancellable = true)
    private static void esir$onBlockBreak(BlockEvent.BreakEvent ev, CallbackInfo ci) {
        if (ev.isCanceled()) {
            ci.cancel();
        }
    }
}
