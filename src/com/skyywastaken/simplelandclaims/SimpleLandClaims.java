package com.skyywastaken.simplelandclaims;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import com.skyywastaken.simplelandclaims.command.SLCCommand;
import com.skyywastaken.simplelandclaims.listener.MobListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class SimpleLandClaims extends JavaPlugin {
    private ClaimTracker CLAIM_TRACKER;
    private File configLocation;

    @Override
    public void onEnable() {
        this.configLocation = new File(this.getDataFolder(), "claims.json");
        initializeClaimTracker();
        this.getCommand("slc").setExecutor(new SLCCommand(CLAIM_TRACKER));
        getServer().getPluginManager().registerEvents(new MobListener(CLAIM_TRACKER), this);
    }

    @Override
    public void onDisable() {
        saveClaims();
    }

    private void initializeClaimTracker() {
        getDataFolder().mkdirs();
        if (this.configLocation.exists()) {
            Gson gson = new Gson();
            ClaimTracker claimTracker;
            try {
                System.out.println("trying to load gson");
                claimTracker = gson.fromJson(new FileReader(this.configLocation), ClaimTracker.class);
                System.out.println("loaded");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                this.CLAIM_TRACKER = new ClaimTracker();
                return;
            }
            if (claimTracker == null) {
                System.out.println("class is null.");
                this.CLAIM_TRACKER = new ClaimTracker();
                return;
            }
            System.out.println("setting tracker");
            this.CLAIM_TRACKER = claimTracker;
        } else {
            this.CLAIM_TRACKER = new ClaimTracker();
        }
    }

    private void saveClaims() {
        Gson newGson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter(this.configLocation);
            newGson.toJson(this.CLAIM_TRACKER, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
