package foxiwhitee.FoxLib.mixins.ae2;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.crafting.CraftingLink;
import appeng.me.cache.CraftingGridCache;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import foxiwhitee.FoxLib.integration.applied.api.ITileMEServer;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingGridCacheAddition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(value = CraftingGridCache.class, remap = false)
public abstract class CraftingGridCacheMixin implements ICraftingGridCacheAddition {

    @Shadow(remap = false)
    @Final
    private Set<CraftingCPUCluster> craftingCPUClusters;

    @Override
    public Set<CraftingCPUCluster> getCraftingCPUClusters() {
        return craftingCPUClusters;
    }

}
