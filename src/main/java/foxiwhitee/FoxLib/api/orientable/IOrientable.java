package foxiwhitee.FoxLib.api.orientable;

import net.minecraftforge.common.util.ForgeDirection;

public interface IOrientable {
    boolean canBeRotated();

    ForgeDirection getForward();

    void setOrientation(ForgeDirection var1);
}
