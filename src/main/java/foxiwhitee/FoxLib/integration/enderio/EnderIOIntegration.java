package foxiwhitee.FoxLib.integration.enderio;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.recipe.Recipe;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.recipes.BaseFoxRecipe;
import foxiwhitee.FoxLib.utils.FindDuplicateCraftsScript;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

@Integration(modid = "EnderIO")
public class EnderIOIntegration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
        FindDuplicateCraftsScript.recipeConverters.put(Recipe.class, recipe -> {
            if (recipe instanceof Recipe r) {
                return new BaseFoxRecipe(r.getOutputs()[0].getOutput(), r.getInputStacks());
            }
            return null;
        });
        FindDuplicateCraftsScript.recipes.add(CrusherRecipeManager.getInstance().getRecipes());
    }
}
