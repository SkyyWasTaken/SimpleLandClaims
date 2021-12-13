package com.skyywastaken.simplelandclaims.command;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCCheckCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCClaimCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCListClaimsCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCPosOneCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCPosTwoCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCRemoveCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCToggleMobGriefingCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SLCCommand implements CommandExecutor, TabCompleter {
    private final HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();
    private final ClaimTracker CLAIM_TRACKER;

    public SLCCommand(ClaimTracker claimTracker) {
        this.CLAIM_TRACKER = claimTracker;
        registerCommands();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String subcommand, String[] args) {
        if(args.length < 1) {
            return true;
        }
        if(subCommandHashMap.containsKey(args[0])) {
            SubCommand subCommand = subCommandHashMap.get(args[0]);
            if (!commandSender.hasPermission(subCommand.getPermission())) {
                commandSender.sendMessage(ChatColor.BOLD + "" + ChatColor.RED
                        + "You do not have permission to use this command.");
                return true;
            }
            subCommandHashMap.get(args[0]).executeCommand(commandSender, command, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 0) {
            return null;
        } else if (args.length == 1) {
            var allowedCommands = new ArrayList<String>();
            for (Map.Entry<String, SubCommand> currentSubCommand : this.subCommandHashMap.entrySet()) {
                if (commandSender.hasPermission(currentSubCommand.getValue().getPermission())) {
                    allowedCommands.add(currentSubCommand.getKey());
                }
            }
            StringUtil.copyPartialMatches(args[0], allowedCommands, tabCompletions);
            return tabCompletions;
        } else if (this.subCommandHashMap.containsKey(args[0])) {
            return this.subCommandHashMap.get(args[0]).getTabCompletions(commandSender, command,
                    Arrays.copyOfRange(args, 1, args.length));
        } else {
            return new ArrayList<>();
        }
    }

    public void registerCommand(SubCommand subCommand) {
        this.subCommandHashMap.put(subCommand.getName(), subCommand);
    }

    private void registerCommands() {
        registerCommand(new SLCPosOneCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCPosTwoCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCClaimCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCCheckCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCRemoveCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCToggleMobGriefingCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCListClaimsCommand(this.CLAIM_TRACKER));
    }
}
