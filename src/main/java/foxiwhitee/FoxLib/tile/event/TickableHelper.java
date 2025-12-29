package foxiwhitee.FoxLib.tile.event;

import foxiwhitee.FoxLib.tile.FoxBaseTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TickableHelper {
    private final List<TickableFunction> tasks = new ArrayList<>();

    public void init(List<TileEventHandler> handler) {
        for (TileEventHandler h : handler) {
            tasks.add(new TickableFunction(h));
        }
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void tick() {
        for (TickableFunction task : tasks) {
            if (task.isSleeping) continue;

            task.timer++;
            if (task.timer >= task.currentInterval) {
                task.timer = 0;
                invokeTask(task);
            }
        }
    }

    private void invokeTask(TickableFunction task) {
        try {
            TickRateModulation result = task.invoke();

            switch (result) {
                case SLEEP: task.isSleeping = true; break;
                case FASTER: task.currentInterval = Math.max(1, task.currentInterval - 1); break;
                case SLOWER: task.currentInterval = Math.min(100, task.currentInterval + 1); break;
                case URGENT: task.currentInterval = 1; break;
                case IDLE: task.currentInterval = 100; break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void awake(String name) {
        for (TickableFunction task : tasks) {
            if (task.isSleeping && task.triggerKey.equals(name)) {
                task.isSleeping = false;
                task.timer = 0;
                task.currentInterval = 1;
                break;
            }
        }
    }

    public void writeToNbt(NBTTagCompound tag) {
        NBTTagList taskList = new NBTTagList();
        for (TickableFunction task : tasks) {
            NBTTagCompound temp = new NBTTagCompound();
            temp.setString("name", task.triggerKey);
            temp.setInteger("interval", task.currentInterval);
            temp.setInteger("timer", task.timer);
            temp.setBoolean("sleep", task.isSleeping);
            taskList.appendTag(temp);
        }
        tag.setTag("tickTasks", taskList);
    }

    public void readFromNbt(NBTTagCompound tag, FoxBaseTile tile) {
        if (tag.hasKey("tickTasks")) {
            NBTTagList taskList = tag.getTagList("tickTasks", 10);
            for (int i = 0; i < taskList.tagCount(); i++) {
                NBTTagCompound taskTag = taskList.getCompoundTagAt(i);
                String name = taskTag.getString("name");

                Class<? extends FoxBaseTile> tileClass = tile.getClass();
                for (Method method : tileClass.getMethods()) {
                    TileEvent event = (TileEvent) method.getAnnotation(TileEvent.class);
                    if (event != null) {
                        TickableFunction task = new TickableFunction(new TileEventHandler(method, tile));
                        task.currentInterval = taskTag.getInteger("interval");
                        task.timer = taskTag.getInteger("timer");
                        task.isSleeping = taskTag.getBoolean("sleep");
                    }
                }
            }
        }
    }
}
