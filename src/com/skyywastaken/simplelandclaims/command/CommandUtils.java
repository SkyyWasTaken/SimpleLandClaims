package com.skyywastaken.simplelandclaims.command;

import com.skyywastaken.simplelandclaims.claim.tracking.LandClaim;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class CommandUtils {
    public static void playSuccessSound(CommandSender sender) {
        if (sender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, Float.MAX_VALUE, 2);
        }
    }

    public static String getClaimsStringFromClaims(LinkedList<LandClaim> claims) {
        StringBuilder list = new StringBuilder();
        list.append(ChatColor.AQUA).append(ChatColor.BOLD).append("CLAIM LIST").append("\n");
        int i = 1;
        for (LandClaim claim : claims) {
            list.append(ChatColor.RED).append(i++).append(". ")
                    .append(ChatColor.GREEN).append("(")
                    .append(ChatColor.LIGHT_PURPLE).append(claim.getPosOne().x())
                    .append(ChatColor.GREEN).append(", ")
                    .append(ChatColor.LIGHT_PURPLE).append(claim.getPosOne().z())
                    .append(ChatColor.GREEN).append(") - (")
                    .append(ChatColor.LIGHT_PURPLE).append(claim.getPosTwo().x())
                    .append(ChatColor.GREEN).append(", ")
                    .append(ChatColor.LIGHT_PURPLE).append(claim.getPosTwo().z())
                    .append(ChatColor.GREEN).append("), O: ")
                    .append(ChatColor.AQUA).append(Bukkit.getServer().getOfflinePlayer(claim.getOwner()).getName())
                    .append(ChatColor.GREEN).append(", MG: ")
                    .append(claim.isMobGriefingDisabled() ? ChatColor.AQUA + "NO" : ChatColor.DARK_RED + "YES")
                    .append(ChatColor.RESET).append("\n");
        }
        return list.toString().trim();
    }
}
