package foxiwhitee.FoxLib.utils.helpers;

import cpw.mods.fml.common.network.IGuiHandler;
import foxiwhitee.FoxLib.utils.handler.GuiHandlerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return GuiHandlerUtils.getContainer(ID, player, world, x, y, z);
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return GuiHandlerUtils.getGui(ID, player, world, x, y, z);
    }
}
