package foxiwhitee.FoxLib.mixins.ae2;

import appeng.api.util.WorldCoord;
import appeng.me.cluster.IAECluster;
import appeng.me.cluster.implementations.CraftingCPUCalculator;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.crafting.TileCraftingTile;
import com.llamalad7.mixinextras.sugar.Local;
import foxiwhitee.FoxLib.integration.applied.api.ITileMEServer;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingCPUClusterAccessor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CraftingCPUCalculator.class, remap = false)
public abstract class CraftingCPUCalculatorMixin {

    @Inject(method = "updateTiles",
        at = @At(value = "INVOKE",
            target = "Lappeng/tile/crafting/TileCraftingTile;updateStatus(Lappeng/me/cluster/implementations/CraftingCPUCluster;)V"),
        cancellable = true
    )
    private void beforeUpdateStatus(IAECluster cl, World w, WorldCoord min, WorldCoord max,
                                    CallbackInfo ci,
                                    @Local(name = "te") TileCraftingTile te,
                                    @Local(name = "c") CraftingCPUCluster c) {

        if (te instanceof ITileMEServer) {
            ((ICraftingCPUClusterAccessor)(Object)c).doneMEServer();
            ci.cancel();
        }
    }
}
