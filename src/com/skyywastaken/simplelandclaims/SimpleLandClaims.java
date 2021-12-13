package com.skyywastaken.simplelandclaims;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.SLCCommand;
import com.skyywastaken.simplelandclaims.listener.MobListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleLandClaims extends JavaPlugin {
    @Override
    public void onEnable() {
        ClaimTracker claimTracker = new ClaimTracker();
        this.getCommand("slc").setExecutor(new SLCCommand(claimTracker));
        getServer().getPluginManager().registerEvents(new MobListener(claimTracker), this);
    }

    @Override
    public void onDisable() {

    }
}
