package com.driga.jskills.sdk.provider;

import com.driga.jskills.api.prototype.Hero;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class HeroProvider {

    private static HeroProvider heroProvider;
    private NBTManager manager;

    private HeroProvider() {
        manager = PowerNBT.getApi();
    }

    public static HeroProvider getInstance() {
        if (HeroProvider.heroProvider == null) {
            HeroProvider.heroProvider = new HeroProvider();
        }
        return HeroProvider.heroProvider;
    }

    public int getWil(Hero hero) {
        NBTCompound forgeData = manager.readForgeData(hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        if(playerPersisted == null){
            return 0;
        }
        if(playerPersisted.get("jrmcWilI") == null){
            return 0;
        }
        int bdy = (int) playerPersisted.get("jrmcWilI");
        return bdy;
    }

    public int getRelease(Hero hero) {
        NBTCompound forgeData = manager.readForgeData(hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        if(playerPersisted == null){
            return 0;
        }
        if(playerPersisted.get("jrmcRelease") == null){
            return 0;
        }
        String r = String.valueOf(playerPersisted.get("jrmcRelease"));
        int release = Integer.valueOf(r);
        return release;
    }

    public void setRelease(Hero hero, Integer value){
        NBTCompound forgeData = manager.readForgeData((Entity)hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        playerPersisted.put("jrmcRelease", value);
        forgeData.put("PlayerPersisted", playerPersisted);
        manager.writeForgeData((Entity)hero.getPlayer(), forgeData);
    }

    public int getStamina(Hero hero) {
        NBTCompound forgeData = manager.readForgeData(hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        if(playerPersisted == null){
            return 0;
        }
        if(playerPersisted.get("jrmcStamina") == null){
            return 0;
        }
        String r = String.valueOf(playerPersisted.get("jrmcStamina"));
        int release = Integer.valueOf(r);
        return release;
    }

    public void setStamina(Hero hero, Integer value){
        NBTCompound forgeData = manager.readForgeData((Entity)hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        playerPersisted.put("jrmcStamina", value);
        forgeData.put("PlayerPersisted", playerPersisted);
        manager.writeForgeData((Entity)hero.getPlayer(), forgeData);
    }

    public int getEnergy(Hero hero) {
        NBTCompound forgeData = manager.readForgeData(hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        if(playerPersisted == null){
            return 0;
        }
        if(playerPersisted.get("jrmcEnrgy") == null){
            return 0;
        }
        String r = String.valueOf(playerPersisted.get("jrmcEnrgy"));
        int release = Integer.valueOf(r);
        return release;
    }

    public int getTP(Hero hero) {
        NBTCompound forgeData = manager.readForgeData(hero.getPlayer());
        NBTCompound playerPersisted = forgeData.getCompound("PlayerPersisted");
        if(playerPersisted == null){
            return 0;
        }
        if(playerPersisted.get("jrmcTpint") == null){
            return 0;
        }
        String r = String.valueOf(playerPersisted.get("jrmcTpint"));
        int release = Integer.valueOf(r);
        return release;
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
    public Object getAtributte(Hero hero, String key) {
        NBTCompound data = manager.read(hero.getPlayer());
        NBTCompound attributes = data.getCompound("Attributes");
        return attributes.get(key);
    }
    public void setAtributte(Hero hero, String key, Object value) {
        NBTCompound data = manager.read(hero.getPlayer());
        NBTCompound attributes = data.getCompound("Attributes");
        attributes.put(key,value);
        data.put("Attributes", attributes);
        manager.write(hero.getPlayer(), data);
    }
    public Map<String, Integer> defaultMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 0);
        map.put("2", 0);
        map.put("3", 0);
        return map;
    }
}
