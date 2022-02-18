package com.driga.jskills.sdk.provider;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.sdk.stat.StatManager;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.Map;

public class HeroProvider {

    private static HeroProvider heroProvider;

    public static HeroProvider getInstance() {
        if (HeroProvider.heroProvider == null) {
            HeroProvider.heroProvider = new HeroProvider();
        }
        return HeroProvider.heroProvider;
    }

    public int getDataInt(Hero hero, String key) {
        int value = StatManager.getInstance().getStatInt(hero, key);
        return value;
    }

    public double getDataDouble(Hero hero, String key) {
        double value = StatManager.getInstance().getStatDouble(hero, key);
        return value;
    }

    public Map<String, Integer> fromString(String string) {
        Map<String, Integer> map = new HashMap<>();
        if (string != null) {
            String[] pairs = string.replace("{", "").replace("}", "").replaceAll(" ", "").split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                map.put(keyValue[0], Integer.valueOf(keyValue[1]));
            }
        }
        return map;
    }

    public Map<String, Integer> defaultMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 0);
        map.put("2", 0);
        map.put("3", 0);
        return map;
    }
}
