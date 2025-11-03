package foxiwhitee.FoxLib.registries;

import foxiwhitee.FoxLib.api.registries.IJsonRecipeRegister;
import foxiwhitee.FoxLib.api.registries.IRegistries;

public class Registries implements IRegistries {
    private final IJsonRecipeRegister jsonRecipeRegister = new JsonRecipeRegister();

    @Override
    public IJsonRecipeRegister registerJsonRecipe() {
        return jsonRecipeRegister;
    }

}
