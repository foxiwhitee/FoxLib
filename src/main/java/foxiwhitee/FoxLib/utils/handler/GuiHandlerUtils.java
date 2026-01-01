package foxiwhitee.FoxLib.utils.handler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;

public class GuiHandlerUtils {

    public static Container getContainer(int index, EntityPlayer player, World world, int x, int y, int z) {
        SimpleGuiHandler handler = GuiHandlerRegistry.getGuiHandler(index);
        if (handler == null) return null;

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null || !handler.tile().isInstance(tile)) return null;

        try {
            Class<? extends Container> containerClass = handler.container();
            Constructor<?> constructor = findContainerConstructor(containerClass, EntityPlayer.class, tile.getClass());

            if (constructor != null) {
                return (Container) constructor.newInstance(player, tile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GuiContainer getGui(int index, EntityPlayer player, World world, int x, int y, int z) {
        SimpleGuiHandler handler = GuiHandlerRegistry.getGuiHandler(index);
        if (handler == null) return null;

        Container container = getContainer(index, player, world, x, y, z);
        if (container == null) return null;

        try {
            Class<? extends GuiContainer> guiClass = handler.gui();
            Constructor<?> guiConstructor = findGuiConstructor(guiClass, container.getClass());

            if (guiConstructor != null) {
                return (GuiContainer) guiConstructor.newInstance(container);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Constructor<?> findContainerConstructor(Class<?> clazz, Class<?> playerClass, Class<?> tileClass) {
        for (Constructor<?> c : clazz.getConstructors()) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 2 &&
                params[0].isAssignableFrom(playerClass) &&
                params[1].isAssignableFrom(tileClass)) {
                return c;
            }
        }
        return null;
    }

    private static Constructor<?> findGuiConstructor(Class<?> guiClass, Class<?> containerClass) {
        for (Constructor<?> c : guiClass.getConstructors()) {
            Class<?>[] params = c.getParameterTypes();
            if (params.length == 1 && params[0].isAssignableFrom(containerClass)) {
                return c;
            }
        }
        return null;
    }
}
