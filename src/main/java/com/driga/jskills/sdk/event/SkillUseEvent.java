package com.driga.jskills.sdk.event;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.event.wrapper.EventWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class SkillUseEvent extends EventWrapper {

    private Hero hero;
    private Skill skill;

    public SkillUseEvent(Hero hero, Skill skill){
        this.hero = hero;
        this.skill = skill;
        Bukkit.getPluginManager().callEvent(this);
    }


    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Skill getSkill() {
        return skill;
    }

    public Hero getHero() {
        return hero;
    }
}
