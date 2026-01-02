package foxiwhitee.FoxLib.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class NoTextureButton extends FoxBaseButton {

    public NoTextureButton(int buttonId, int x, int y, int width, int height, String[] names, String[] tooltips) {
        super(buttonId, x, y, 0, 0, width, height, names, tooltips);
    }

    public NoTextureButton(int buttonId, int x, int y, String[] names, String[] tooltips) {
        super(buttonId, x, y, 0, 0, names, tooltips);
    }

    public NoTextureButton(int buttonId, int x, int y, int width, int height, String names) {
        super(buttonId, x, y, 0, 0, width, height, names);
    }

    public NoTextureButton(int buttonId, int x, int y, String names) {
        super(buttonId, x, y, 0, 0, names);
    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {}
}
