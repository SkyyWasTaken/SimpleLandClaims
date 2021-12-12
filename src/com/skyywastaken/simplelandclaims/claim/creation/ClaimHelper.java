package com.skyywastaken.simplelandclaims.claim.creation;

import java.util.HashMap;
import java.util.UUID;

public class ClaimHelper {
    private final transient HashMap<UUID, PlayerClaimPositions> potentialClaimPositions = new HashMap<>();

    public void addPosOneForPlayer(UUID playerUUID, int x, int z) {
        checkForPlayerAndAdd(playerUUID);
        this.potentialClaimPositions.get(playerUUID).setPos1(x, z);
    }

    public void addPosTwoForPlayer(UUID playerUUID, int x, int z) {
        checkForPlayerAndAdd(playerUUID);
        this.potentialClaimPositions.get(playerUUID).setPos2(x, z);
    }

    private void checkForPlayerAndAdd(UUID playerUUID) {
        if(!this.potentialClaimPositions.containsKey(playerUUID)) {
            this.potentialClaimPositions.put(playerUUID, new PlayerClaimPositions());
        }
    }
}
