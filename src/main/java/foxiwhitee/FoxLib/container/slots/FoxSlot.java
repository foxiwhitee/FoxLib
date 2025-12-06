package foxiwhitee.FoxLib.container.slots;

import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FoxSlot extends Slot {
    private final int defX;
    private final int defY;
    private final int idx;
    private FoxBaseContainer myContainer = null;
    private boolean isDisplay = false;
    private IOptionalSlotHost host;

    public FoxSlot(IInventory inv, int idx, int x, int y) {
        this(inv, null, idx, x, y);
    }
    public FoxSlot(IInventory inv, IOptionalSlotHost host, int idx, int x, int y) {
        super(inv, idx, x, y);
        this.defX = x;
        this.defY = y;
        this.idx = idx;
        this.host = host;
    }

    public void clearStack() {
        super.putStack((ItemStack)null);
    }

    public boolean isItemValid(ItemStack par1ItemStack) {
        return this.isEnabled() ? super.isItemValid(par1ItemStack) : false;
    }

    public ItemStack getStack() {
        if (!this.isEnabled()) {
            return null;
        } else if (this.inventory.getSizeInventory() <= this.getSlotIndex()) {
            return null;
        } else if (this.isDisplay()) {
            this.setDisplay(false);
            return this.getDisplayStack();
        } else {
            return this.inventory.getStackInSlot(this.getSlotIndex());
        }
    }

    public void putStack(ItemStack par1ItemStack) {
        if (this.isEnabled()) {
            super.putStack(par1ItemStack);
        }
    }

    public boolean func_111238_b() {
        return this.isEnabled();
    }

    public ItemStack getDisplayStack() {
        return super.getStack();
    }

    public boolean isEnabled() {
        if (host == null) {
            return true;
        }
        return host.isSlotEnabled(idx);
    }

    public int getX() {
        return this.defX;
    }

    public int getY() {
        return this.defY;
    }

    private boolean isDisplay() {
        return this.isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    FoxBaseContainer getContainer() {
        return this.myContainer;
    }

    public void setContainer(FoxBaseContainer myContainer) {
        this.myContainer = myContainer;
    }

}
