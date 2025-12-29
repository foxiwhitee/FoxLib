package foxiwhitee.FoxLib.tile.event;

public enum TileEventType {
    TICK,
    TICK_SPEED,
    SERVER_NBT_READ,
    SERVER_NBT_WRITE,
    CLIENT_NBT_READ,
    CLIENT_NBT_WRITE;

    private TileEventType() {
    }
}
