package foxiwhitee.FoxLib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderStaticBlock<T extends TileEntity> extends TileEntitySpecialRendererObjWrapper<T> implements IItemRenderer {
    private final IModelCustom model;
    private final StaticRender render;

    public RenderStaticBlock(StaticRender render) {
        super((Class<T>) render.tile(), new ResourceLocation(render.modID(), render.model()), new ResourceLocation(render.modID(), render.texture()));
        this.render = render;
        model = AdvancedModelLoader.loadModel(new ResourceLocation(render.modID(), render.model()));
        createList("all");
    }

    public void renderAt(T tileEntity, double x, double y, double z, double f) {
        GL11.glPushMatrix();
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        bindTexture();
        GL11.glTranslated(0.5D, 0.0D, 0.5D);
        renderPart("all");
        GL11.glDisable(3008);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(0.0D, -0.5D, 0.0D);
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        switch (type) {
            case ENTITY:
                GL11.glScaled(render.itemRender().entity().scale().x(), render.itemRender().entity().scale().y(), render.itemRender().entity().scale().z());
                GL11.glTranslated(render.itemRender().entity().position().x(), render.itemRender().entity().position().y(), render.itemRender().entity().position().z());
                break;
            case EQUIPPED:
                GL11.glScaled(render.itemRender().equipped().scale().x(), render.itemRender().equipped().scale().y(), render.itemRender().equipped().scale().z());
                GL11.glTranslated(render.itemRender().equipped().position().x(), render.itemRender().equipped().position().y(), render.itemRender().equipped().position().z());
                break;
            case INVENTORY:
                GL11.glScaled(render.itemRender().inventory().scale().x(), render.itemRender().inventory().scale().y(), render.itemRender().inventory().scale().z());
                GL11.glTranslated(render.itemRender().inventory().position().x(), render.itemRender().inventory().position().y(), render.itemRender().inventory().position().z());
                break;
            case FIRST_PERSON_MAP:
                GL11.glScaled(render.itemRender().firstPersonMap().scale().x(), render.itemRender().firstPersonMap().scale().y(), render.itemRender().firstPersonMap().scale().z());
                GL11.glTranslated(render.itemRender().firstPersonMap().position().x(), render.itemRender().firstPersonMap().position().y(), render.itemRender().firstPersonMap().position().z());
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glScaled(render.itemRender().equippedFirstPerson().scale().x(), render.itemRender().equippedFirstPerson().scale().y(), render.itemRender().equippedFirstPerson().scale().z());
                GL11.glTranslated(render.itemRender().equippedFirstPerson().position().x(), render.itemRender().equippedFirstPerson().position().y(), render.itemRender().equippedFirstPerson().position().z());
                break;
        }
        (Minecraft.getMinecraft()).renderEngine.bindTexture(this.getTexture());
        this.model.renderAll();
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
