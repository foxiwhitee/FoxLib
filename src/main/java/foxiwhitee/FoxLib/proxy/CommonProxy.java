package foxiwhitee.FoxLib.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.client.render.StaticRenderHandler;
import foxiwhitee.FoxLib.config.ConfigHandler;
import foxiwhitee.FoxLib.integration.IntegrationLoader;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.recipes.RecipesHandler;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxLib.utils.handler.GuiHandlerRegistry;
import foxiwhitee.FoxLib.utils.helpers.GuiHandler;
import net.minecraft.item.Item;

public class CommonProxy {
    public static final Item PRODUCTIVITY_CARDS = new ItemProductivityCard("productivityCard");

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfigs(event);
        RecipesHandler.loadRecipesLocations(event);
        GuiHandlerRegistry.registerGuiHandlers(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(FoxLib.instance, new GuiHandler());
        IntegrationLoader.preInit(event);
        StaticRenderHandler.loadStaticRenderData(event);

        RegisterUtils.registerItem(PRODUCTIVITY_CARDS);
    }

    public void init(FMLInitializationEvent event) {
        IntegrationLoader.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        NetworkManager.instance = new NetworkManager("FoxLib");
        IntegrationLoader.postInit(event);
        RecipesHandler.init();
    }
}
