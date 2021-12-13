package com.skyywastaken.simplelandclaims.listener;

import com.skyywastaken.simplelandclaims.claim.tracking.ClaimTracker;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MobListener implements Listener {
    private final ClaimTracker CLAIM_TRACKER;

    public MobListener(ClaimTracker passedClaimTracker) {
        this.CLAIM_TRACKER = passedClaimTracker;
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent e) {
        if (this.CLAIM_TRACKER.posIsInMobProtectedClaim(e.getLocation())
                && (e.getEntityType() == EntityType.CREEPER || e.getEntityType() == EntityType.FIREBALL)) {
            e.blockList().clear();
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        if (!this.CLAIM_TRACKER.posIsInMobProtectedClaim(e.getBlock().getLocation())) {
            return;
        }
        if (e.getEntityType() == EntityType.ENDERMAN || (e.getBlockData().getMaterial() == Material.DIRT
                && e.getEntityType() != EntityType.PLAYER)) {
            e.setCancelled(true);
        }
    }
}
