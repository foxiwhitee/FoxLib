package foxiwhitee.FoxLib.api.orientable;

import net.minecraftforge.common.util.ForgeDirection;

public interface IOrientable {
    boolean canBeRotated();

    ForgeDirection getForward();

    ForgeDirection getUp();

    void setOrientation(ForgeDirection var1, ForgeDirection var2);
}
