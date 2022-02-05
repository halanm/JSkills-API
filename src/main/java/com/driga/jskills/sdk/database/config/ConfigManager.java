package com.driga.jskills.sdk.database.config;

import com.driga.jskills.JSkills;
import com.google.common.io.Files;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class ConfigManager {

    private JSkills plugin;
    private FileConfiguration dataConfig;
    private File configFile;
    private File fileToSave;

    public ConfigManager() {
        this.plugin = JSkills.getInstance();
        this.dataConfig = null;
        this.configFile = null;
        this.fileToSave = this.plugin.getDataFolder();
        this.saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.fileToSave, "config.yml");
        }
        this.dataConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = JSkills.getInstance().getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration((Reader)new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults((Configuration)defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            this.reloadConfig();
            this.customConfigLoad();
        }
        return this.dataConfig;
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.fileToSave, "config.yml");
        }
        if (!this.configFile.exists()) {
            this.createFile();
        }
    }

    private void createFile() {
        try {
            this.configFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void customConfigLoad () {
        try {
            dataConfig.loadFromString (Files.toString (configFile, StandardCharsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace ();
        }
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException e) {
            JSkills.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
            e.printStackTrace();
        }
    }
}
