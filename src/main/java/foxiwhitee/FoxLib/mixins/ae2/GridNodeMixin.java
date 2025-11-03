package foxiwhitee.FoxLib.mixins.ae2;

import appeng.me.GridNode;
import foxiwhitee.FoxLib.integration.applied.api.channels.ICustomChannelCount;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = GridNode.class, remap = false)
public abstract class GridNodeMixin {

    @Shadow(remap = false)
    private int compressedData;
    @Final
    @Shadow(remap = false)
    private static int[] CHANNEL_COUNT;


    /**
     * @author
     * @reason
     */
    @Overwrite
    public int getMaxChannels() {
        int original = CHANNEL_COUNT[this.compressedData & 3];
        if (((GridNode)(Object)this).getGridBlock().getMachine() instanceof ICustomChannelCount)
            return ((ICustomChannelCount)((GridNode)(Object)this).getGridBlock().getMachine()).getMaxChannelSize();
        return original;
    }
}
