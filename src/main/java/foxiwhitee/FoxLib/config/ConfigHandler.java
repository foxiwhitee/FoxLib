package foxiwhitee.FoxLib.config;

import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigHandler {
    private static final Map<String, Configuration> configCache = new HashMap<>();

    public static void loadConfigs(FMLPreInitializationEvent event) {
        ASMDataTable asmData = event.getAsmData();
        Set<ASMDataTable.ASMData> configClasses = asmData.getAll(Config.class.getName());

        for (ASMDataTable.ASMData entry : configClasses) {
            try {
                Class<?> configClass = Class.forName(entry.getClassName());
                Config configAnnotation = configClass.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    loadConfig(configClass, configAnnotation, event);
                }
            } catch (Exception e) {
                System.err.println("Failed to load config for " + entry.getClassName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void loadConfig(Class<?> configClass, Config configAnnotation, FMLPreInitializationEvent event) {
        String folder = configAnnotation.folder();
        String name = configAnnotation.name();
        String configKey = folder + "/" + name;
        File configDir = new File(event.getModConfigurationDirectory(), folder);
        File configFile = new File(configDir, name + ".cfg");

        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        Configuration config = new Configuration(configFile);
        config.load();

        for (Field field : configClass.getDeclaredFields()) {
            ConfigValue configValue = field.getAnnotation(ConfigValue.class);
            if (configValue != null) {
                field.setAccessible(true);
                String category = configValue.category().isEmpty() ? "general" : configValue.category();
                String key = configValue.name().isEmpty() ? field.getName() : configValue.name();
                String desc = configValue.desc();
                String min = configValue.min();
                String max = configValue.max();

                try {
                    Object defaultValue = field.get(null);
                    Class<?> type = field.getType();

                    double minVal = Double.NEGATIVE_INFINITY;
                    double maxVal = Double.POSITIVE_INFINITY;

                    if (type == Integer.TYPE) {
                        maxVal = max.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(max);
                        minVal = min.isEmpty() ? 1.0 : Integer.parseInt(min);
                    } else if (type == Long.TYPE) {
                        maxVal = max.isEmpty() ? Long.MAX_VALUE : Long.parseLong(max);
                        minVal = min.isEmpty() ? 1.0 : Long.parseLong(min);
                    } else if (type == Double.TYPE) {
                        maxVal = max.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(max);
                        minVal = min.isEmpty() ? 1.0 : Double.parseDouble(min);
                    } else if (type == Boolean.TYPE) {
                        if (!max.isEmpty() || !min.isEmpty()) {
                            throw new ConfigExcaption("Boolean type can't have min/max values");
                        }
                    } else if (type == String.class) {
                        if (!max.isEmpty() || !min.isEmpty()) {
                            throw new ConfigExcaption("String type can't have min/max values");
                        }
                    } else if (type.isArray() && type.getComponentType() == String.class) {
                        if (!max.isEmpty() || !min.isEmpty()) {
                            throw new ConfigExcaption("String[] type can't have min/max values");
                        }
                    }

                    if (type == Integer.TYPE) {
                        int value = config.getInt(key, category, (Integer) defaultValue, (int) minVal, (int) maxVal, desc);
                        field.setInt(null, value);
                    } else if (type == Long.TYPE) {
                        long value = Long.parseLong(config.getString(key, category, String.valueOf(defaultValue),
                            desc + " [range: " + (long) minVal + " ~ " + (long) maxVal + "]"));
                        field.setLong(null, value);
                    } else if (type == Double.TYPE) {
                        double value = config.get(category, key, (Double) defaultValue,
                            desc + " [range: " + minVal + " ~ " + maxVal + ", default: " + (Double) defaultValue + "]",
                            minVal, maxVal).getDouble();
                        field.setDouble(null, value);
                    } else if (type == Boolean.TYPE) {
                        boolean value = config.getBoolean(key, category, (Boolean) defaultValue, desc);
                        field.setBoolean(null, value);
                    } else if (type == String.class) {
                        String value = config.getString(key, category, (String) defaultValue, desc);
                        field.set(null, value);
                    } else if (type.isArray() && type.getComponentType() == String.class) {
                        String[] defaultArray = (String[]) defaultValue;
                        String[] value = config.getStringList(key, category, defaultArray, desc);
                        field.set(null, value);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    if (config.hasChanged()) {
                        config.save();
                    }
                }
            }
        }
        configCache.put(configKey, config);
    }
}
