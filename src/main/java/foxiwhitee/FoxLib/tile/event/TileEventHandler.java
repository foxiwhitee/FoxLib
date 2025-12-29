package foxiwhitee.FoxLib.tile.event;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxLib.tile.FoxBaseTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class TileEventHandler {
    private final Method method;
    private FoxBaseTile tile;

    public TileEventHandler(Method method, FoxBaseTile tile) {
        this.method = method;
        this.tile = tile;
    }

    public TileEventHandler(Method method) {
        this(method, null);
    }

    public void tick(FoxBaseTile tile) {
        try {
            this.method.invoke(tile);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void tick() {
        tick(tile);
    }

    public TickRateModulation tickSpeed(FoxBaseTile tile) {
        try {
            return (TickRateModulation)this.method.invoke(tile);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public TickRateModulation tickSpeed()  {
        return tickSpeed(tile);
    }

    public void writeToNBT(FoxBaseTile tile, NBTTagCompound data) {
        try {
            this.method.invoke(tile, data);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void writeToNBT(NBTTagCompound data) {
        this.writeToNBT(tile, data);
    }

    public void readFromNBT(FoxBaseTile tile, NBTTagCompound data) {
        try {
            this.method.invoke(tile, data);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void readFromNBT(NBTTagCompound data) {
        readFromNBT(tile, data);
    }

    public void writeToStream(FoxBaseTile tile, ByteBuf data) {
        try {
            this.method.invoke(tile, data);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void writeToStream(ByteBuf data) {
        writeToStream(tile, data);
    }

    @SideOnly(Side.CLIENT)
    public boolean readFromStream(FoxBaseTile tile, ByteBuf data) {
        try {
            return (Boolean)this.method.invoke(tile, data);
        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean readFromStream(ByteBuf data) {
        return readFromStream(tile, data);
    }

    Method getMethod() {
        return method;
    }
}
