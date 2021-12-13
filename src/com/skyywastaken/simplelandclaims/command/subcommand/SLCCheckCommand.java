package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.claim.tracking.LandClaim;
import com.skyywastaken.simplelandclaims.command.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
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
        if(commandSender instanceof Player player) {
            if (this.CLAIM_TRACKER.posIsInClaim(player.getLocation())) {
                LinkedList<LandClaim> claimList = this.CLAIM_TRACKER.getLandClaimsAtPosition(player.getLocation());
                commandSender.sendMessage(CommandUtils.getClaimsStringFromClaims(claimList));
            } else {
                player.sendMessage("There are no claims at this position!");
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

    @Override
    public String getPermission() {
        return "slc.check";
    }
}
