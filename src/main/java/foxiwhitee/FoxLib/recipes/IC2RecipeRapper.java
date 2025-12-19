package foxiwhitee.FoxLib.recipes;

import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class IC2RecipeRapper implements IFoxRecipe {
    private final ItemStack output;
    private final Object input;

    public IC2RecipeRapper(ItemStack output, Object input) {
        this.output = output;
        this.input = input;
    }

    @Override
    public ItemStack getOut() {
        return output;
    }

    @Override
    public List<Object> getInputs() {
        return Arrays.asList(input);
    }

    @Override
    public boolean matches(List<ItemStack> stacks) {
        ItemStack input = stacks.get(0);
        if (this.input instanceof ItemStack) {
            return IFoxRecipe.simpleAreStacksEqual((ItemStack) this.input, input);
        } else if (this.input instanceof String) {
            return OreDictUtil.areStacksEqual(this.input, input);
        } else if (this.input instanceof StackOreDict ore) {
            return ore.check(input, false);
        }
        return false;
    }
}
