package foxiwhitee.FoxLib.utils;

import java.util.Map;

public class ProductivityUtil {
    private static double findNumberOfCycles(int percent) {
        return Math.pow((double) percent / 100, -1);
    }

    public static int check(double[] progress, int percent) {
        double n = findNumberOfCycles(percent);
        if (progress[0] >= n) {
            int out = 0;
            while (progress[0] >= n) {
                progress[0] -= n;
                out++;
            }
            return out;
        } else {
            return 0;
        }
    }

    public static <T> int check(Map<T, Double> progress, T key, int percent) {
        double n = findNumberOfCycles(percent);
        if (progress.get(key) >= n) {
            int out = 0;
            while (progress.get(key) >= n) {
                progress.merge(key, -n, Double::sum);
                out++;
            }
            return out;
        } else {
            return 0;
        }
    }

    public static double gauge(double pix, double ticks, double max) {
        double a = ticks * pix;
        return a / max;
    }

    public static double gaugeProductivityProgressBar(double ticks, int percent, double[] progress, double pix, double maxTicks) {
        double n = findNumberOfCycles(percent);

        double x = n != 0 ? progress[0] / n + (ticks / (maxTicks)) / n : 0;

        return gauge(pix, x, 1);
    }
}
