package com.skyywastaken.simplelandclaims.claim.tracking;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class LandClaim {
    private final UUID owner;
    private ArrayList<UUID> members;
    private ClaimPosition pos1;
    private ClaimPosition pos2;
    private boolean mobGriefingDisabled = false;

    public LandClaim(UUID passedOwner, Location pos1, Location pos2) {
        this.owner = passedOwner;
        setPositionsFromPos(pos1, pos2);
    }

    private void setPositionsFromPos(Location pos1, Location pos2) {
        if(pos1.getWorld() == null || pos1.getWorld() == null) {
            return;
        }
        int lowerX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int higherX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int lowerZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int higherZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        this.pos1 = new ClaimPosition(lowerX, lowerZ, pos1.getWorld().getEnvironment());
        this.pos2 = new ClaimPosition(higherX, higherZ, pos1.getWorld().getEnvironment());
    }

    public ClaimPosition getPosOne() {
        return this.pos1;
    }

    public ClaimPosition getPosTwo() {
        return this.pos2;
    }

    public boolean positionIsInClaim(Location location) {
        if (location.getWorld() == null) {
            return false;
        }
        return location.getWorld().getEnvironment() == pos1.dimension()
                && (location.getBlockX() >= pos1.x() && location.getBlockZ() >= pos1.z())
                && (location.getBlockX() <= pos2.x() && location.getBlockZ() <= pos2.z());
    }

    public UUID getOwner() {
        return this.owner;
    }

    public boolean isMobGriefingDisabled() {
        return mobGriefingDisabled;
    }

    public void toggleMobGriefing() {
        this.mobGriefingDisabled = !this.mobGriefingDisabled;
    }
}
