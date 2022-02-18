package com.driga.jskills.sdk.stat;

import com.driga.jskills.api.prototype.Hero;

import java.util.HashMap;
import java.util.Map;

public class StatManager {

    private Map<String, Map<String, Object>> playerStatMap = new HashMap<>();

    private static StatManager statManager;

    public static StatManager getInstance() {
        if (StatManager.statManager == null) {
            StatManager.statManager = new StatManager();
        }
        return StatManager.statManager;
    }

    public void setStatInt(Hero hero, String key, Integer value){
        Map<String, Object> statMap = new HashMap<>();
        if(playerStatMap.containsKey(hero.getName())){
            statMap = playerStatMap.get(hero.getName());
        }
        statMap.put(key, value);
        playerStatMap.put(hero.getName(), statMap);
    }

    public void setStatDouble(Hero hero, String key, Double value){
        Map<String, Object> statMap = new HashMap<>();
        if(playerStatMap.containsKey(hero.getName())){
            statMap = playerStatMap.get(hero.getName());
        }
        statMap.put(key, value);
        playerStatMap.put(hero.getName(), statMap);
    }

    public Integer getStatInt(Hero hero, String key){
        Map<String, Object> statMap = new HashMap<>();
        if(playerStatMap.containsKey(hero.getName())){
            statMap = playerStatMap.get(hero.getName());
        }
        if(!statMap.containsKey(key)){
            return 0;
        }
        return (Integer) statMap.get(key);
    }

    public Double getStatDouble(Hero hero, String key){
        Map<String, Object> statMap = new HashMap<>();
        if(playerStatMap.containsKey(hero.getName())){
            statMap = playerStatMap.get(hero.getName());
        }
        if(!statMap.containsKey(key)){
            return 0.0;
        }
        return (Double) statMap.get(key);
    }
}
