package foxiwhitee.FoxLib.container;

import foxiwhitee.FoxLib.container.slots.SlotPlayerHotBar;
import foxiwhitee.FoxLib.container.slots.SlotPlayerInv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public abstract class FoxBaseContainer extends Container {
    private final InventoryPlayer invPlayer;
    private final TileEntity tileEntity;
    private boolean isProcessingShiftClick = false;

    public FoxBaseContainer(InventoryPlayer ip, TileEntity myTile) {
        this.invPlayer = ip;
        this.tileEntity = myTile;
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int offsetX, int offsetY) {
        for(int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new SlotPlayerHotBar(inventoryPlayer, i, 8 + i * 18 + offsetX, 58 + offsetY));

        }
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotPlayerInv(inventoryPlayer, j + i * 9 + 9, 8 + j * 18 + offsetX, offsetY + i * 18));

            }
        }
    }

    public InventoryPlayer getInventoryPlayer() {
        return this.invPlayer;
    }

    public TileEntity getTileEntity() {
        return this.tileEntity;
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.tileEntity instanceof IInventory ? ((IInventory)this.tileEntity).isUseableByPlayer(entityplayer) : true;
    }

}
