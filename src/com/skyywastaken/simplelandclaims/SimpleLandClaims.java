package com.skyywastaken.simplelandclaims;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.SLCCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleLandClaims extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("slc").setExecutor(new SLCCommand(new ClaimTracker()));
    }

    @Override
    public void onDisable() {

    }
}
