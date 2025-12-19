package foxiwhitee.FoxLib.integration.botania;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxLib.recipes.BaseFoxRecipe;
import foxiwhitee.FoxLib.utils.FindDuplicateCraftsScript;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Integration(modid = "botania")
public class BotaniaIntegration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
        FindDuplicateCraftsScript.recipeConverters.put(RecipeManaInfusion.class, recipe -> {
            if (recipe instanceof RecipeManaInfusion r) {
                return new BaseFoxRecipe(r.getOutput(), r.getInput());
            }
            return null;
        });
        FindDuplicateCraftsScript.recipes.add(BotaniaAPI.manaInfusionRecipes);
    }
}
