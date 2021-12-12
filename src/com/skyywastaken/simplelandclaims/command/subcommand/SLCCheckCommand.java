package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SLCCheckCommand implements SubCommand {
    private final ClaimTracker CLAIM_TRACKER;
    public SLCCheckCommand(ClaimTracker claimTracker) {
        this.CLAIM_TRACKER = claimTracker;
    }
    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        if(!commandSender.hasPermission("slc.check")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
        }
        if(commandSender instanceof Player player) {
            if(this.CLAIM_TRACKER.posIsInClaim(player.getLocation())) {
                for(UUID uuid : this.CLAIM_TRACKER.getOwnersFromLocation(player.getLocation())) {
                    OfflinePlayer owner = Bukkit.getServer().getOfflinePlayer(uuid);
                    commandSender.sendMessage(Objects.requireNonNull(owner.getName()));
                }
            }
        } else {
            commandSender.sendMessage("You can only use this command as a player.");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.singletonList("");
    }

    @Override
    public String getName() {
        return "check";
    }
}
