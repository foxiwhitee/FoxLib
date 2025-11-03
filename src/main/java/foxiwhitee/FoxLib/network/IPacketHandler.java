package foxiwhitee.FoxLib.network;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.entity.player.EntityPlayer;

public interface IPacketHandler {
    void onPacketData(IInfoPacket var1, FMLProxyPacket var2, EntityPlayer var3);
}
