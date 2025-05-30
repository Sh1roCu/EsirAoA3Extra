package cn.sh1rocu.esiraoa3extra.mixin.compat.aoa3;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.tslat.aoa3.common.registration.AoAEntitySpawns$SpawnPredicates")
public class AoAEntitySpawns$SpawnPredicatesMixin {
    @Unique
    private static boolean esirAoA3Extra$isMiningWorld(ServerLevelAccessor world) {
        ResourceLocation resourceLocation = world.getLevel().dimension().location();
        return "minecraft".equals(resourceLocation.getNamespace()) && "mining".equals(resourceLocation.getPath());
    }

    @ModifyExpressionValue(remap = false, method = "lambda$static$1", at = @At(remap = true, value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/level/ServerLevelAccessor;[Lnet/minecraft/resources/ResourceKey;)Z"))
    private static boolean canSpawnInMining4MONSTER(boolean origin, @Local(argsOnly = true) ServerLevelAccessor world) {
        return esirAoA3Extra$isMiningWorld(world);
    }

    @ModifyExpressionValue(remap = false, method = "lambda$static$2", at = @At(remap = true, value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/level/ServerLevelAccessor;[Lnet/minecraft/resources/ResourceKey;)Z"))
    private static boolean canSpawnInMining4WATER_MONSTER(boolean origin, @Local(argsOnly = true) ServerLevelAccessor world) {
        return esirAoA3Extra$isMiningWorld(world);

    }
}