package foxiwhitee.FoxLib.mixins.ae2;

import appeng.api.implementations.parts.IPartCable;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import appeng.parts.AEBasePart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AEBasePart.class, remap = false)
public abstract class AEBasePartMixin {

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "appeng/me/helpers/AENetworkProxy"))
    public AENetworkProxy replaceAENetworkProxy(IGridProxyable te, String nbtName, ItemStack visual, boolean inWorld) {
        return new AENetworkProxy(te, nbtName, visual, te instanceof IPartCable);
    }
}
