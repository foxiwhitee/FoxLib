package foxiwhitee.FoxLib.integration.applied;

import appeng.block.crafting.BlockCraftingStorage;
import appeng.block.crafting.BlockCraftingUnit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Integration(modid = "appliedenergistics2")
public class AppliedIntegration implements IIntegration {
    public static final Map<String, Predicate<ItemStack>> slotRestrictedInputFilter = new HashMap<>();

    static {
        slotRestrictedInputFilter.put("productivity", stack -> stack.getItem() instanceof ItemProductivityCard);
        slotRestrictedInputFilter.put("storage", stack -> Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingStorage);
        slotRestrictedInputFilter.put("accelerator", stack -> (Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingUnit && !(Block.getBlockFromItem(stack.getItem()) instanceof BlockCraftingStorage)));
    }

    @Override
    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent paramFMLInitializationEvent) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {

    }
}
