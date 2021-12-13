package com.skyywastaken.simplelandclaims.command.subcommand;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.claim.tracking.LandClaim;
import com.skyywastaken.simplelandclaims.command.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SLCToggleMobGriefingCommand implements SubCommand {
    private final ClaimTracker CLAIM_TRACKER;
    private final HashMap<UUID, LinkedList<LandClaim>> CACHED_CLAIMS = new HashMap<>();

    public SLCToggleMobGriefingCommand(ClaimTracker passedTracker) {
        this.CLAIM_TRACKER = passedTracker;
    }

    @Override
    public void executeCommand(CommandSender commandSender, Command command, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                handleListClaims(player);
            } else {
                handleToggleMobGriefing(player, args[0]);
            }
        } else {
            commandSender.sendMessage("You need to be a player to use this command!");
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private void handleListClaims(Player passedPlayer) {
        this.CACHED_CLAIMS.remove(passedPlayer.getUniqueId());
        LinkedList<LandClaim> landClaims = this.CLAIM_TRACKER.getLandClaimsAtPosition(passedPlayer.getLocation());
        this.CACHED_CLAIMS.put(passedPlayer.getUniqueId(), landClaims);
        String builder = ChatColor.RESET + CommandUtils.getClaimsStringFromClaims(landClaims)
                + "\n" + ChatColor.YELLOW + "Type /slc togglemobgriefing (number) to toggle mob griefing on or off!";
        passedPlayer.sendMessage(builder);
    }

    @Override
    public String getName() {
        return "togglemobgriefing";
    }

    @Override
    public String getPermission() {
        return "slc.togglemobgriefing";
    }

    private void handleToggleMobGriefing(Player player, String enteredText) {
        if (!this.CACHED_CLAIMS.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You need to list the claims first! Type " +
                    "'/slc togglemobgriefing' to get started.");
            return;
        }
        int typedInt;
        try {
            typedInt = Integer.parseInt(enteredText);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + enteredText + " wasn't an option!");
            return;
        }
        LandClaim selectedClaim = this.CACHED_CLAIMS.get(player.getUniqueId()).get(typedInt - 1);
        if (selectedClaim.getOwner() != player.getUniqueId()
                && !player.hasPermission("slc.togglemobgriefing.others")) {
            player.sendMessage(ChatColor.RED + "You can't toggle mob griefing on someone else's claim!");
            return;
        } else if (selectedClaim.getOwner() == player.getUniqueId()
                && !player.hasPermission("slc.togglemobgriefing.self")) {
            player.sendMessage(ChatColor.RED
                    + "You aren't allowed to toggle mob griefing on your own claims!");
            return;
        }
        selectedClaim.toggleMobGriefing();
        player.sendMessage(ChatColor.GREEN + "Success! Mob griefing is now " +
                (selectedClaim.isMobGriefingDisabled() ? "Disabled" : "Enabled") + " on claim " + typedInt + "!");
    }
}
