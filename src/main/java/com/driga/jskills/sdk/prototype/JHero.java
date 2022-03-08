package com.driga.jskills.sdk.prototype;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.sdk.database.config.ConfigManager;
import com.driga.jskills.sdk.provider.HeroProvider;
import com.driga.jskills.sdk.provider.SkillProvider;
import com.driga.jskills.sdk.repository.QuirkRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;

public class JHero implements Hero {

    private final String name;
    private String quirk;
    private final Map<String, Integer> skillsLevel;

    public JHero(String name, String quirk, String skillsLevel) {
        this.name = name;
        this.quirk = quirk;
        this.skillsLevel = HeroProvider.getInstance().fromString(skillsLevel);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    @Override
    public String getQuirk() {
        return this.quirk;
    }

    @Override
    public Map<String, Integer> getSkillLevelMap() {
        return this.skillsLevel;
    }

    @Override
    public Integer getSkillLevel(Integer skill) {
        return skillsLevel.get(skill.toString());
    }

    @Override
    public void setSkillLevel(Integer skill, Integer level) {
        this.skillsLevel.put(skill.toString(), level);
    }

    public void addSkillLevel(Integer skill, Integer level){
        int current = skillsLevel.get(skill);
        int toSet = current + level;
        this.skillsLevel.put(skill.toString(), toSet);
    }

    public void removeSkillLevel(Integer skill, Integer level){
        int current = skillsLevel.get(skill);
        int toSet = current - level;
        this.skillsLevel.put(skill.toString(), toSet);
    }

    @Override
    public void setQuirk(String quirk) {
        this.quirk = quirk;
        Quirk quirk1 = QuirkRepository.getInstance().find(quirk);
        getPlayer().getInventory().setItem(6, SkillProvider.getInstance().getSkillItem(quirk1.getSkill1(), this));
        getPlayer().getInventory().setItem(7, SkillProvider.getInstance().getSkillItem(quirk1.getSkill2(), this));
        getPlayer().getInventory().setItem(8, SkillProvider.getInstance().getSkillItem(quirk1.getSkill3(), this));

        for(String quirks : QuirkRepository.getInstance().getMap().keySet()){
            quirks = quirks.toLowerCase();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manudelp " + name + " quirk." + quirks);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp " + name + " quirk." + quirk.toLowerCase());

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "modelapply " + quirk.toLowerCase() + " " + name);

        getPlayer().sendMessage("§aVocê Recebeu a individualidade §b" + quirk);
    }
}
