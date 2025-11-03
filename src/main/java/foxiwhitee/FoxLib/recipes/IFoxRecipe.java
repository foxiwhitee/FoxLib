package foxiwhitee.FoxLib.recipes;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IFoxRecipe {

    ItemStack getOut();

    List<Object> getInputs();

    boolean matches(List<ItemStack> stacks);

    static boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2) {
        return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
    }
}
