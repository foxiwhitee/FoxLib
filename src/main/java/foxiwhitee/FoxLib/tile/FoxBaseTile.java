package foxiwhitee.FoxLib.tile;

import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventHandler;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.*;

public abstract class FoxBaseTile extends TileEntity implements IOrientable {
    private static final Map<Class<? extends FoxBaseTile>, Map<TileEventType, List<TileEventHandler>>> HANDLERS = new HashMap();
    private ForgeDirection forward;

    public FoxBaseTile() {
        this.forward = ForgeDirection.UNKNOWN;
    }

    public final void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        try {
            if (this.canBeRotated()) {
                this.forward = ForgeDirection.valueOf(data.getString("orientation_forward"));
            }
        } catch (IllegalArgumentException var4) {
        }

        for (TileEventHandler h : this.getHandlerListFor(TileEventType.SERVER_NBT_READ)) {
            h.readFromNBT(this, data);
        }
    }

    public final void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        if (this.canBeRotated()) {
            data.setString("orientation_forward", this.forward.name());
        }

        for (TileEventHandler h : this.getHandlerListFor(TileEventType.SERVER_NBT_WRITE)) {
            h.writeToNBT(this, data);
        }
    }

    public final void updateEntity() {
        for(TileEventHandler h : this.getHandlerListFor(TileEventType.TICK)) {
            h.tick(this);
        }
    }

    private final boolean readFromStream(ByteBuf data) {
        boolean output = false;

        try {
            if (this.canBeRotated()) {
                ForgeDirection old_Forward = this.forward;
                byte orientation = data.readByte();
                this.forward = ForgeDirection.getOrientation(orientation & 7);
                output = this.forward != old_Forward;
            }
            for(TileEventHandler h : this.getHandlerListFor(TileEventType.CLIENT_NBT_READ)) {
                if (h.readFromStream(this, data)) {
                    output = true;
                }
            }
        } catch (Throwable ignored) {
        }

        return output;
    }

    private final void writeToStream(ByteBuf data) {
        try {
            if (this.canBeRotated()) {
                byte orientation = (byte)(this.forward.ordinal());
                data.writeByte(orientation);
            }

            for(TileEventHandler h : this.getHandlerListFor(TileEventType.CLIENT_NBT_WRITE)) {
                h.writeToStream(this, data);
            }
        } catch (Throwable ignored) {
        }
    }

    public void markForUpdate() {
        if (this.worldObj != null) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
        }
    }

    private List<TileEventHandler> getHandlerListFor(TileEventType type) {
        Map<TileEventType, List<TileEventHandler>> eventToHandlers = this.getEventToHandlers();
        return this.getHandlers(eventToHandlers, type);
    }

    private Map<TileEventType, List<TileEventHandler>> getEventToHandlers() {
        Class<? extends FoxBaseTile> clazz = this.getClass();
        Map<TileEventType, List<TileEventHandler>> storedHandlers = (Map) HANDLERS.get(clazz);
        if (storedHandlers == null) {
            Map<TileEventType, List<TileEventHandler>> newStoredHandlers = new EnumMap(TileEventType.class);
            HANDLERS.put(clazz, newStoredHandlers);

            for (Method method : clazz.getMethods()) {
                TileEvent event = (TileEvent) method.getAnnotation(TileEvent.class);
                if (event != null) {
                    this.addHandler(newStoredHandlers, event.value(), method);
                }
            }

            return newStoredHandlers;
        } else {
            return storedHandlers;
        }
    }

    private List<TileEventHandler> getHandlers(Map<TileEventType, List<TileEventHandler>> eventToHandlers, TileEventType event) {
        List<TileEventHandler> oldHandlers = eventToHandlers.get(event);
        if (oldHandlers == null) {
            List<TileEventHandler> newHandlers = new LinkedList<>();
            eventToHandlers.put(event, newHandlers);
            return newHandlers;
        } else {
            return oldHandlers;
        }
    }

    private void addHandler(Map<TileEventType, List<TileEventHandler>> handlerSet, TileEventType value, Method m) {
        List<TileEventHandler> list = handlerSet.computeIfAbsent(value, (k) -> new ArrayList<>());
        list.add(new TileEventHandler(m));
    }

    public boolean canBeRotated() {
        return true;
    }

    public ForgeDirection getForward() {
        return this.forward;
    }

    @Override
    public void setOrientation(ForgeDirection var1) {
        this.forward = var1;
        this.markForUpdate();
        if (worldObj.blockExists(this.xCoord, this.yCoord, this.zCoord)) {
            worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
        }
    }
}
