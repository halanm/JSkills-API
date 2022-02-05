package com.driga.jskills.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data @AllArgsConstructor
public class DebuffEntity {
    private int release;
    private float time;
    private Location location;
}
