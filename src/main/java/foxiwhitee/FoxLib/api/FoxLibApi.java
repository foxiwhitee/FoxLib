package foxiwhitee.FoxLib.api;

import foxiwhitee.FoxLib.api.registries.IRegistries;
import foxiwhitee.FoxLib.registries.Registries;

public class FoxLibApi implements IFoxLibApi {
    public static final IFoxLibApi instance = new FoxLibApi();
    private final IRegistries registries = new Registries();

    private FoxLibApi() {}

    @Override
    public IRegistries registries() {
        return registries;
    }
}
