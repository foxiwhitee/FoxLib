package foxiwhitee.FoxLib.recipes;

import com.google.gson.JsonObject;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;

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

    default void addCraftByMineTweaker(IItemStack stack, IIngredient... inputs) {

    }

    default void removeCraftByMineTweaker(IItemStack stack) {

    }

    default boolean matches(I[] objects) {
        return matches(Arrays.asList(objects));
    }
}
