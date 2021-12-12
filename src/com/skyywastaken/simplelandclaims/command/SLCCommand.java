package com.skyywastaken.simplelandclaims.command;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCCheckCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCClaimCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCPosOneCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SLCPosTwoCommand;
import com.skyywastaken.simplelandclaims.command.subcommand.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        System.out.println(args[0]);
        if(subCommandHashMap.containsKey(args[0])) {
            subCommandHashMap.get(args[0]).executeCommand(commandSender, command, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.singletonList("");
    }

    public void registerCommand(SubCommand subCommand) {
        this.subCommandHashMap.put(subCommand.getName(), subCommand);
    }

    private void registerCommands() {
        registerCommand(new SLCPosOneCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCPosTwoCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCClaimCommand(this.CLAIM_TRACKER));
        registerCommand(new SLCCheckCommand(this.CLAIM_TRACKER));
    }
}
