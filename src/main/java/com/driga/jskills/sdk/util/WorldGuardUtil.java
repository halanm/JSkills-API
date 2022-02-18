package com.driga.jskills.sdk.util;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.StateFlag;
import lombok.val;
import org.bukkit.Location;

public class WorldGuardUtil {
    public static boolean canFlag(Location loc, StateFlag flag){
        StateFlag.State out = WGBukkit.getPlugin().getRegionManager(loc.getWorld()).getApplicableRegions(loc).queryState(null, flag);
        if(out==null)
            return false;
        return !String.valueOf(out).equalsIgnoreCase("DENY");
    }
}
