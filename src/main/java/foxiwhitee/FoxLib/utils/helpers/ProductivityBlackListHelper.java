package foxiwhitee.FoxLib.utils.helpers;

import com.github.bsideup.jabel.Desugar;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import ic2.core.block.machine.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ProductivityBlackListHelper {
    private static final class ItemInBL {
        private final Item item;
        private final int meta;

        ItemInBL(Item item, int meta) {
            this.item = item;
            this.meta = meta;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemInBL other)) return false;
            return Item.getIdFromItem(item) == Item.getIdFromItem(other.item) && meta == other.meta;
        }

        @Override
        public int hashCode() {
            return (Item.getIdFromItem(item) * 31) + meta;
        }
    }

    private static final List<ItemInBL> BLACKLIST = new ArrayList<>();

    public static void registerBlackList(String[] strings) {
        for (String s : strings) {
            ItemStack stack = null;
            try {
                stack = RecipeUtils.getItemStack(s);
            } catch (RuntimeException ignored) {}
            if (stack != null) {
                BLACKLIST.add(new ItemInBL(stack.getItem(), stack.getItemDamage()));
            }
        }
    }

    public static boolean isInBlackList(ItemStack itemStack) {
        if (itemStack == null) return false;
        return BLACKLIST.contains(new ItemInBL(itemStack.getItem(), itemStack.getItemDamage()));
    }

    public static boolean isInBlackList(Item item, int meta) {
        return BLACKLIST.contains(new ItemInBL(item, meta));
    }
}
