package com.skyywastaken.simplelandclaims.claim.tracking;

import com.skyywastaken.simplelandclaims.claim.creation.ClaimHelper;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimTracker {
    private final transient ClaimHelper claimCreationHelper = new ClaimHelper();
    private final ArrayList<LandClaim> CLAIMS = new ArrayList<>();

    public boolean posIsInMobProtectedClaim(Location location) {
        for(LandClaim currentClaim : CLAIMS) {
            if(currentClaim.isMobGriefingDisabled() && currentClaim.positionIsInClaim(location)) {
                return true;
            }
        }
        return false;
    }

    public ClaimHelper getClaimCreationHelper() {
        return this.claimCreationHelper;
    }

    public boolean posIsInClaim(Location location) {
        for(LandClaim currentClaim : CLAIMS) {
            if(currentClaim.positionIsInClaim(location)) {
                return true;
            }
        }
        return false;
    }

    public void createClaim(UUID newOwner, Location location1, Location location2) {
        this.CLAIMS.add(new LandClaim(newOwner, location1, location2));
    }

    public boolean playerHasTwentyOrMoreClaims(UUID player) {
        int claimCount = 0;
        for(LandClaim claim : this.CLAIMS) {
            if(claim.getOwner().equals(player)) {
                claimCount++;
            }
        }
        return claimCount >= 20;
    }

    public List<UUID> getOwnersFromLocation(Location passedLocation) {
        ArrayList<UUID> owners = new ArrayList<>();
        for(LandClaim landClaim : this.CLAIMS) {
            if(landClaim.positionIsInClaim(passedLocation)) {
                owners.add(landClaim.getOwner());
            }
        }
        return owners;
    }
}
