package foxiwhitee.FoxLib.config;

@Config(folder = "Fox-Mods", name = "FoxLib")
public class FoxLibConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enable_tooltips = true;


    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 1?")
    public static int productivityLvl1 = 1;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 2?")
    public static int productivityLvl2 = 3;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 3?")
    public static int productivityLvl3 = 5;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 4?")
    public static int productivityLvl4 = 7;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 5?")
    public static int productivityLvl5 = 10;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 6?")
    public static int productivityLvl6 = 15;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 7?")
    public static int productivityLvl7 = 25;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 8?")
    public static int productivityLvl8 = 50;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 9?")
    public static int productivityLvl9 = 75;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 10?")
    public static int productivityLvl10 = 100;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 11?")
    public static int productivityLvl11 = 200;

}
