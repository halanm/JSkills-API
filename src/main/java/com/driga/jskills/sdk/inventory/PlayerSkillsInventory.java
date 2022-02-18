package com.driga.jskills.sdk.inventory;

import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.sdk.event.HeroChangeSkillLevelEvent;
import com.driga.jskills.sdk.inventory.item.ItemBuilder;
import com.driga.jskills.sdk.inventory.sustainer.InventorySustainer;
import com.driga.jskills.sdk.provider.HeroProvider;
import com.driga.jskills.sdk.provider.SkillProvider;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerSkillsInventory extends InventorySustainer {

    private JSkills jSkills;
    private QuirkRepository quirkRepository;
    private SkillProvider skillProvider;
    private HeroProvider heroProvider;
    private Player player;
    private Hero hero;

    public PlayerSkillsInventory(Hero hero, String quirkName) {
        super("Individualidade - " + quirkName, MenuSize.ONE_LINE);
        this.jSkills = JSkills.getInstance();
        this.quirkRepository = QuirkRepository.getInstance();
        this.skillProvider = SkillProvider.getInstance();
        this.heroProvider = HeroProvider.getInstance();
        this.player = hero.getPlayer();
        this.hero = hero;

        this.render();


    }

    private void render(){
        Quirk quirk = quirkRepository.find(hero.getQuirk());

        String skill1 = player.getInventory().getItem(6).getItemMeta().getDisplayName();
        String skill2 = player.getInventory().getItem(7).getItemMeta().getDisplayName();
        String skill3 = player.getInventory().getItem(8).getItemMeta().getDisplayName();

        for(int i = 0; i <= 8; i++){
            this.setItem(i, new ItemBuilder(160, 1, (byte)15).name(" ").lore(""));
        }

        this.setItem(1, new ItemBuilder(player.getInventory().getItem(6).getType()).name(skill1)
        .lore("§bLevel: §a" + hero.getSkillLevel(1),
        "§bCusto: §a" + skillProvider.getUnlockCost(quirk.getSkill1(), hero) + " TP",
        "§fClique para Melhorar essa skill"),
        e ->{
            if(heroProvider.getDataInt(hero, "tp") < skillProvider.getUnlockCost(quirk.getSkill1(), hero)){
                player.sendMessage("§cVocê não tem TP Suficiente");
                player.closeInventory();
            }else{
                if(skillProvider.getUnlockCost(quirk.getSkill1(), hero) != 0){
                    String heroName = hero.getName();
                    int cost = skillProvider.getUnlockCost(quirk.getSkill1(), hero);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmctp -" + cost + " " + heroName);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 0.1f, 0.1f);
                    new HeroChangeSkillLevelEvent(hero, quirk.getSkill1(), hero.getSkillLevel(1) + 1);
                }else{
                    player.sendMessage("§cEssa Skill já está no Level Máximo");
                    player.closeInventory();
                }
            }
            render();
            this.open(player);
        });

        this.setItem(4, new ItemBuilder(player.getInventory().getItem(7).getType()).name(skill2)
        .lore("§bLevel: §a" + hero.getSkillLevel(2),
        "§bCusto: §a" + skillProvider.getUnlockCost(quirk.getSkill2(), hero) + " TP",
         "§fClique para Melhorar essa skill"),
        e ->{
            if(heroProvider.getDataInt(hero, "tp") < skillProvider.getUnlockCost(quirk.getSkill2(), hero)){
                player.sendMessage("§cVocê não tem TP Suficiente");
                player.closeInventory();
            }else{
                if(skillProvider.getUnlockCost(quirk.getSkill2(), hero) != 0) {
                    String heroName = hero.getName();
                    int cost = skillProvider.getUnlockCost(quirk.getSkill2(), hero);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmctp -" + cost + " " + heroName);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 0.1f, 0.1f);
                    new HeroChangeSkillLevelEvent(hero, quirk.getSkill2(), hero.getSkillLevel(2) + 1);
                }else{
                    player.sendMessage("§cEssa Skill já está no Level Máximo");
                    player.closeInventory();
                }
            }
            render();
            this.open(player);
        });

        this.setItem(7, new ItemBuilder(player.getInventory().getItem(8).getType()).name(skill3)
        .lore("§bLevel: §a" + hero.getSkillLevel(3),
        "§bCusto: §a" + skillProvider.getUnlockCost(quirk.getSkill3(), hero) + " TP",
        "§fClique para Melhorar essa skill"),
        e ->{
            if(heroProvider.getDataInt(hero, "tp") < skillProvider.getUnlockCost(quirk.getSkill3(), hero)){
                player.sendMessage("§cVocê não tem TP Suficiente");
                player.closeInventory();
            }else{
                if(skillProvider.getUnlockCost(quirk.getSkill3(), hero) != 0) {
                    String heroName = hero.getName();
                    int cost = skillProvider.getUnlockCost(quirk.getSkill3(), hero);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jrmctp -" + cost + " " + heroName);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 0.1f, 0.1f);
                    new HeroChangeSkillLevelEvent(hero, quirk.getSkill3(), hero.getSkillLevel(3) + 1);
                }else{
                    player.sendMessage("§cEssa Skill já está no Level Máximo");
                    player.closeInventory();
                }
            }
            render();
            this.open(player);
        });
    }
}
