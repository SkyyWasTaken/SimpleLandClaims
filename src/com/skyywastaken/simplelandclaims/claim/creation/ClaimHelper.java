package com.skyywastaken.simplelandclaims.claim.creation;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class ClaimHelper {
    private final transient HashMap<UUID, PlayerClaimPositions> potentialClaimPositions = new HashMap<>();

    public void addPosOneForPlayer(UUID playerUUID, Location position) {
        checkForPlayerAndAdd(playerUUID);
        this.potentialClaimPositions.get(playerUUID).setPos1(position);
    }

    public void addPosTwoForPlayer(UUID playerUUID, Location passedLocation) {
        checkForPlayerAndAdd(playerUUID);
        this.potentialClaimPositions.get(playerUUID).setPos2(passedLocation);
    }

    public boolean claimIsTooWide(UUID playerUUID) {
        return this.potentialClaimPositions.get(playerUUID).getWidth() > 500;
    }

    public boolean claimIsTooDeep(UUID playerUUID) {
        return this.potentialClaimPositions.get(playerUUID).getDepth() > 500;
    }

    public Location getPosOneForPlayer(UUID playerToGet) {
        return this.potentialClaimPositions.get(playerToGet).getPos1();
    }

    public Location getPosTwoForPlayer(UUID playerToGet) {
        return this.potentialClaimPositions.get(playerToGet).getPos2();
    }

    public boolean playerHasBothPositions(UUID queriedPlayer) {
        return this.potentialClaimPositions.get(queriedPlayer).hasBothPositions();
    }

    private void checkForPlayerAndAdd(UUID playerUUID) {
        if (!this.potentialClaimPositions.containsKey(playerUUID)) {
            this.potentialClaimPositions.put(playerUUID, new PlayerClaimPositions());
        }
    }

    public boolean bothPositionsAreInSameDimension(UUID playerUUID) {
        return this.potentialClaimPositions.get(playerUUID).bothPositionsAreInSameDimension();
    }
}
