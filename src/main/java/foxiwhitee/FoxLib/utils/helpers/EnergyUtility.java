package foxiwhitee.FoxLib.utils.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.List;

public class EnergyUtility {
    private static final String[] POSTFIX = {"", "k", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q"};
    private static final DecimalFormat DF = new DecimalFormat("#.##");

    public static String formatNumber(double energy) {
        if (energy < 0) return "-" + formatNumber(-energy);
        if (energy < 1000) return DF.format(energy).replace(",", ".");

        int offset = 0;
        double value = energy;

        while (value >= 1000 && offset < POSTFIX.length - 1) {
            value /= 1000.0;
            offset++;
        }

        if (value >= 1000) {
            return String.format("%.2e", energy).replace(",", ".");
        }

        return DF.format(value).replace(",", ".") + POSTFIX[offset];
    }
}
