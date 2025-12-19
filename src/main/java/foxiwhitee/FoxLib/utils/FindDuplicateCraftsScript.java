package foxiwhitee.FoxLib.utils;

import cpw.mods.fml.common.registry.GameData;
import foxiwhitee.FoxLib.recipes.IC2RecipeRapper;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindDuplicateCraftsScript {

    @FunctionalInterface
    public interface RecipeConverter {
        IFoxRecipe convert(Object input);
    }

    public static final Map<Class<?>, RecipeConverter> recipeConverters = new HashMap<>();
    public static final List<List<?>> recipes = new ArrayList<>();
    private static final List<IFoxRecipe> convertedRecipes = new ArrayList<>();

    public FindDuplicateCraftsScript() {
        if (convertedRecipes.isEmpty()) {
            recipes.forEach(list -> {
                list.forEach(recipe -> {
                    for (Map.Entry<Class<?>, RecipeConverter> entry : recipeConverters.entrySet()) {
                        if (entry.getKey().isInstance(recipe)) {
                            IFoxRecipe r = entry.getValue().convert(recipe);
                            if (r != null) {
                                convertedRecipes.add(r);
                                break;
                            }
                        }
                    }
                });
            });
        }
    }

    public void findReverseCrafts() {
        List<String> foundPairs = new ArrayList<>();
        for (int i = 0; i < convertedRecipes.size(); i++) {
            IFoxRecipe r1 = convertedRecipes.get(i);
            ItemStack output1 = r1.getOut();
            if (output1 == null) continue;
            List<Object> input1 = r1.getInputs();
            if (input1.isEmpty()) continue;
            for (int j = 0; j < convertedRecipes.size(); j++) {
                if (i == j) continue;
                IFoxRecipe r2 = convertedRecipes.get(j);
                ItemStack output2 = r2.getOut();
                if (output2 == null) continue;
                List<Object> input2 = r2.getInputs();
                if (input2.isEmpty()) continue;
                if (matchesReverse(input1, output2) && matchesReverse(input2, output1)) {
                    String pair = getStackName(output1) + " <-> " + getStackName(output2);
                    foundPairs.add(pair);
                }
            }
        }
        for (String s : foundPairs) {
            System.out.println(s);
        }
    }

    private static boolean matchesReverse(List<Object> inputs, ItemStack output) {
        int totalCount = 0;
        for (Object o : inputs) {
            if (!ItemStackUtil.matchesStackAndOther(output, o)) {
                return false;
            }
            if (o instanceof ItemStack s) {
                totalCount += s.stackSize;
            } else if (o instanceof StackOreDict ore) {
                totalCount += ore.getCount();
            } else if (o instanceof String s) {
                totalCount++;
            }
        }

        if (totalCount == 0) {
            return false;
        }
        return totalCount == output.stackSize || output.stackSize % totalCount == 0 || totalCount % output.stackSize == 0;
    }

    private static String getStackName(ItemStack stack) {
        Item item = stack.getItem();
        String modid = GameData.getItemRegistry().getNameForObject(item).split(":")[0];
        String name = GameData.getItemRegistry().getNameForObject(item).split(":")[1];
        int meta = stack.getItemDamage();

        String itemString = meta == 0
            ? "<" + modid + ":" + name + ">"
            : "<" + modid + ":" + name + "." + meta + ">";
        return itemString;
    }
}
