package com.skyywastaken.simplelandclaims.claim;

public class PlayerClaimPositions {
    private ClaimPosition pos1 = null;
    private ClaimPosition pos2 = null;

    boolean hasPos1() {
        return pos1 != null;
    }

    boolean hasPos2() {
        return pos2 != null;
    }

    void setPos1(int x, int z) {
        this.pos1 = new ClaimPosition(x, z);
    }

    void setPos2(int x, int z) {
        this.pos2 = new ClaimPosition(x, z);
    }
}
