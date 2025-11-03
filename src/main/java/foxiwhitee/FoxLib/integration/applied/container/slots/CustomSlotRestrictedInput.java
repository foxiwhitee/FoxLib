package foxiwhitee.FoxLib.integration.applied.container.slots;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItems;
import appeng.api.definitions.IMaterials;
import appeng.block.crafting.BlockCraftingStorage;
import appeng.block.crafting.BlockCraftingUnit;
import foxiwhitee.FoxLib.integration.applied.AppliedIntegration;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.Map;
import java.util.function.Predicate;

public class CustomSlotRestrictedInput extends CustomAppEngSlot {
    private final String which;
    private final InventoryPlayer p;
    private boolean allowEdit = true;
    private int stackLimit = -1;

    public CustomSlotRestrictedInput(String valid, IInventory i, int slotIndex, int x, int y, InventoryPlayer p) {
        super(i, slotIndex, x, y);
        this.which = valid;
        this.p = p;
    }

    public int getSlotStackLimit() {
        return this.stackLimit != -1 ? this.stackLimit : super.getSlotStackLimit();
    }

    public Slot setStackLimit(int i) {
        this.stackLimit = i;
        return this;
    }

    public boolean isItemValid(ItemStack i) {
        if (!this.getContainer().isValidForSlot(this, i)) {
            return false;
        } else if (i == null) {
            return false;
        } else if (i.getItem() == null) {
            return false;
        } else if (!this.inventory.isItemValidForSlot(this.getSlotIndex(), i)) {
            return false;
        } else if (!this.isAllowEdit()) {
            return false;
        } else {
            for (Map.Entry<String, Predicate<ItemStack>> entry : AppliedIntegration.slotRestrictedInputFilter.entrySet()) {
                if (entry.getKey().equals(which)) {
                    return entry.getValue().test(i);
                }
            }
            return false;
        }
    }

    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return this.isAllowEdit();
    }

    public ItemStack getDisplayStack() {
        return super.getStack();
    }

    private boolean isAllowEdit() {
        return this.allowEdit;
    }

}
