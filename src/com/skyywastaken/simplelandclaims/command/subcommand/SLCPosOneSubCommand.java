package com.skyywastaken.simplelandclaims.command.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SLCPosOneSubCommand implements SubCommand {
    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {

    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
