package foxiwhitee.FoxLib.tile.inventory;

public enum InvOperation {
    decreaseStackSize,
    increasingStackSize,
    setInventorySlotContents,
    markDirty;

    private InvOperation() {
    }
}
