package foxiwhitee.FoxLib.tile.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TileEvent {
    TileEventType value();
}
