package foxiwhitee.FoxLib.mixins.ae2;

import appeng.api.config.RedstoneMode;
import appeng.api.implementations.IUpgradeableHost;
import appeng.container.implementations.ContainerUpgradeable;
import foxiwhitee.FoxLib.integration.applied.api.channels.IContainerUpgradeableAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerUpgradeable.class)
public abstract class ContainerUpgradeableMixin implements IContainerUpgradeableAccessor {

    @Shadow(remap = false)
    abstract void setRedStoneMode(RedstoneMode rsMode);

    @Shadow(remap = false)
    public abstract IUpgradeableHost getUpgradeable();

    public void callSetRedStoneMode(RedstoneMode mode) {
        setRedStoneMode(mode);
    }

    public IUpgradeableHost callGetUpgradeable() {
        return getUpgradeable();
    }
}
