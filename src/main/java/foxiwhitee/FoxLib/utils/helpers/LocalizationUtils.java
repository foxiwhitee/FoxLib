package foxiwhitee.FoxLib.utils.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class LocalizationUtils {

    public static String localize(String string, Object... objects) {
        boolean hasArguments = objects != null && objects.length != 0;
        return !hasArguments ? StatCollector.translateToLocal(string) : String.format(StatCollector.translateToLocal(string), objects);
    }

    public static String makeRainbow(String string, double delay, int step, boolean isBold) {
        return ludicrousFormatting(string, new String[]{EnumChatFormatting.RED.toString(), EnumChatFormatting.GOLD.toString(), EnumChatFormatting.YELLOW.toString(), EnumChatFormatting.GREEN.toString(), EnumChatFormatting.AQUA.toString(), EnumChatFormatting.BLUE.toString(), EnumChatFormatting.LIGHT_PURPLE.toString()}, delay, step, isBold);
    }

    public static String makeDivine(String string, double delay, int step, boolean isBold) {
        return ludicrousFormatting(string, new String[]{"#F30000", "#FF0000", "#E80000", "#DC0000", "#D00000", "#C50101", "#B90101", "#AD0101", "#A10101", "#AD0101", "#B90101", "#C50101", "#D00000", "#E80000", "#FF0000"}, delay, step, isBold);
    }

    public static String makeFox(String string, double delay, int step, boolean isBold) {
        return ludicrousFormatting(string, new String[]{"#E86A00", "#E56600", "#EB6D00", "#EE7100", "#F17400", "#F37800", "#F67B00", "#F97F00", "#FC8200", "#FF8600", "#FC8200", "#F97F00", "#F67B00", "#F37800", "#F17400", "#EE7100", "#EB6D00", "#E56600"}, delay, step, isBold);
    }

    private static String ludicrousFormatting(String input, String[] colours, double delay, int step, boolean isBold) {
        StringBuilder sb = new StringBuilder(input.length() * (isBold ? 5 : 3));
        double d = delay;
        if (d <= 0.0D)
            d = 0.001D;
        int offset =
                (int)Math.floor(Minecraft.getSystemTime() / d) % colours.length;
        for (int i = 0, j = input.length(); i < j; i++) {
            char c = input.charAt(i);
            int col = (i * step + colours.length - offset) % colours.length;
            sb.append(colours[col].toString());
            if (isBold)
                sb.append(String.valueOf(EnumChatFormatting.BOLD));
            sb.append(c);
        }
        return sb.toString();
    }
}
