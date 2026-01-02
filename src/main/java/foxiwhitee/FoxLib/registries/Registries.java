package foxiwhitee.FoxLib.registries;

import foxiwhitee.FoxLib.api.registries.IJsonRecipeRegister;
import foxiwhitee.FoxLib.api.registries.IPacketRegister;
import foxiwhitee.FoxLib.api.registries.IRegistries;

public class Registries implements IRegistries {
    private final IJsonRecipeRegister jsonRecipeRegister = new JsonRecipeRegister();
    private final IPacketRegister packetRegister = new PacketRegister();

    @Override
    public IJsonRecipeRegister registerJsonRecipe() {
        return jsonRecipeRegister;
    }

    @Override
    public IPacketRegister registerPacket() {
        return packetRegister;
    }

}
