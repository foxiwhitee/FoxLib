package foxiwhitee.FoxLib.recipes;

import com.google.gson.JsonObject;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.value.IAny;

import java.util.Arrays;
import java.util.List;

public interface IJsonRecipe<I, O> {
    O[] getOutputs();
    I[] getInputs();
    boolean matches(List<I> objects);
    boolean hasOreDict();
    boolean hasMineTweakerIntegration();
    String getType();
    IJsonRecipe create(JsonObject data);
    void register();

    default void addCraftByMineTweaker(IItemStack stack, IAny... inputs) {
        IIngredient[] ingredients = new IIngredient[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].is(IIngredient.class)) {
                ingredients[i] = inputs[i].as(IIngredient.class);
            }
        }
        addCraftByMineTweaker(stack, ingredients);
    }

    default void addCraftByMineTweaker(IItemStack stack, IIngredient... inputs) {

    }

    default void removeCraftByMineTweaker(IItemStack stack) {

    }

    default boolean matches(I[] objects) {
        return matches(Arrays.asList(objects));
    }
}
