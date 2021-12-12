package com.skyywastaken.simplelandclaims.command;

import com.skyywastaken.simplelandclaims.command.subcommand.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SLCCommand implements CommandExecutor, TabCompleter {
    private final HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String subcommand, String[] args) {
        if(subCommandHashMap.containsKey(subcommand)) {
            subCommandHashMap.get(subcommand).executeCommand(commandSender, command, args);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.singletonList("");
    }
}
