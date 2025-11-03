package foxiwhitee.FoxLib.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.client.render.RenderStaticBlock;
import foxiwhitee.FoxLib.client.render.StaticRender;
import foxiwhitee.FoxLib.client.render.StaticRenderHandler;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        for (Map.Entry<Block, StaticRender> entry : StaticRenderHandler.STATIC_RENDER_DATA.entrySet()) {
            RegisterUtils.registerItemRenderer(Item.getItemFromBlock(entry.getKey()), new RenderStaticBlock<>(entry.getValue()));
            RegisterUtils.registerTileRenderer(entry.getValue().tile(), new RenderStaticBlock<>(entry.getValue()));
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
