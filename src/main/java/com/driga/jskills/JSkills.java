package com.driga.jskills;

import com.driga.jskills.sdk.command.MainCommand;
import com.driga.jskills.sdk.command.SkillCommand;
import com.driga.jskills.sdk.database.DatabaseProvider;
import com.driga.jskills.sdk.database.config.ConfigManager;
import com.driga.jskills.sdk.inventory.sustainer.InventorySustainer;
import com.driga.jskills.sdk.listener.CustomEventListener;
import com.driga.jskills.sdk.listener.EventListener;
import com.driga.jskills.sdk.provider.SkillProvider;
import com.driga.jskills.sdk.repository.HeroRepository;
import com.driga.jskills.sdk.repository.QuirkRepository;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JSkills extends JavaPlugin {

    private DatabaseProvider databaseProvider;
    private HeroRepository heroRepository;
    private QuirkRepository quirkRepository;
    private ConfigManager configManager;
    private SkillProvider skillProvider;

    public JSkills() {
        this.heroRepository = HeroRepository.getInstance();
        this.quirkRepository = QuirkRepository.getInstance();
    }

    public static JSkills getInstance() {
        return (JSkills)getPlugin((Class)JSkills.class);
    }

    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.configManager = new ConfigManager();
        this.skillProvider = new SkillProvider();

        configManager.saveDefaultConfig();
        configManager.customConfigLoad();

        (this.databaseProvider = new DatabaseProvider()).openConnection();

        InventorySustainer.register(this);

        Bukkit.getPluginManager().registerEvents(new EventListener() , this);
        Bukkit.getPluginManager().registerEvents(new CustomEventListener() , this);

        getCommand("jskills").setExecutor(new MainCommand());
        getCommand("skills").setExecutor(new SkillCommand());
    }

    public void onDisable() {
        this.heroRepository.getMap().values().forEach(hero -> this.databaseProvider.update("UPDATE players SET quirk = ?, skillsLevel = ? WHERE name = ?", hero.getQuirk(), hero.getSkillLevelMap().toString(), hero.getName()));
        this.databaseProvider.closeConnection();
    }

    public DatabaseProvider getDatabaseProvider() {
        return this.databaseProvider;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SkillProvider getSkillProvider() {
        return skillProvider;
    }
}
