package foxiwhitee.FoxLib.client.render;

import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StaticRenderHandler {
    public static final Map<Block, StaticRender> STATIC_RENDER_DATA = new HashMap<>();

    public static void loadStaticRenderData(FMLPreInitializationEvent event) {
        ASMDataTable asm = event.getAsmData();
        Set<ASMDataTable.ASMData> data = asm.getAll(StaticRender.class.getName());

        for (ASMDataTable.ASMData entry : data) {
            try {
                Class<?> clazz = Class.forName(entry.getClassName());
                Field field = clazz.getDeclaredField(entry.getObjectName());
                field.setAccessible(true);

                Object value = field.get(null);
                if (!(value instanceof Block)) {
                    System.err.println("[StaticRender] Поле " + field + " не є Block!");
                    continue;
                }
                Block block = (Block) value;

                StaticRender render = field.getAnnotation(StaticRender.class);

                STATIC_RENDER_DATA.put(block, render);
            } catch (Exception e) {
                System.err.println("[StaticRender] Не вдалося обробити " + entry.getClassName() + ": " + e);
            }
        }
    }
}
