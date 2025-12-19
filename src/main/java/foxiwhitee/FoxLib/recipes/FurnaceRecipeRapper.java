package foxiwhitee.FoxLib.recipes;

import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class FurnaceRecipeRapper implements IFoxRecipe {
    private final ItemStack input, output;

    public FurnaceRecipeRapper(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
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
        return ItemStackUtil.stackEquals(input, stacks.get(0));
    }
}
