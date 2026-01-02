package foxiwhitee.FoxLib.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class NoTextureButton extends FoxBaseButton {

    public NoTextureButton(int buttonId, int x, int y, int u, int v, int width, int height, int textureSizeX, int textureSizeY, String[] names, String[] tooltips) {
        super(buttonId, x, y, u, v, width, height, textureSizeX, textureSizeY, names, tooltips);
    }

    public NoTextureButton(int buttonId, int x, int y, int u, int v, int width, int height, String[] names, String[] tooltips) {
        super(buttonId, x, y, u, v, width, height, names, tooltips);
    }

    public NoTextureButton(int buttonId, int x, int y, int u, int v, String[] names, String[] tooltips) {
        super(buttonId, x, y, u, v, names, tooltips);
    }

    public NoTextureButton(int buttonId, int x, int y, int u, int v, int width, int height, String names) {
        super(buttonId, x, y, u, v, width, height, names);
    }

    public NoTextureButton(int buttonId, int x, int y, int u, int v, String names) {
        super(buttonId, x, y, u, v, names);
    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {}
}
