package com.driga.jskills.sdk.event;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.event.wrapper.EventWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SkillDamageEvent extends EventWrapper {

    private Hero hero;
    private Skill skill;
    private Entity entity;

    public SkillDamageEvent(Hero hero, Skill skill, Entity entity){
        this.hero = hero;
        this.skill = skill;
        this.entity = entity;
        Bukkit.getPluginManager().callEvent(this);
    }


    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Skill getSkill() {
        return skill;
    }

    public Hero getHero() {
        return hero;
    }

    public Entity getEntity() {
        return entity;
    }
}
