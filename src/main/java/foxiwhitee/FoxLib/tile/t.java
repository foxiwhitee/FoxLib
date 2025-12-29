package foxiwhitee.FoxLib.tile;

import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class t extends FoxBaseInvTile {
    @Override
    public FoxInternalInventory getInternalInventory() {
        return null;

    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection whichSide) {
        return new int[0];
    }

    @Override
    public void onChangeInventory(IInventory inventory, int idx, InvOperation operation, ItemStack removed, ItemStack added) {

    }

    @Override
    public String getInventoryName() {
        return "";
    }
}
