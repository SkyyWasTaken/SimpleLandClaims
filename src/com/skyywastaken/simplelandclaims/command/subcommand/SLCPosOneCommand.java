package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SLCPosOneCommand implements SubCommand {
    private final ClaimTracker CLAIM_TRACKER;
    public SLCPosOneCommand(ClaimTracker tracker) {
        this.CLAIM_TRACKER = tracker;
    }
    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        System.out.println("yes");
        if(commandSender instanceof Player player) {
            Location playerLocation = player.getLocation();
            if(playerLocation.getWorld() == null) {
                return;
            }
            this.CLAIM_TRACKER.addProspectivePos1(player.getUniqueId(), playerLocation);
            commandSender.sendMessage(ChatColor.GREEN + "Successfully set pos1 to (" + playerLocation.getBlockX()
                    + ", " + playerLocation.getBlockZ() + "), dimension " + playerLocation.getWorld().getEnvironment());
        } else {
            commandSender.sendMessage("You can only use this command as a player!");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.singletonList("");
    }

    @Override
    public String getName() {
        return "pos1";
    }
}