package cn.sh1rocu.esiraoa3extra.mixin;

import corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarMobSpawnInfo;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.MobSpawnInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(targets = "net.tslat.aoa3.common.registration.AoAEntitySpawns$SpawnPredicates")
public class AoAEntitySpawns$SpawnPredicatesMixin {
    @Unique
    private static void esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(IServerWorld world, EntityType<MobEntity> type, CallbackInfoReturnable<Boolean> cir) {
        LunarContext lunarContext = ((EnhancedCelestialsWorldData) world.getLevel().getLevel()).getLunarContext();
        if (lunarContext != null) {
            LunarMobSpawnInfo lunarSpawner = lunarContext.getCurrentEvent().getLunarSpawner();
            if (lunarSpawner != null) {
                MobSpawnInfo mobSpawnInfo = lunarSpawner.getSpawnInfo();
                if (mobSpawnInfo.getMobs(EntityClassification.MONSTER).stream().anyMatch(spawners -> spawners.type == type)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(remap = false, method = "lambda$static$1", at = @At(value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/IServerWorld;[Lnet/minecraft/util/RegistryKey;)Z"), cancellable = true)
    private static void useLunarSpawner$MONSTER(EntityType<MobEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand, CallbackInfoReturnable<Boolean> cir) {
        esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(world, type, cir);
    }

    @Inject(remap = false, method = "lambda$static$2", at = @At(value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/IServerWorld;[Lnet/minecraft/util/RegistryKey;)Z"), cancellable = true)
    private static void useLunarSpawner$WATER_MONSTER(EntityType<MobEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand, CallbackInfoReturnable<Boolean> cir) {
        esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(world, type, cir);
    }
}