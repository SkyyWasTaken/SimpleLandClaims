package com.skyywastaken.simplelandclaims.claim.tracking;

import com.skyywastaken.simplelandclaims.claim.creation.ClaimHelper;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ClaimTracker {
    private final transient ClaimHelper claimCreationHelper = new ClaimHelper();
    private final LinkedList<LandClaim> CLAIMS = new LinkedList<>();

    public void removeClaim(LandClaim landClaim) {
        this.CLAIMS.remove(landClaim);
    }

    public boolean posIsInMobProtectedClaim(Location location) {
        for (LandClaim currentClaim : CLAIMS) {
            if (currentClaim.isMobGriefingDisabled() && currentClaim.positionIsInClaim(location)) {
                return true;
            }
        }
        return false;
    }

    public ClaimHelper getClaimCreationHelper() {
        return this.claimCreationHelper;
    }

    public LinkedList<LandClaim> getClaimsByOwner(UUID ownerUUID) {
        var claims = new LinkedList<LandClaim>();
        for (LandClaim currentClaim : this.CLAIMS) {
            if (currentClaim.getOwner() == ownerUUID) {
                claims.add(currentClaim);
            }
        }
        return claims;
    }

    public boolean posIsInClaim(Location location) {
        for (LandClaim currentClaim : CLAIMS) {
            if (currentClaim.positionIsInClaim(location)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<LandClaim> getLandClaimsAtPosition(Location locationToCheck) {
        var claims = new LinkedList<LandClaim>();
        for (LandClaim claim : this.CLAIMS) {
            if (claim.positionIsInClaim(locationToCheck)) {
                claims.add(claim);
            }
        }
        return claims;
    }

    public void createClaim(UUID newOwner, Location location1, Location location2) {
        this.CLAIMS.add(new LandClaim(newOwner, location1, location2));
    }

    public boolean playerHasTwentyOrMoreClaims(UUID player) {
        int claimCount = 0;
        for (LandClaim claim : this.CLAIMS) {
            if (claim.getOwner().equals(player)) {
                claimCount++;
            }
        }
        return claimCount >= 20;
    }
}
