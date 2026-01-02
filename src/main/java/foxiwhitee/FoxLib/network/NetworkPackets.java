package foxiwhitee.FoxLib.network;

import foxiwhitee.FoxLib.api.FoxLibApi;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkPackets {
    private final List<Class<? extends BasePacket>> packetClasses = new ArrayList<>();
    private final List<Constructor<? extends BasePacket>> packetConstructors = new ArrayList<>();

    NetworkPackets() {
        for (int i = 0; i < FoxLibApi.instance.registries().registerPacket().size(); i++) {
            Class<? extends BasePacket> pktClass =  FoxLibApi.instance.registries().registerPacket().get(i);
            packetClasses.add(pktClass);

            Constructor<? extends BasePacket> constructor = null;

            try {
                constructor = pktClass.getConstructor(ByteBuf.class);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new IllegalStateException("Packet class " + pktClass.getName() + " must have a ByteBuf constructor", e);
            }

            packetConstructors.add(constructor);
        }
    }

    public int fromClass(Class<? extends BasePacket> packetClass) {
        for (int i = 0; i < packetClasses.size(); i++) {
            if (packetClasses.get(i).equals(packetClass)) {
                return i;
            }
        }
        return -1;
    }

    public BasePacket createInstance(int packetId, ByteBuf buffer) throws InstantiationException, IllegalAccessException, InvocationTargetException, NullPointerException {
        return packetConstructors.get(packetId).newInstance(buffer);
    }
}
