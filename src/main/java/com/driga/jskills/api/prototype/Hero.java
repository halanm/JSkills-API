package com.driga.jskills.api.prototype;

import org.bukkit.entity.Player;

import java.util.Map;

public interface Hero {

    String getName();

    Player getPlayer();

    String getQuirk();

    Map<String, Integer> getSkillLevelMap();

    Integer getSkillLevel(Integer p0);

    void setSkillLevel(Integer p0, Integer p1);

    void setQuirk(String p0);
}
