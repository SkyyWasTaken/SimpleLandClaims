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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SLCListClaimsCommand implements SubCommand {
    private final ClaimTracker CLAIM_TRACKER;

    public SLCListClaimsCommand(ClaimTracker claimTracker) {
        this.CLAIM_TRACKER = claimTracker;
    }

    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        var landClaimList = new LinkedList<LandClaim>();
        boolean runOnSelf;
        if (args.length == 0) {
            if (!(commandSender instanceof Player player)) {
                commandSender.sendMessage("I don't know who you are! Either join as a player or specify who you want"
                        + " to list claims for.");
                return;
            } else if (!commandSender.hasPermission("slc.listclaims.self")) {
                commandSender.sendMessage(ChatColor.RED + "You don't have permission to check your own claims!");
                return;
            }
            runOnSelf = true;
            landClaimList.addAll(this.CLAIM_TRACKER.getClaimsByOwner(player.getUniqueId()));
        } else {
            if (!commandSender.hasPermission("slc.listclaims.others")) {
                commandSender.sendMessage(ChatColor.RED + "You don't have permission to check others' claims!");
                return;
            }
            runOnSelf = false;
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                OfflinePlayer offlinePlayer;
                try {
                    offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(args[0]));
                } catch (IllegalArgumentException e) {
                    commandSender.sendMessage(ChatColor.RED + args[0] + " is not an online player or UUID!");
                    return;
                }
                landClaimList.addAll(this.CLAIM_TRACKER.getClaimsByOwner(offlinePlayer.getUniqueId()));
            } else {
                landClaimList.addAll(this.CLAIM_TRACKER.getClaimsByOwner(player.getUniqueId()));
            }
        }
        if (landClaimList.size() == 0) {
            sendNoClaimMessage(commandSender, runOnSelf);
            return;
        }
        commandSender.sendMessage(CommandUtils.getClaimsStringFromClaims(landClaimList));
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String[] args) {
        if (args.length == 1 && commandSender.hasPermission("slc.listclaims.others")) {
            return null;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String getName() {
        return "listclaims";
    }

    @Override
    public String getPermission() {
        return "slc.listclaims";
    }

    private void sendNoClaimMessage(CommandSender passedSender, boolean wasRunOnSelf) {
        passedSender.sendMessage(ChatColor.RED + (wasRunOnSelf ? "You don't" : "This player doesn't")
                + " have any claims");
    }
}
