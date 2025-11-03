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

    @Shadow
    @Final
    private IGrid grid;

    @Override
    public Set<CraftingCPUCluster> getCraftingCPUClusters() {
        return craftingCPUClusters;
    }

    @Inject(method = "updateCPUClusters", at = @At("TAIL"))
    private void onAddTileEnd(CallbackInfo ci) {
        for(IGridNode cst : grid.getMachines(ITileMEServer.class)) {
            ITileMEServer tile = (ITileMEServer)cst.getMachine();
            for (int i = 0; i < tile.getVirtualClusters().size(); i++) {
                CraftingCPUCluster cluster = (CraftingCPUCluster)tile.getVirtualClusters().get(i);
                if (cluster != null) {
                    ((ICraftingGridCacheAddition) (Object) this).getCraftingCPUClusters().add(cluster);
                    if (cluster.getLastCraftingLink() != null) {
                        ((CraftingGridCache)((Object) this)).addLink((CraftingLink)cluster.getLastCraftingLink());
                    }
                }
            }
        }
    }
}
