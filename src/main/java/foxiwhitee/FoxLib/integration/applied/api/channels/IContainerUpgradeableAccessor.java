package foxiwhitee.FoxLib.integration.applied.api.channels;

import appeng.api.config.RedstoneMode;
import appeng.api.implementations.IUpgradeableHost;

public interface IContainerUpgradeableAccessor {
    void callSetRedStoneMode(RedstoneMode paramRedstoneMode);

    IUpgradeableHost callGetUpgradeable();
}
