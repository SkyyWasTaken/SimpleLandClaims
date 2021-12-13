package com.skyywastaken.simplelandclaims.claim.tracking;

import org.bukkit.World;

public class ClaimPosition {
    // This is not a record because gson hates records.
    private int x;
    private int z;
    private World.Environment dimension;

    public ClaimPosition(int x, int z, World.Environment dimension) {
        this.x = x;
        this.z = z;
        this.dimension = dimension;
    }

    public int x() {
        return x;
    }

    public int z() {
        return z;
    }

    public World.Environment dimension() {
        return this.dimension;
    }
}
