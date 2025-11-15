package foxiwhitee.FoxLib.container;

import foxiwhitee.FoxLib.container.slots.SlotPlayerHotBar;
import foxiwhitee.FoxLib.container.slots.SlotPlayerInv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class FoxBaseContainer extends Container {
    private final InventoryPlayer invPlayer;
    private final TileEntity tileEntity;
    private boolean isProcessingShiftClick = false;
    private final Slot[] playerSlots;

    public FoxBaseContainer(InventoryPlayer ip, TileEntity myTile) {
        this.invPlayer = ip;
        this.tileEntity = myTile;
        this.playerSlots = new Slot[36];
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int offsetX, int offsetY) {
        for(int i = 0; i < 9; ++i) {
            this.playerSlots[i] = this.addSlotToContainer(new SlotPlayerHotBar(inventoryPlayer, i, 8 + i * 18 + offsetX, 58 + offsetY));
        }

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.playerSlots[j + i * 9 + 9] = this.addSlotToContainer(new SlotPlayerInv(inventoryPlayer, j + i * 9 + 9, 8 + j * 18 + offsetX, offsetY + i * 18));
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

    public boolean mergeStackToPlayerInv(ItemStack stack) {
        return mergeItemStack(stack, playerSlots[0].slotNumber, playerSlots[35].slotNumber, false);
    }

    public boolean mergeStackToTileInv(ItemStack stack) {
        return mergeItemStack(stack, playerSlots[35].slotNumber + 1, inventorySlots.size(), false);
    }

    public boolean clickWasInPlayerInventory(Slot slot) {
        return playerSlots[0] != null && slot != null && playerSlots[0].slotNumber <= slot.slotNumber && playerSlots[35] != null && slot.slotNumber <= playerSlots[35].slotNumber;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        Slot slot = (Slot) inventorySlots.get(slotId);
        if (slot == null || !slot.getHasStack()) {
            return null;
        }
        ItemStack stack = slot.getStack();
        boolean didMerge = clickWasInPlayerInventory(slot) ? mergeStackToTileInv(stack) : mergeStackToPlayerInv(stack);
        if (didMerge) {
            if (stack.stackSize == 0) {
                slot.putStack(null);
            }
            slot.onSlotChanged();
            return slot.getStack();
        }
        return null;
    }

    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
        boolean changed = false;
        int index = start;

        if (reverse) {
            index = end - 1;
        }

        Slot slot;
        ItemStack slotStack;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!reverse && index < end || reverse && index >= start)) {

                slot = (Slot) this.inventorySlots.get(index);
                slotStack = slot.getStack();

                if (!slot.isItemValid(stack)) {
                    if (reverse) --index; else ++index;
                    continue;
                }

                if (slotStack != null
                    && slotStack.getItem() == stack.getItem()
                    && (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack.getItemDamage())
                    && ItemStack.areItemStackTagsEqual(stack, slotStack)) {

                    int total = slotStack.stackSize + stack.stackSize;
                    int max = stack.getMaxStackSize();

                    if (total <= max) {
                        stack.stackSize = 0;
                        slotStack.stackSize = total;
                        slot.onSlotChanged();
                        changed = true;
                    } else if (slotStack.stackSize < max) {
                        stack.stackSize -= (max - slotStack.stackSize);
                        slotStack.stackSize = max;
                        slot.onSlotChanged();
                        changed = true;
                    }
                }

                if (reverse) --index; else ++index;
            }
        }

        if (stack.stackSize > 0) {

            if (reverse) index = end - 1;
            else index = start;

            while (!reverse && index < end || reverse && index >= start) {

                slot = (Slot) this.inventorySlots.get(index);
                slotStack = slot.getStack();

                if (!slot.isItemValid(stack)) {
                    if (reverse) --index; else ++index;
                    continue;
                }

                if (slotStack == null) {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    changed = true;
                    break;
                }

                if (reverse) --index; else ++index;
            }
        }

        return changed;
    }

}
