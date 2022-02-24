package com.driga.jskills.sdk.listener;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.cooldown.CooldownManager;
import com.driga.jskills.sdk.event.HeroChangeSkillLevelEvent;
import com.driga.jskills.sdk.event.SkillDamageEvent;
import com.driga.jskills.sdk.event.SkillUseEvent;
import com.driga.jskills.sdk.provider.SkillProvider;
import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.sdk.provider.HeroProvider;
import com.driga.jskills.sdk.util.WorldGuardUtil;
import com.driga.jstatscore.api.JStatsCoreAPI;
import com.driga.jstatscore.api.prototype.Attribute;
import com.driga.jstatscore.api.prototype.Subject;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomEventListener implements Listener {

    private SkillProvider skillProvider;
    private HeroProvider heroProvider;
    private QuirkRepository quirkRepository;
    private CooldownManager cooldownManager;

    public CustomEventListener() {
        this.skillProvider = SkillProvider.getInstance();
        this.heroProvider = HeroProvider.getInstance();
        this.quirkRepository = QuirkRepository.getInstance();
        this.cooldownManager = CooldownManager.getInstance();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onDamage(SkillDamageEvent event){
        if(event.isCancelled()){
            return;
        }
        Hero hero = event.getHero();
        Skill skill = event.getSkill();
        Player p = hero.getPlayer();
        Entity entity = event.getEntity();

        if (!WorldGuardUtil.canFlag(p.getLocation(), DefaultFlag.PVP)){
           if(entity instanceof Player)
               return;
        }

        if (!WorldGuardUtil.canFlag(p.getLocation(), DefaultFlag.POTION_SPLASH)){
            if(!(entity instanceof Player))
                return;
        }
        double damage = skillProvider.getDamage(skill, hero);

        Event e = new EntityDamageByEntityEvent(p, entity, EntityDamageEvent.DamageCause.CUSTOM, damage);
        Bukkit.getPluginManager().callEvent(e);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onUse(SkillUseEvent event){
        if(event.isCancelled()){
            return;
        }
        Hero hero = event.getHero();
        Skill skill = event.getSkill();
        Subject subject = JStatsCoreAPI.getInstance().getSubjects().find(hero.getPlayer().getUniqueId());

        if(hero.getSkillLevel(skillProvider.getSkillIndex(skill, hero.getQuirk())) == 0){
            hero.getPlayer().sendMessage("§cVocê ainda não desbloqueou essa Skill!");
            return;
        }

        if(cooldownManager.isOnCooldown(hero, skill.getName())){
            long current = System.currentTimeMillis();
            long release = cooldownManager.getTime(hero, skill.getName());
            long millis = release - current;
            String cd = cooldownManager.getRemainingTime(millis);

            hero.getPlayer().sendMessage("§cVocê só pode usar essa Skill depois de:");
            hero.getPlayer().sendMessage(cd);
            return;
        }

        Attribute en = JStatsCoreAPI.getInstance().getAttributes().find("ENERGY");
        String attr = ChatColor.stripColor(en.getName());
        if(subject.getAttributeLevel("SP") < skillProvider.getEnergyCost(skill, hero)){
            hero.getPlayer().sendMessage("§cVocê não tem " + attr + " para usar essa Skill!");
            return;
        }
        double energy = skillProvider.getEnergyCost(skill, hero);


        String heroName = hero.getName();
        double spn = subject.getAttributeLevel("SP");
        double spa = spn - energy;
        subject.setAttributeLevel("SP", spa);
        cooldownManager.applyCooldown(hero, skillProvider.getCooldown(skill, hero), skill.getName());

        if(skill.getType().equals("EFFECT") || skill.getType().equals("BUFF")){
            skill.playEffect(hero);
        }
        if(skill.getType().equals("COMMAND")){
            for (String cmd : skillProvider.getCommands(skill, hero)) {
                String command = cmd.replaceAll("%Player%", hero.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
            skill.playEffect(hero);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChange(HeroChangeSkillLevelEvent event){
        if(event.isCancelled()){
            return;
        }
        Hero hero = event.getHero();
        Player player = hero.getPlayer();
        String quirkName = hero.getQuirk();
        Quirk quirk = quirkRepository.find(quirkName);
        Skill skill = event.getSkill();
        int level = event.getLevel();
        int index = skillProvider.getSkillIndex(skill, quirkName);

        hero.setSkillLevel(index, level);
        player.getInventory().setItem(6, skillProvider.getSkillItem(quirk.getSkill1(), hero));
        player.getInventory().setItem(7, skillProvider.getSkillItem(quirk.getSkill2(), hero));
        player.getInventory().setItem(8, skillProvider.getSkillItem(quirk.getSkill3(), hero));
    }
}
