package foxiwhitee.FoxLib.integration.applied;

import appeng.api.recipes.IIngredient;
import appeng.block.crafting.BlockCraftingStorage;
import appeng.block.crafting.BlockCraftingUnit;
import appeng.recipes.game.ShapedRecipe;
import appeng.recipes.game.ShapelessRecipe;
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
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Integration(modid = "appliedenergistics2")
public class AppliedIntegration implements IIntegration {
    public static final Map<String, Predicate<ItemStack>> slotRestrictedInputFilter = new HashMap<>();

    static {
        slotRestrictedInputFilter.put("productivity", stack -> stack.getItem() instanceof ItemProductivityCard);
        slotRestrictedInputFilter.put("storage", stack -> Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingStorage);
        slotRestrictedInputFilter.put("accelerator", stack -> (Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingUnit && !(Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingStorage)));
    }

    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
        FindDuplicateCraftsScript.recipeConverters.put(ShapedRecipe.class, recipe -> {
            if (recipe instanceof ShapedRecipe r) {
                Object[] temp = r.getInput();
                List<ItemStack> list = new ArrayList<>();
                for (Object o : temp) {
                    if (o instanceof IIngredient ing) {
                        try {
                            list.add(ing.getItemStack());
                        } catch (Exception e) {}
                    }
                }
                return new BaseFoxRecipe(r.getRecipeOutput(), list.toArray());
            }
            return null;
        });
        FindDuplicateCraftsScript.recipeConverters.put(ShapelessRecipe.class, recipe -> {
            if (recipe instanceof ShapelessRecipe r) {
                Object[] temp = r.getInput().toArray();
                List<ItemStack> list = new ArrayList<>();
                for (Object o : temp) {
                    if (o instanceof IIngredient ing) {
                        try {
                            list.add(ing.getItemStack());
                        } catch (Exception e) {}
                    }
                }
                return new BaseFoxRecipe(r.getRecipeOutput(), list.toArray());
            }
            return null;
        });
    }
}
