package foxiwhitee.FoxLib.commands;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

public class CommandHand extends CommandBase {

    @Override
    public String getCommandName() {
        return "hand";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return StatCollector.translateToLocalFormatted("tooltip.command.hand.description", "/hand");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.command.hand.onlyForPlayers")));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        ItemStack held = player.getHeldItem();

        if (held == null) {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.command.hand.null")));
            return;
        }

        Item item = held.getItem();
        String modid = GameData.getItemRegistry().getNameForObject(item).split(":")[0];
        String name = GameData.getItemRegistry().getNameForObject(item).split(":")[1];
        int meta = held.getItemDamage();

        String itemString = meta == 0
            ? "<" + modid + ":" + name + ">"
            : "<" + modid + ":" + name + "." + meta + ">";

        sender.addChatMessage(new ChatComponentText("§a" + itemString));

        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(itemString), null);
        } catch (Exception e) {
        }

        if (held.hasTagCompound()) {
            NBTTagCompound tag = held.getTagCompound();
            String nbtString = tag.toString();
            sender.addChatMessage(new ChatComponentText("§dNBT: §7" + nbtString));
        }

        int[] oreIDs = OreDictionary.getOreIDs(held);
        if (oreIDs.length > 0) {
            sender.addChatMessage(new ChatComponentText("§bOreDictionary:"));
            Arrays.stream(oreIDs)
                .mapToObj(OreDictionary::getOreName)
                .forEach(name1 -> sender.addChatMessage(new ChatComponentText(" §7- " + name1)));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
