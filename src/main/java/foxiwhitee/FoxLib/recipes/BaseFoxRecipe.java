package foxiwhitee.FoxLib.recipes;

import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaseFoxRecipe implements IFoxRecipe {
    private final ItemStack output;
    private final Object[] inputs;

    public BaseFoxRecipe(ItemStack output, Object... inputs) {
        this.output = output;
        this.inputs = new Object[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            Object input = inputs[i];
            if (input != null) {
                if (input instanceof ArrayList<?> list && !list.isEmpty()) {
                    this.inputs[i] = list.get(0);
                } else {
                    this.inputs[i] = input;
                }
            }
        }
    }

    @Override
    public ItemStack getOut() {
        return output;
    }

    @Override
    public List<Object> getInputs() {
        return Arrays.asList(inputs);
    }

    public boolean matchesAll(List<Object> stacks, boolean withCount) {
        List<Object> inputsMissing = new ArrayList(Arrays.asList(this.inputs));
        List<Object> stacksToRemove = new ArrayList();

        for(Object o : stacks) {
            if (o != null) {
                if (inputsMissing.isEmpty()) {
                    break;
                }

                int stackIndex = -1;

                for(int j = 0; j < inputsMissing.size(); ++j) {
                    Object input = inputsMissing.get(j);
                    if (ItemStackUtil.matches(o, input, withCount)) {
                        if(!stacksToRemove.contains(o)) {
                            stacksToRemove.add(o);
                            stackIndex = j;
                            break;
                        }
                    }
                }

                if (stackIndex != -1) {
                    inputsMissing.remove(stackIndex);
                }
            }
        }

        return inputsMissing.isEmpty();
    }

    @Override
    public boolean matches(List<ItemStack> stacks) {
        return matchesAll(Arrays.asList(stacks.toArray()), false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        BaseFoxRecipe other = (BaseFoxRecipe)obj;
        if (!ItemStackUtil.stackEquals(this.output, other.output, true)) {
            return false;
        }
        return ItemStackUtil.matches(this.inputs, other.inputs, true);
    }
}
