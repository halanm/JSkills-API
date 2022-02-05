package com.driga.jskills.sdk.cooldown;

import com.driga.jskills.api.prototype.Hero;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static CooldownManager cooldownManager;
    public Map<String, Map<UUID,Long>> playerCooldown = new HashMap<String,Map<UUID,Long>>();
    public Map<String, Map<UUID,Long>> cooldownTime = new HashMap<String,Map<UUID,Long>>();

    public static CooldownManager getInstance() {
        if (CooldownManager.cooldownManager == null) {
            CooldownManager.cooldownManager = new CooldownManager();
        }
        return CooldownManager.cooldownManager;
    }

    public void applyCooldown(Hero hero, long seconds, String cd){
        Map<UUID, Long> cooldown = playerCooldown.get(cd);
        if(cooldown == null){
            cooldown = new HashMap<UUID, Long>();
        }
        Map<UUID, Long> cooldownApplied = cooldownTime.get(cd);
        if(cooldownApplied == null){
            cooldownApplied = new HashMap<UUID, Long>();
        }
        long millis = seconds * 1000;
        cooldown.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis() + millis);
        playerCooldown.put(cd, cooldown);
        cooldownApplied.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis());
        cooldownTime.put(cd, cooldownApplied);
    }

    public boolean isOnCooldown(Hero hero, String cd) {
        long current = System.currentTimeMillis();
        long millis = getTime(hero, cd);
        return millis > current;
    }

    public long getTime(Hero hero, String cd) {
        Map<UUID, Long> cooldown = playerCooldown.get(cd);
        if(cooldown == null){
            cooldown = new HashMap<UUID, Long>();
            cooldown.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
        if(!cooldown.containsKey(hero.getPlayer().getUniqueId())){
            cooldown.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
        return cooldown.get(hero.getPlayer().getUniqueId());
    }

    public long getTimeApplied(Hero hero, String cd) {
        Map<UUID, Long> cooldownApplied = cooldownTime.get(cd);
        if(cooldownApplied == null){
            cooldownApplied = new HashMap<UUID, Long>();
            cooldownApplied.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
        if(!cooldownApplied.containsKey(hero.getPlayer().getUniqueId())){
            cooldownApplied.put(hero.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
        return cooldownApplied.get(hero.getPlayer().getUniqueId());
    }

    public String getRemainingTime(long millis) {
        long seconds = millis/1000;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        return "§e" + minutes + " Minuto(s) " + seconds + " Segundo(s)";
    }
}
