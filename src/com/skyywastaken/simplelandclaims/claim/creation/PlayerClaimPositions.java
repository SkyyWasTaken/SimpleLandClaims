package com.skyywastaken.simplelandclaims.claim.creation;

import org.bukkit.Location;

public class PlayerClaimPositions {
    private Location pos1 = null;
    private Location pos2 = null;

    boolean hasBothPositions() {
        return pos1 != null && pos2 != null;
    }

    Location getPos1() {
        return pos1;
    }

    void setPos1(Location passedLocation) {
        if (passedLocation.getWorld() == null) {
            return;
        }
        this.pos1 = passedLocation;
    }

    Location getPos2() {
        return pos2;
    }

    void setPos2(Location passedLocation) {
        this.pos2 = passedLocation;
    }

    int getWidth() {
        return Math.max(pos1.getBlockX(), pos2.getBlockX()) - Math.min(pos1.getBlockX(), pos2.getBlockX());
    }

    int getDepth() {
        return Math.max(pos1.getBlockZ(), pos2.getBlockZ()) - Math.min(pos1.getBlockZ(), pos2.getBlockZ());
    }

    boolean bothPositionsAreInSameDimension() {
        if (this.pos1.getWorld() == null || this.pos2.getWorld() == null) {
            return false;
        }
        return this.pos1.getWorld().getEnvironment() == this.pos2.getWorld().getEnvironment();
    }
}
