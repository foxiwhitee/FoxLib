package foxiwhitee.FoxLib.api.registries;

import foxiwhitee.FoxLib.recipes.IJsonRecipe;

import java.util.Map;

public interface IJsonRecipeRegister {
    void register(Class<? extends IJsonRecipe> recipe, String name);
    int size();
    Class<? extends IJsonRecipe> get(String key);
    Map.Entry<String, Class<? extends IJsonRecipe>> getEntry(int idx);
}
