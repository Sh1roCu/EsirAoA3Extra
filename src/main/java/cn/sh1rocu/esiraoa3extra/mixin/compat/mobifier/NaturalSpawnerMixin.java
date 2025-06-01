package cn.sh1rocu.esiraoa3extra.mixin.compat.mobifier;

import corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import corgitaco.enhancedcelestials.LunarContext;
import corgitaco.enhancedcelestials.api.lunarevent.LunarMobSpawnInfo;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NaturalSpawner.SpawnState.class)
public class NaturalSpawnerMixin {
    @Inject(method = "afterSpawn", at = @At("HEAD"))
    private void esir$afterSpawn(Mob mob, ChunkAccess chunk, CallbackInfo ci) {
        if (!mob.level.isClientSide()) {
            LunarContext lunarContext = ((EnhancedCelestialsWorldData) mob.level).getLunarContext();
            if (lunarContext != null) {
                LunarMobSpawnInfo lunarSpawner = lunarContext.getCurrentEvent().getLunarSpawner();
                if (lunarSpawner != null && lunarSpawner.getSpawnInfo().getMobs(mob.getType().getCategory()).stream().anyMatch(spawnerData -> spawnerData.type == mob.getType())) {
                    mob.setHealth(mob.getMaxHealth());
                }
            }
        }
    }
}
