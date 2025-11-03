package foxiwhitee.FoxLib.registries;

import foxiwhitee.FoxLib.api.registries.IJsonRecipeRegister;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.crafts.ShapedCraft;
import foxiwhitee.FoxLib.recipes.crafts.ShapelessCraft;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonRecipeRegister implements IJsonRecipeRegister {
    private final Map<String, Class<? extends IJsonRecipe>> recipes = new LinkedHashMap<>();

    public JsonRecipeRegister() {
        register(ShapedCraft.class, "workbenchShaped");
        register(ShapelessCraft.class, "workbenchShapeless");
    }

    @Override
    public void register(Class<? extends IJsonRecipe> recipe, String name) {
        recipes.put(name, recipe);
    }

    public int size() {
        return recipes.size();
    }

    public Class<? extends IJsonRecipe> get(String key) {
        return recipes.get(key);
    }

    public Map.Entry<String, Class<? extends IJsonRecipe>> getEntry(int idx) {
        int i = 0;
        if (size() > idx) {
            for (Map.Entry<String, Class<? extends IJsonRecipe>> entry : recipes.entrySet()) {
                if (i == idx) {
                    return entry;
                }
                i++;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
