package foxiwhitee.FoxLib.tile.event;

import java.lang.reflect.Method;

public class TickableFunction {
    private final TileEventHandler tileEventHandler;
    int currentInterval;
    int timer;
    boolean isSleeping;
    String triggerKey;

    public TickableFunction(TileEventHandler handler) {
        this.tileEventHandler = handler;
        this.currentInterval = 20;
        this.triggerKey = handler.getMethod().getName();
    }

    public TickRateModulation invoke() {
        return tileEventHandler.tickSpeed();
    }
}
