package foxiwhitee.FoxLib.integration.applied.api;

import appeng.api.networking.IGridHost;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface ITileMEServer extends IGridHost {
    int getClusterIndex(CraftingCPUCluster cluster);
    long getClusterStorageBytes(int clusterIndex);
    int getClusterAccelerator(int clusterIndex);
    NBTTagCompound getPreviousState(int index);
    void setPreviousState(int index, NBTTagCompound previousState);
    List<CraftingCPUCluster> getVirtualClusters();
}
