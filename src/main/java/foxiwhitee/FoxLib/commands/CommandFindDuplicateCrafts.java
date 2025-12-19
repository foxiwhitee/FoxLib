package foxiwhitee.FoxLib.commands;

import foxiwhitee.FoxLib.utils.FindDuplicateCraftsScript;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandFindDuplicateCrafts extends CommandBase {
    @Override
    public String getCommandName() {
        return "findDuplicateCrafts";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        new FindDuplicateCraftsScript().findReverseCrafts();
    }
}
