package foxiwhitee.FoxLib.recipes;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeUtils {
    public static ItemStack getItemStack(JsonElement element) {
        if (element.isJsonPrimitive()) {
            return getItemStack(element.getAsString());
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            ItemStack stack = getItemStack(object.get("value").getAsString());
            if (stack == null) {
                return null;
            }
            stack.setTagCompound(toNBT(object.get("tag").getAsJsonObject()));
            return stack;
        }
        return null;
    }

    public static ItemStack getItemStack(String name) throws RuntimeException {
        if (name.equals("null")) {
            return null;
        }
        Parsed info = parse(name);
        Item item = (Item) Item.itemRegistry.getObject(info.modId + ":" + info.name);
        if (item != null) {
            return new ItemStack(item, info.count, info.meta);
        }
        throw new RuntimeException("Item not found: " + name);
    }

    private static Parsed parse(String input) {
        Pattern pattern = Pattern.compile("^<([\\w-]+):([\\w.-]*?)(?:\\.(\\d+))?(?::(\\d+))?>$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String first = matcher.group(1);
            String second = matcher.group(2);
            int dotNumber = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
            int colonNumber = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 1;
            return new Parsed(first, second, dotNumber, colonNumber);
        }

        throw new RuntimeException("ItemStack should have the form <modId:name.meta:count> where meta and count are optional");
    }

    public static ItemStack getOutput(JsonObject data) throws RuntimeException {
        ItemStack out;
        if (data.has("output")) {
            out = RecipeUtils.getItemStack(data.get("output"));
        } else if (data.has("outputs")) {
            out = RecipeUtils.getItemStack(data.get("outputs").getAsJsonArray().get(0));
        } else {
            throw new RuntimeException("Unable to find craft exit");
        }
        if (out == null) {
            throw new NullPointerException();
        }
        return out;
    }

    public static Object[] getInputs(JsonObject data, boolean oreDict) throws RuntimeException {
        Object[] inputs;
        if (data.has("inputs")) {
            JsonArray inp = data.get("inputs").getAsJsonArray();
            inputs = new Object[inp.size()];
            String name;
            for (int i = 0; i < inp.size(); i++) {
                if (inp.get(i).isJsonObject()) {
                    inputs[i] = getItemStack(inp.get(i));
                    continue;
                }
                name = inp.get(i).getAsString();
                if (oreDict && name.startsWith("<ore:")) {
                    inputs[i] = name.replace("<ore:", "").replace(">", "");
                } else {
                    inputs[i] = getItemStack(name);
                }
            }
        } else {
            throw new RuntimeException("Unable to find craft inputs");
        }
        return inputs;
    }


    public static ItemStack[] getOutputs(JsonObject data) throws RuntimeException {
        ItemStack[] out;
        if (data.has("outputs")) {
            out = new ItemStack[data.get("outputs").getAsJsonArray().size()];
            for (int i = 0; i < data.get("outputs").getAsJsonArray().size(); i++) {
                out[i] = RecipeUtils.getItemStack(data.get("outputs").getAsJsonArray().get(i));
            }
        } else {
            throw new RuntimeException("Unable to find craft exit");
        }
        return out;
    }

    private static NBTTagCompound toNBT(JsonObject json) {
        return toNBTInternal(json, "");
    }

    private static NBTTagCompound toNBTInternal(JsonObject json, String path) {
        NBTTagCompound compound = new NBTTagCompound();

        for (Map.Entry<String, JsonElement> entrySet : json.entrySet()) {
            String key = entrySet.getKey();;
            JsonElement element = entrySet.getValue();
            String currentPath = path.isEmpty() ? key : path + "." + key;

            if (element.isJsonObject()) {
                compound.setTag(key, toNBTInternal(element.getAsJsonObject(), currentPath));

            } else if (element.isJsonArray()) {
                JsonArray array = element.getAsJsonArray();
                NBTTagList list = new NBTTagList();

                for (int i = 0; i < array.size(); i++) {
                    JsonElement entry = array.get(i);

                    if (!entry.isJsonObject()) {
                        throw new IllegalArgumentException("Array at " + currentPath + " contains non-object element at index " + i);
                    }

                    list.appendTag(toNBTInternal(entry.getAsJsonObject(), currentPath + "[" + i + "]"));
                }

                compound.setTag(key, list);

            } else if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();

                if (primitive.isBoolean()) {
                    compound.setBoolean(key, primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    Number num = primitive.getAsNumber();

                    if (num.toString().contains(".")) {
                        compound.setDouble(key, num.doubleValue());
                    } else {
                        long l = num.longValue();
                        if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                            compound.setInteger(key, (int) l);
                        } else {
                            compound.setLong(key, l);
                        }
                    }
                } else if (primitive.isString()) {
                    compound.setString(key, primitive.getAsString());
                }
            } else {
                throw new IllegalArgumentException("Unsupported JSON element at " + currentPath);
            }
        }

        return compound;
    }

    @Desugar
    private record Parsed(String modId, String name, int meta, int count) {}
}
