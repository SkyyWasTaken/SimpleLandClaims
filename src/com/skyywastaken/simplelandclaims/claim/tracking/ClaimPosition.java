package com.skyywastaken.simplelandclaims.claim.tracking;

import org.bukkit.World;

public record ClaimPosition(int x, int z, World.Environment dimension) {
}
