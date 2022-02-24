package com.driga.jskills.sdk.provider;

import com.driga.jskills.sdk.inventory.item.ItemBuilder;
import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.JSkillsAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillProvider {

    private static SkillProvider skillProvider;
    private ConfigurationSection section;
    private HeroProvider heroProvider;
    private QuirkRepository quirkRepository;

    public SkillProvider() {
        heroProvider = HeroProvider.getInstance();
        quirkRepository = QuirkRepository.getInstance();
        section = JSkills.getInstance().getConfigManager().getConfig().getConfigurationSection("Skills");
    }

    public static SkillProvider getInstance() {
        if (SkillProvider.skillProvider == null) {
            SkillProvider.skillProvider = new SkillProvider();
        }
        return SkillProvider.skillProvider;
    }

    public Integer getUnlockCost(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return 0;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return 0;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return 0;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index) + 1;
        if(level > 5) return 0;
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getInt("UnlockCost");
    }

    public Double getDamage(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return 0.0;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return 0.0;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return 0.0;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        double multiplier = heroProvider.getAttributeValue(hero, getAttribute(skill, hero));
        double damage = skillLevelSection.getDouble("DMG") * multiplier;
        return damage;
    }

    public Double getValue(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return 0.0;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return 0.0;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return 0.0;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        double multiplier = heroProvider.getAttributeValue(hero, getAttribute(skill, hero));
        double value = skillLevelSection.getInt("Value") * multiplier;
        return value;
    }

    public Long getDuration(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        long duration = 0;
        if(!section.contains(quirk)) return duration;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return duration;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return duration;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        duration = skillLevelSection.getLong("Duration") * 20;
        return duration;
    }

    public List<String> getCommands(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return null;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return null;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return null;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getStringList("Commands");
    }

    public Double getEnergyCost(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return 0.0;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return 0.0;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return 0.0;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getDouble("EnergyCost");
    }

    public Integer getCooldown(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return 0;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return 0;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return 0;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getInt("Cooldown");
    }

    public String getItemName(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return "";
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return "";
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return "";
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getString("ItemName").replaceAll("&", "§");
    }

    public String getItemId(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return "";
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return "";
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return "";
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        return skillLevelSection.getString("ItemId");
    }

    public List<String> getItemLore(Skill skill, Hero hero){
        List<String> lore = new ArrayList<>();
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return lore;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return lore;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return lore;
        int index = getSkillIndex(skill, hero.getQuirk());
        int level = hero.getSkillLevel(index);
        ConfigurationSection skillLevelSection = skillSection.getConfigurationSection("LVL" + level);
        String[] split = skillLevelSection.getString("ItemLore").split("\\|");
        for(String line : split){
            lore.add(line.replaceAll("&", "§"));
        }
        return lore;
    }

    public String getAttribute(Skill skill, Hero hero){
        String quirk = hero.getQuirk();
        String skillName = skill.getName();
        if(!section.contains(quirk)) return null;
        ConfigurationSection quirkSection = section.getConfigurationSection(quirk);
        if(!quirkSection.contains(skillName)) return null;
        ConfigurationSection skillSection = quirkSection.getConfigurationSection(skillName);
        if(getSkillIndex(skill, hero.getQuirk()) == 0) return null;
        int index = getSkillIndex(skill, hero.getQuirk());
        return skillSection.getString("Attribute");
    }

    public Integer getSkillIndex(Skill skill, String quirkName){
        Quirk quirk = JSkillsAPI.getInstance().getQuirks().find(quirkName);
        if(skill.getName().equals(quirk.getSkill1().getName())) return 1;
        if(skill.getName().equals(quirk.getSkill2().getName())) return 2;
        if(skill.getName().equals(quirk.getSkill3().getName())) return 3;
        return 0;
    }

    public Skill getSkillByIndex(Integer index, String quirkName){
        Quirk quirk = JSkillsAPI.getInstance().getQuirks().find(quirkName);
        if(index == 1) return quirk.getSkill1();
        if(index == 3) return quirk.getSkill2();
        if(index == 2) return quirk.getSkill3();
        return null;
    }

    public void generateSection(Quirk quirk){
        List<String> skillList = new ArrayList<>();
        skillList.add(quirk.getSkill1().getName());
        skillList.add(quirk.getSkill2().getName());
        skillList.add(quirk.getSkill3().getName());
        if(section == null) {
            section = JSkills.getInstance().getConfigManager().getConfig().createSection("Skills");
            ConfigurationSection quirkSection = section.createSection(quirk.getName());
            for(String skillName : skillList){
                Skill skill = getSkillByName(skillName, quirk);
                ConfigurationSection skillSection = quirkSection.createSection(skillName);
                skillSection.set("Attribute", "str");
                for(int i = 0; i <= 5; i++){
                    ConfigurationSection skillLevelSection = skillSection.createSection("LVL" + i);
                    skillLevelSection.set("UnlockCost", 0);
                    if(skill.getType().equals("EFFECT")){
                        skillLevelSection.set("DMG", 0.0);
                    }
                    if(skill.getType().equals("BUFF")){
                        skillLevelSection.set("Value", 0);
                        skillLevelSection.set("Duration", 0);
                    }
                    if(skill.getType().equals("COMMAND")){
                        skillLevelSection.set("Commands", new String[]{"comando1", "comando2"});
                    }
                    skillLevelSection.set("EnergyCost", 0.0);
                    skillLevelSection.set("Cooldown", 0);
                    skillLevelSection.set("ItemId", "381:0");
                    skillLevelSection.set("ItemName", "&6Exemplo de Nome de item");
                    skillLevelSection.set("ItemLore", "&6Linha1|&bLinha2");
                    skillSection.set("LVL" + i, skillLevelSection);
                }
                quirkSection.set(skillName, skillSection);
            }
            section.set(quirk.getName(), quirkSection);
            JSkills.getInstance().getConfigManager().getConfig().set("Skills." + quirk.getName(), quirkSection);
            JSkills.getInstance().getConfigManager().saveConfig();
        }
        if(section != null && !section.contains(quirk.getName())){
            ConfigurationSection quirkSection = section.createSection(quirk.getName());
            for(String skillName : skillList){
                Skill skill = getSkillByName(skillName, quirk);
                ConfigurationSection skillSection = quirkSection.createSection(skillName);
                skillSection.set("Attribute", "str");
                for(int i = 0; i <= 5; i++){
                    ConfigurationSection skillLevelSection = skillSection.createSection("LVL" + i);
                    skillLevelSection.set("UnlockCost", 0);
                    if(skill.getType().equals("EFFECT")){
                        skillLevelSection.set("DMG", 0);
                    }
                    if(skill.getType().equals("BUFF")){
                        skillLevelSection.set("Value", 0);
                        skillLevelSection.set("Duration", 0);
                    }
                    if(skill.getType().equals("COMMAND")){
                        skillLevelSection.set("Commands", new String[]{"comando1", "comando2"});
                    }
                    skillLevelSection.set("EnergyCost", 0);
                    skillLevelSection.set("Cooldown", 0);
                    skillLevelSection.set("ItemId", "381:0");
                    skillLevelSection.set("ItemName", "&6Exemplo de Nome de item");
                    skillLevelSection.set("ItemLore", "&6Linha1|&bLinha2");
                    skillSection.set("LVL" + i, skillLevelSection);
                }
                quirkSection.set(skillName, skillSection);
            }
            section.set(quirk.getName(), quirkSection);
            JSkills.getInstance().getConfigManager().getConfig().set("Skills." + quirk.getName(), quirkSection);
            JSkills.getInstance().getConfigManager().saveConfig();
        }
    }

    public ItemStack getSkillItem(Skill skill, Hero hero){
        String[] split = getItemId(skill, hero).split(":");
        int id = Integer.parseInt(split[0]);
        int data = Integer.parseInt(split[1]);
        ItemBuilder builder = new ItemBuilder(id, 1, (byte)data);
        builder.name(getItemName(skill, hero)).lore(getItemLore(skill, hero));
        return builder.build();
    }

    public Skill getSkillByName(String name, Quirk quirk){
        if(quirk.getSkill1().getName().equals(name)){
            return quirk.getSkill1();
        }
        if(quirk.getSkill2().getName().equals(name)){
            return quirk.getSkill2();
        }
        if(quirk.getSkill3().getName().equals(name)){
            return quirk.getSkill3();
        }
        return null;
    }

    public Skill getSkillByItem(ItemStack item, Hero hero){
        if(item == null){
            return null;
        }
        if(!item.hasItemMeta()){
            return null;
        }
        if(item.getItemMeta().getDisplayName() == null){
            return null;
        }
        if(hero.getQuirk().equals("None")){
            return null;
        }
        String itemName = item.getItemMeta().getDisplayName();

        ConfigurationSection quirkSection = section.getConfigurationSection(hero.getQuirk());
        List<String> skills = new ArrayList<>();
        for(String key : quirkSection.getKeys(false)){
            skills.add(key);
        }

        for(String key : skills){
            ConfigurationSection skillSection = quirkSection.getConfigurationSection(key);
            for(String lvl : skillSection.getKeys(false)){
                if(!lvl.equals("Attribute")){
                    String name = skillSection.getConfigurationSection(lvl).getString("ItemName");
                    name = name.replaceAll("&", "§");
                    if(itemName.equals(name)){
                        Quirk quirk = quirkRepository.find(hero.getQuirk());
                        return getSkillByName(key, quirk);
                    }
                }
            }
        }
        return null;
    }

    public void reload(){
        section = JSkills.getInstance().getConfigManager().getConfig().getConfigurationSection("Skills");
    }
}
