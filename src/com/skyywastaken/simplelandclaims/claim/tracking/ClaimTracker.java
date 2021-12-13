package com.skyywastaken.simplelandclaims.claim.tracking;

import com.skyywastaken.simplelandclaims.claim.creation.ClaimHelper;
import org.bukkit.Location;

import java.util.LinkedList;
import java.util.UUID;

public class ClaimTracker {
    private final transient ClaimHelper claimCreationHelper = new ClaimHelper();
    private LinkedList<LandClaim> landClaims = new LinkedList<>();

    public void loadClaims(LinkedList<LandClaim> passedClaims) {
        if (this.landClaims == null) {
            this.landClaims = passedClaims;
        }
    }

    public void freshStart() {
        if (this.landClaims == null) {
            this.landClaims = new LinkedList<>();
        }
    }

    public LinkedList<LandClaim> getClaims() {
        return this.landClaims;
    }

    public void removeClaim(LandClaim landClaim) {
        this.landClaims.remove(landClaim);
    }

    public boolean posIsInMobProtectedClaim(Location location) {
        for (LandClaim currentClaim : landClaims) {
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
        for (LandClaim currentClaim : this.landClaims) {
            if (currentClaim.getOwner().equals(ownerUUID)) {
                claims.add(currentClaim);
            }
        }
        return claims;
    }

    public boolean posIsInClaim(Location location) {
        for (LandClaim currentClaim : landClaims) {
            if (currentClaim.positionIsInClaim(location)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<LandClaim> getLandClaimsAtPosition(Location locationToCheck) {
        var claims = new LinkedList<LandClaim>();
        for (LandClaim claim : this.landClaims) {
            if (claim.positionIsInClaim(locationToCheck)) {
                claims.add(claim);
            }
        }
        return claims;
    }

    public void createClaim(UUID newOwner, Location location1, Location location2) {
        this.landClaims.add(new LandClaim(newOwner, location1, location2));
    }

    public boolean playerHasTwentyOrMoreClaims(UUID player) {
        int claimCount = 0;
        for (LandClaim claim : this.landClaims) {
            if (claim.getOwner().equals(player)) {
                claimCount++;
            }
        }
        return claimCount >= 20;
    }
}
