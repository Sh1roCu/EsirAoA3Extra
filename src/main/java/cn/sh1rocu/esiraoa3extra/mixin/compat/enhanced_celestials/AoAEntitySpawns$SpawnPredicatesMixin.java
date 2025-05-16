package cn.sh1rocu.esiraoa3extra.mixin.compat.enhanced_celestials;

import corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarMobSpawnInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(targets = "net.tslat.aoa3.common.registration.AoAEntitySpawns$SpawnPredicates")
public class AoAEntitySpawns$SpawnPredicatesMixin {
    @Unique
    private static void esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(ServerLevelAccessor world, EntityType<Mob> type, CallbackInfoReturnable<Boolean> cir) {
        LunarContext lunarContext = ((EnhancedCelestialsWorldData) world.getLevel().getLevel()).getLunarContext();
        if (lunarContext != null) {
            LunarMobSpawnInfo lunarSpawner = lunarContext.getCurrentEvent().getLunarSpawner();
            if (lunarSpawner != null) {
                MobSpawnSettings mobSpawnInfo = lunarSpawner.getSpawnInfo();
                if (mobSpawnInfo.getMobs(MobCategory.MONSTER).stream().anyMatch(spawners -> spawners.type == type)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(remap = false, method = "lambda$static$1", at = @At(remap = true, value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/level/ServerLevelAccessor;[Lnet/minecraft/resources/ResourceKey;)Z"), cancellable = true)
    private static void useLunarSpawner$MONSTER(EntityType<Mob> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand, CallbackInfoReturnable<Boolean> cir) {
        esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(world, type, cir);
    }

    @Inject(remap = false, method = "lambda$static$2", at = @At(remap = true, value = "INVOKE", target = "Lnet/tslat/aoa3/util/WorldUtil;isWorld(Lnet/minecraft/world/level/ServerLevelAccessor;[Lnet/minecraft/resources/ResourceKey;)Z"), cancellable = true)
    private static void useLunarSpawner$WATER_MONSTER(EntityType<Mob> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand, CallbackInfoReturnable<Boolean> cir) {
        esiraoa3extra$aoaMonsterCanSpawnInLunarEvent(world, type, cir);
    }
}