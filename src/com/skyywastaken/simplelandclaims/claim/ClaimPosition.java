package com.skyywastaken.simplelandclaims.claim;

public record ClaimPosition(int x, int z) {
    public ClaimPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }
}
