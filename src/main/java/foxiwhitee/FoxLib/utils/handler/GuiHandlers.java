package foxiwhitee.FoxLib.utils.handler;

import net.minecraft.client.gui.GuiButton;

import java.util.HashMap;
import java.util.Map;

public final class GuiHandlers {
    private static final Map<Class<?>, Integer> handlers = new HashMap<>();
    private static int counter = 0;

    private GuiHandlers() {}

    public static void registerHandler(Class<?> clazz) {
        handlers.put(clazz, counter++);
    }

    public static int getHandler(Class<?> clazz) {
        return handlers.get(clazz);
    }

    public static boolean containsHandler(Class<?> clazz) {
        return handlers.containsKey(clazz);
    }
}
