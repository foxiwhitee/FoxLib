package foxiwhitee.FoxLib.utils;

import appeng.recipes.game.ShapedRecipe;
import appeng.recipes.game.ShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

    public static void findReverseCrafts() {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        List<String> foundPairs = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            IRecipe r1 = recipes.get(i);
            ItemStack output1 = r1.getRecipeOutput();
            if (output1 == null) continue;
            List<ItemStack> input1 = getRecipeInputs(r1);
            if (input1.isEmpty()) continue;

            for (int j = 0; j < recipes.size(); j++) {
                if (i == j) continue;
                IRecipe r2 = recipes.get(j);
                ItemStack output2 = r2.getRecipeOutput();
                if (output2 == null) continue;
                List<ItemStack> input2 = getRecipeInputs(r2);
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

    private static List<ItemStack> getRecipeInputs(IRecipe recipe) {
        List<ItemStack> inputs = new ArrayList<>();
        Object[] items = null;

        if (recipe instanceof ShapedRecipes r) {
            items = r.recipeItems;
        } else if (recipe instanceof ShapelessRecipes r) {
            items = r.recipeItems.toArray();
        } else if (recipe instanceof ShapedRecipe r) {
            items = r.getInput();
        } else if (recipe instanceof ShapedOreRecipe r) {
            items = r.getInput();
        } else if (recipe instanceof ShapelessRecipe r) {
            items = r.getInput().toArray();
        } else if (recipe instanceof ShapelessOreRecipe r) {
            items = r.getInput().toArray();
        }

        if (items == null) return inputs;

        for (Object o : items) {
            if (o instanceof ItemStack) {
                inputs.add(((ItemStack) o).copy());
            } else if (o instanceof String) {
                List<ItemStack> oreStacks = OreDictionary.getOres((String) o);
                if (!oreStacks.isEmpty()) {
                    inputs.add(oreStacks.get(0).copy());
                }
            } else if (o instanceof ArrayList<?> a) {
                if (a.get(0) instanceof ItemStack s) {
                    inputs.add(s.copy());
                }
            }
        }

        return inputs;
    }


    private static boolean matchesReverse(List<ItemStack> inputs, ItemStack output) {
        int totalCount = 0;

        for (ItemStack s : inputs) {
            if (!OreDictionary.itemMatches(s, output, false)) return false;
            totalCount += s.stackSize;
        }

        if (totalCount == 0) {
            return false;
        }
        return totalCount == output.stackSize || output.stackSize % totalCount == 0 || totalCount % output.stackSize == 0;
    }

    private static String getStackName(ItemStack stack) {
        return stack.getItem().getUnlocalizedName().replace("tile.", "").replace("item.", "") + "." + stack.getItemDamage();
    }
}
