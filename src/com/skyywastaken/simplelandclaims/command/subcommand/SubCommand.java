package com.skyywastaken.simplelandclaims.command.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    void executeCommand(CommandSender commandSender, Command command, String[] args);

    List<String> getTabCompletions(CommandSender commandSender, Command command, String[] strings);

    String getName();

    String getPermission();
}
