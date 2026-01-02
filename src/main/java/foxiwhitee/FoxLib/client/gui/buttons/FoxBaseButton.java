package foxiwhitee.FoxLib.client.gui.buttons;

import appeng.client.gui.widgets.GuiImgButton;
import foxiwhitee.FoxLib.api.button.ITooltipButton;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class FoxBaseButton extends GuiButton implements ITooltipButton {
    private final int u;
    private final int v;
    private final int textureSizeX;
    private final int textureSizeY;
    private final String[] names;
    private final String[] tooltips;
    private int current = 0;

    public FoxBaseButton(int buttonId, int x, int y, int u, int v, int width, int height, int textureSizeX, int textureSizeY, String[] names, String[] tooltips) {
        super(buttonId, x, y, names[0]);
        this.width = width;
        this.height = height;
        this.u = u;
        this.v = v;
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
        this.names = names;
        this.tooltips = tooltips;
    }

    public FoxBaseButton(int buttonId, int x, int y, int u, int v, int width, int height, String[] names, String[] tooltips) {
        this(buttonId, x, y, u, v, width, height, 512, 512, names, tooltips);
    }

    public FoxBaseButton(int buttonId, int x, int y, int u, int v, String[] names, String[] tooltips) {
        this(buttonId, x, y, u, v, 16, 16, 512, 512, names, tooltips);
    }

    public FoxBaseButton(int buttonId, int x, int y, int u, int v, int width, int height, String names) {
        this(buttonId, x, y, u, v, width, height, 512, 512, new String[] {names}, new String[0]);
    }

    public FoxBaseButton(int buttonId, int x, int y, int u, int v, String names) {
        this(buttonId, x, y, u, v, 16, 16, 512, 512, new String[] {names}, new String[0]);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        GL11.glColor4f(1, 1, 1, 1);
        UtilGui.drawTexture(xPosition, yPosition, u, v, width, height, textureSizeX, textureSizeY);
        mouseDragged(mc, mouseX, mouseY);
    }

    @Override
    public String getMessage() {
        String displayName = names[current];
        String displayValue = null;
        if (tooltips.length > current) {
            displayValue = tooltips[current];
        }

        if (displayName == null) {
            return null;
        } else {
            String name = LocalizationUtils.localize(displayName);
            if (displayValue != null) {
                String value = LocalizationUtils.localize(displayValue);
                StringBuilder sb = new StringBuilder(value);
                int i = sb.lastIndexOf("\n");
                if (i <= 0) {
                    i = 0;
                }

                while (i + 30 < sb.length() && (i = sb.lastIndexOf(" ", i + 30)) != -1) {
                    sb.replace(i, i + 1, "\n");
                }
                return name + "\n" + sb.toString();
            }

            return name;
        }
    }

    public void nextText() {
        current++;
        if (current >= names.length) {
            current -= names.length;
        }
    }

    public void setCurrentText(int current) {
        this.current = Math.min(current, names.length - 1);
    }

    @Override
    public int xPos() {
        return this.xPosition;
    }

    @Override
    public int yPos() {
        return this.yPosition;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }
}
