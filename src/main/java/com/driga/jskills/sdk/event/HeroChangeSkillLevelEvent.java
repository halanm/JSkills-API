package com.driga.jskills.sdk.event;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.event.wrapper.EventWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class HeroChangeSkillLevelEvent extends EventWrapper {

    private Hero hero;
    private Skill skill;
    private Integer level;

    public HeroChangeSkillLevelEvent(Hero hero, Skill skill, Integer level){
        this.hero = hero;
        this.skill = skill;
        this.level = level;
        Bukkit.getPluginManager().callEvent(this);
    }


    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Skill getSkill() {
        return skill;
    }

    public Hero getHero() {
        return hero;
    }

    public Integer getLevel() {
        return level;
    }
}
