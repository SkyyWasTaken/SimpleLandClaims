package com.skyywastaken.simplelandclaims.command;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtils {
    public void playSuccessSound(CommandSender sender) {
        if(sender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, Float.MAX_VALUE, 2);
        }
    }
}
