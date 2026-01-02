package foxiwhitee.FoxLib.api.registries;

import foxiwhitee.FoxLib.network.BasePacket;

public interface IPacketRegister {
    int size();
    void register(Class<? extends BasePacket> packet);
    Class<? extends BasePacket> get(int idx);
}
