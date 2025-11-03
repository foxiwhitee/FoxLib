package foxiwhitee.FoxLib.integration.applied.api.crafting;

import appeng.api.networking.crafting.ICraftingMedium;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import net.minecraft.inventory.InventoryCrafting;

public interface IPreCraftingMedium extends ICraftingMedium {
    default boolean pushPattern(ICraftingPatternDetails details, InventoryCrafting ic, CraftingCPUCluster cluster) {
        return this.pushPattern(details, ic);
    }
}
