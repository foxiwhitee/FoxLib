package foxiwhitee.FoxLib.registries;

import foxiwhitee.FoxLib.api.registries.IPacketRegister;
import foxiwhitee.FoxLib.network.BasePacket;

import java.util.ArrayList;
import java.util.List;

public class PacketRegister implements IPacketRegister {
    private final List<Class<? extends BasePacket>> packetClasses = new ArrayList<>();

    PacketRegister() {}

    @Override
    public int size() {
        return packetClasses.size();
    }

    @Override
    public void register(Class<? extends BasePacket> packet) {
        if (packet != null && !packetClasses.contains(packet)) {
            packetClasses.add(packet);
        }
    }

    @Override
    public Class<? extends BasePacket> get(int idx) {
        return packetClasses.get(idx);
    }
}
