package foxiwhitee.FoxLib.recipes.crafts;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ShapelessCraft implements IJsonRecipe<Object, ItemStack> {
    private ItemStack[] outputs;
    private Object[] inputs;

    public ShapelessCraft() {}

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public Object[] getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(List<Object> inputs) {
        List<Object> ins = new ArrayList<>(Arrays.asList(getInputs()));
        List<Object> inCopy = new ArrayList<>(inputs);

        for (Iterator<Object> it = inCopy.iterator(); it.hasNext(); ) {
            Object obj = it.next();
            if (obj == null) continue;

            for (int i = 0; i < ins.size(); i++) {
                Object input = ins.get(i);
                boolean match = false;

                if (input instanceof String && obj instanceof String) {
                    match = input.equals(obj);
                } else if (input instanceof ItemStack && obj instanceof ItemStack) {
                    match = IFoxRecipe.simpleAreStacksEqual((ItemStack) input, (ItemStack) obj);
                } else if (input instanceof String && obj instanceof ItemStack) {
                    for (ItemStack ore : OreDictionary.getOres((String) input)) {
                        if (IFoxRecipe.simpleAreStacksEqual(ore, (ItemStack) obj)) {
                            match = true;
                            break;
                        }
                    }
                }

                if (match) {
                    ins.remove(i);
                    it.remove();
                    break;
                }
            }
        }

        return ins.isEmpty() && inCopy.isEmpty();
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
        return "workbenchShapeless";
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
        if (inputs == null || inputs.length == 0)
            throw new IllegalArgumentException("Inputs cannot be empty for shapeless recipe");

        List<Object> params = new ArrayList<>();

        for (Object in : inputs) {
            if (in == null) continue;
            if (in instanceof String) {
                params.add((String) in);
            } else if (in instanceof ItemStack) {
                params.add(((ItemStack) in).copy());
            } else {
                throw new IllegalArgumentException("Unsupported ingredient type: " + in.getClass());
            }
        }

        if (params.isEmpty())
            throw new IllegalArgumentException("Recipe must have at least one valid ingredient");

        GameRegistry.addRecipe(new ShapelessOreRecipe(outputs[0].copy(), params.toArray()));
    }
}
