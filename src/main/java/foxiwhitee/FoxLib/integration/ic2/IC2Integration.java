package foxiwhitee.FoxLib.integration.ic2;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.recipes.BaseFoxRecipe;
import foxiwhitee.FoxLib.recipes.IC2RecipeRapper;
import foxiwhitee.FoxLib.utils.FindDuplicateCraftsScript;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Integration(modid = "IC2")
public class IC2Integration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
        List<IC2RecipeRapper> ic2Recipes = new ArrayList<>();
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.macerator.getRecipes().entrySet()) {
            Object input;
            if (entry.getKey() instanceof RecipeInputOreDict r) {
                input = new StackOreDict(r.input, r.amount);
            } else {
                input = entry.getKey().getInputs().get(0);
            }
            ic2Recipes.add(new IC2RecipeRapper(entry.getValue().items.get(0), input));
        }
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : Recipes.compressor.getRecipes().entrySet()) {
            Object input;
            if (entry.getKey() instanceof RecipeInputOreDict r) {
                input = new StackOreDict(r.input, r.amount);
            } else {
                input = entry.getKey().getInputs().get(0);
            }
            ic2Recipes.add(new IC2RecipeRapper(entry.getValue().items.get(0), input));
        }
        FindDuplicateCraftsScript.recipeConverters.put(IC2RecipeRapper.class, recipe -> {
            if (recipe instanceof IC2RecipeRapper r) {
                return r;
            }
            return null;
        });
        FindDuplicateCraftsScript.recipes.add(ic2Recipes);
    }
}
