package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.creation.ClaimHelper;
import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SLCClaimCommand implements SubCommand{
    private final ClaimTracker CLAIM_TRACKER;
    public SLCClaimCommand(ClaimTracker passedTracker) {
        this.CLAIM_TRACKER = passedTracker;
    }
    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        if(commandSender instanceof Player player) {
            UUID newOwner;
            if(args.length > 0 && player.hasPermission("slc.claim.others")) {
                Player possibleTarget = Bukkit.getPlayer(args[0]);
                if(possibleTarget != null) {
                    newOwner = possibleTarget.getUniqueId();
                } else {
                    try {
                        newOwner = UUID.fromString(args[0]);
                        player.sendMessage(ChatColor.GREEN + "Successfully loaded UUID " + newOwner);
                    } catch(IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + args[0] + " either doesn't exist or is not online!");
                        return;
                    }
                }
            } else {
                if(commandSender.hasPermission("slc.claim.self")) {
                    newOwner = player.getUniqueId();
                } else {
                    commandSender.sendMessage("You aren't allowed to claim land for yourself!");
                    return;
                }
            }
            handleSuccessfulClaimAttempt(newOwner, player);
        } else {
            commandSender.sendMessage("You cannot use this command unless you're a player!");
        }
    }

    private void handleSuccessfulClaimAttempt(UUID newLandOwner, Player landClaimer) {
        if (!checkPreConditionsAndWarn(landClaimer, newLandOwner)) {
            return;
        }
        ClaimHelper claimCreationHelper = this.CLAIM_TRACKER.getClaimCreationHelper();
        Location pos1 = claimCreationHelper.getPosOneForPlayer(landClaimer.getUniqueId());
        Location pos2 = claimCreationHelper.getPosTwoForPlayer(landClaimer.getUniqueId());
        this.CLAIM_TRACKER.createClaim(newLandOwner, pos1, pos2);
        landClaimer.sendMessage(ChatColor.GREEN + "Land claimed successfully!");
        CommandUtils.playSuccessSound(landClaimer);
    }

    private boolean checkPreConditionsAndWarn(Player landClaimer, UUID owner) {
        ClaimHelper claimHelper = this.CLAIM_TRACKER.getClaimCreationHelper();
        if(!claimHelper.playerHasBothPositions(landClaimer.getUniqueId())) {
            landClaimer.sendMessage("You don't have both of your positions set! Use /slc pos1 and "
                    + "/slc pos2 to select an area!");
            return false;
        } else if (claimHelper.claimIsTooWide(landClaimer.getUniqueId())) {
            landClaimer.sendMessage("Your claim is too wide! Make the x length 500 blocks or less!");
            return false;
        } else if (claimHelper.claimIsTooDeep(landClaimer.getUniqueId())) {
            landClaimer.sendMessage("Your claim is too deep! Make the Z length 500 blocks or less!");
            return false;
        } else if(!claimHelper.bothPositionsAreInSameDimension(landClaimer.getUniqueId())) {
            landClaimer.sendMessage("Your claim spans multiple dimensions! That's definitely not allowed!");
            return false;
        } else if(this.CLAIM_TRACKER.playerHasTwentyOrMoreClaims(owner)) {
            landClaimer.sendMessage("You have twenty or more claims! Unclaim some land to claim more.");
            return false;
        }
        return true;
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String[] args) {
        if (args.length == 1 && commandSender.hasPermission("slc.claim.others")) {
            return null;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getPermission() {
        return "slc.claim";
    }
}
