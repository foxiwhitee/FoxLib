package foxiwhitee.FoxLib.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.lang.reflect.InvocationTargetException;

public class NetworkManager {
    public static NetworkManager instance;
    public final NetworkPackets packets;
    private final FMLEventChannel channel;
    private final String channelName;
    private final IPacketHandler clientPacketHandler;
    private final IPacketHandler serverPacketHandler;

    public NetworkManager(String channelName) {
        this.channelName = channelName;
        this.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
        this.channel.register(this);
        FMLCommonHandler.instance().bus().register(this);
        if (FMLCommonHandler.instance().getSide().isClient()) {
            this.clientPacketHandler = new ClientPacketProcessor();
        } else {
            this.clientPacketHandler = null;
        }
        this.serverPacketHandler = new ServerPacketProcessor();
        packets = new NetworkPackets();
    }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (serverPacketHandler != null) {
            NetHandlerPlayServer handler = (NetHandlerPlayServer) event.packet.handler();
            serverPacketHandler.onPacketData(null, event.packet, handler.playerEntity);
        }
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        if (clientPacketHandler != null) {
            clientPacketHandler.onPacketData(null, event.packet, null);
        }
    }

    public String getChannelName() {
        return channelName;
    }

    public void sendToAll(BasePacket packet) {
        channel.sendToAll(packet.createProxyPacket());
    }

    public void sendToPlayer(BasePacket packet, EntityPlayerMP player) {
        channel.sendTo(packet.createProxyPacket(), player);
    }

    public void sendToAllAround(BasePacket packet, NetworkRegistry.TargetPoint point) {
        channel.sendToAllAround(packet.createProxyPacket(), point);
    }

    public void sendToDimension(BasePacket packet, int dimensionId) {
        channel.sendToDimension(packet.createProxyPacket(), dimensionId);
    }

    public void sendToServer(BasePacket packet) {
        channel.sendToServer(packet.createProxyPacket());
    }

    public void sendToChunk(BasePacket packet, World world, int chunkX, int chunkZ) {
        PlayerManager manager = ((WorldServer) world).getPlayerManager();
        for (EntityPlayerMP player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            if (manager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
                sendToPlayer(packet, player);
            }
        }
    }

    private static class ServerPacketProcessor implements IPacketHandler {
        @Override
        public void onPacketData(IInfoPacket network, FMLProxyPacket packet, EntityPlayer player) {
            try {
                int packetId = packet.payload().readInt();
                BasePacket basePacket = NetworkManager.instance.packets.createInstance(packetId, packet.payload());
                basePacket.handleServerSide(network, basePacket, player);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | RuntimeException ignored) {
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static class ClientPacketProcessor implements IPacketHandler {
        @Override
        public void onPacketData(IInfoPacket network, FMLProxyPacket packet, EntityPlayer player) {
            try {
                int packetId = packet.payload().readInt();
                BasePacket basePacket = NetworkManager.instance.packets.createInstance(packetId, packet.payload());
                EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
                basePacket.handleClientSide(network, basePacket, clientPlayer);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | RuntimeException ignored) {
            }
        }
    }
}

