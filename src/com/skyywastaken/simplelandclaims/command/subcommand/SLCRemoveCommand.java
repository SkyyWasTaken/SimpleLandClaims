package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.claim.tracking.LandClaim;
import com.skyywastaken.simplelandclaims.command.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SLCRemoveCommand implements SubCommand {
    private final ClaimTracker CLAIM_TRACKER;
    private final HashMap<UUID, LinkedList<LandClaim>> CACHED_CLAIM_RESULTS = new HashMap<>();
    private final HashMap<UUID, LandClaim> CONFIRMATION_CACHE = new HashMap<>();

    public SLCRemoveCommand(ClaimTracker claimTracker) {
        this.CLAIM_TRACKER = claimTracker;
    }

    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                handleRemoveList(player);
            } else {
                if (args[0].equals("confirm")) {
                    handleConfirmation(player);
                } else {
                    tryOptionSelect(player, args[0]);
                }
            }
        } else {
            commandSender.sendMessage("You need to be a player to use this command.");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String[] args) {
        if (!(commandSender instanceof Player player) || args.length != 1) {
            return new ArrayList<>();
        }
        var possibleCompletionList = new ArrayList<String>();
        UUID playerUUID = player.getUniqueId();
        if (this.CACHED_CLAIM_RESULTS.containsKey(playerUUID)) {
            int i = 1;
            for (LandClaim currentClaim : this.CACHED_CLAIM_RESULTS.get(playerUUID)) {
                if (currentClaim.getOwner() == playerUUID && commandSender.hasPermission("slc.removeclaim.self")
                        || currentClaim.getOwner() != playerUUID
                        && commandSender.hasPermission("slc.removeclaim.others")) {
                    possibleCompletionList.add(Integer.toString(i));
                }
                i++;
            }
        }
        if (this.CONFIRMATION_CACHE.containsKey(player.getUniqueId())) {
            possibleCompletionList.add("confirm");
        }
        var returnList = new ArrayList<String>();
        StringUtil.copyPartialMatches(args[0], possibleCompletionList, returnList);
        return returnList;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getPermission() {
        return "slc.removeclaim";
    }

    private void handleRemoveList(Player sender) {
        Location playerLocation = sender.getLocation();
        StringBuilder builder = new StringBuilder();
        LinkedList<LandClaim> claims = this.CLAIM_TRACKER.getLandClaimsAtPosition(playerLocation);
        if (claims.size() == 0) {
            sender.sendMessage(ChatColor.RED + "There are no claims here!");
            return;
        }
        builder.append(CommandUtils.getClaimsStringFromClaims(claims));
        builder.append(ChatColor.BOLD).append(ChatColor.LIGHT_PURPLE)
                .append("Type /slc remove (number) to remove a claim!");
        sender.sendMessage(builder.toString());
        this.CACHED_CLAIM_RESULTS.put(sender.getUniqueId(), claims);
    }

    private void handleConfirmation(Player player) {
        if (!this.CONFIRMATION_CACHE.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You haven't selected a claim to remove! "
                    + "Type '/slc remove' to try removing a claim!");
            return;
        }
        LandClaim claimToRemove = this.CONFIRMATION_CACHE.get(player.getUniqueId());
        this.CONFIRMATION_CACHE.remove(player.getUniqueId());
        this.CLAIM_TRACKER.removeClaim(claimToRemove);
        player.sendMessage(ChatColor.GREEN + "Claim removed successfully!");
        CommandUtils.playSuccessSound(player);
    }

    private void tryOptionSelect(Player player, String enteredText) {
        int selectedInt;
        try {
            selectedInt = Integer.parseInt(enteredText);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Hmm. That doesn't seem right! Try running "
                    + "'/slc remove' with no arguments!");
            return;
        }
        if (!this.CACHED_CLAIM_RESULTS.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "I don't know which claim you're talking about! Type /slc "
                    + "remove to list claims at your position.");
            return;
        }
        LandClaim claim = this.CACHED_CLAIM_RESULTS.get(player.getUniqueId()).get(selectedInt - 1);
        if (claim.getOwner() != player.getUniqueId() && !player.hasPermission("slc.removeclaim.others")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to remove someone else's claim!");
            return;
        } else if (claim.getOwner() == player.getUniqueId() && !player.hasPermission("slc.removeclaim.self")) {
            player.sendMessage("You don't have permission to remove your own claims!");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "Successfully selected #" + selectedInt + "! Type /slc remove "
                + "confirm to remove the claim.");
        this.CONFIRMATION_CACHE.put(player.getUniqueId(), claim);
    }
}
