package foxiwhitee.FoxLib.integration.thermal;

import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.SawmillManager;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.recipes.BaseFoxRecipe;
import foxiwhitee.FoxLib.utils.FindDuplicateCraftsScript;

import java.util.Arrays;

@Integration(modid = "ThermalExpansion")
public class ThermalIntegration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
        FindDuplicateCraftsScript.recipeConverters.put(PulverizerManager.RecipePulverizer.class, recipe -> {
            if (recipe instanceof PulverizerManager.RecipePulverizer r) {
                return new BaseFoxRecipe(r.getPrimaryOutput(), r.getInput());
            }
            return null;
        });
        FindDuplicateCraftsScript.recipes.add(Arrays.asList(PulverizerManager.getRecipeList()));
        FindDuplicateCraftsScript.recipeConverters.put(SawmillManager.RecipeSawmill.class, recipe -> {
            if (recipe instanceof SawmillManager.RecipeSawmill r) {
                return new BaseFoxRecipe(r.getPrimaryOutput(), r.getInput());
            }
            return null;
        });
        FindDuplicateCraftsScript.recipes.add(Arrays.asList(SawmillManager.getRecipeList()));
    }
}
