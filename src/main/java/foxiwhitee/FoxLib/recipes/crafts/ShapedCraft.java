package foxiwhitee.FoxLib.recipes.crafts;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class ShapedCraft implements IJsonRecipe<Object, ItemStack> {
    private ItemStack[] outputs;
    private Object[] inputs;

    public ShapedCraft() {}

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public Object[] getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(List<Object> objects) {
        if (inputs.length != objects.size()) {
            return false;
        }
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] instanceof String && objects.get(i) instanceof String) {
                if (!inputs[i].equals(objects.get(i))) {
                    return false;
                }
            } else if (inputs[i] instanceof String && objects.get(i) instanceof ItemStack) {
                if(!OreDictUtil.areStacksEqual(inputs[i], (ItemStack) objects.get(i))) {
                    return false;
                }
            } else if (inputs[i] instanceof ItemStack && objects.get(i) instanceof String) {
                if(!OreDictUtil.areStacksEqual(objects.get(i), (ItemStack) inputs[i])) {
                    return false;
                }
            } else if (inputs[i] instanceof ItemStack && objects.get(i) instanceof ItemStack) {
                if(!IFoxRecipe.simpleAreStacksEqual((ItemStack) inputs[i], (ItemStack) objects.get(i))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasOreDict() {
        return true;
    }

    @Override
    public boolean hasMineTweakerIntegration() {
        return false;
    }

    @Override
    public String getType() {
        return "workbenchShaped";
    }

    @Override
    public IJsonRecipe create(JsonObject data) {
        try {
            this.outputs = new ItemStack[] { RecipeUtils.getOutput(data) };
            this.inputs = RecipeUtils.getInputs(data, hasOreDict());
        } catch (RuntimeException e) {
            if (e.getMessage().startsWith("Item not found:")) {
                this.inputs = null;
                this.outputs = null;
            } else {
                throw e;
            }
        }
        return this;
    }

    @Override
    public void register() {
        if (this.outputs == null && this.inputs == null) {
            return;
        }
        if (inputs.length != 9)
            throw new IllegalArgumentException("Inputs must be length 9 (3x3 grid)");

        String[] pattern = {"ABC", "DEF", "GHI"};

        Object[] recipe = new Object[pattern.length * 2 + 1];
        int i = 0;
        recipe[i++] = outputs[0];

        List<Object> params = new java.util.ArrayList<>();
        params.add("ABC");
        params.add("DEF");
        params.add("GHI");

        char[] keys = {'A','B','C','D','E','F','G','H','I'};
        for (int k = 0; k < inputs.length; k++) {
            Object in = inputs[k];
            if (in == null) continue;
            if (in instanceof String) {
                params.add(keys[k]);
                params.add((String) in);
            } else if (in instanceof ItemStack) {
                params.add(keys[k]);
                params.add(((ItemStack) in).copy());
            } else {
                throw new IllegalArgumentException("Unsupported ingredient type at slot " + k);
            }
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(outputs[0].copy(), params.toArray()));
    }
}
