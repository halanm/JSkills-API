package com.driga.jskills.sdk;

import com.driga.jskills.JSkills;
import com.driga.jskills.api.SkillsAPI;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.repository.Repository;
import com.driga.jskills.sdk.database.DatabaseProvider;
import com.driga.jskills.sdk.repository.HeroRepository;
import com.driga.jskills.sdk.repository.QuirkRepository;

import java.util.UUID;

public class JSkillsAPI implements SkillsAPI {

    private static JSkillsAPI jSkillsAPI;

    public static JSkillsAPI getInstance() {
        if (JSkillsAPI.jSkillsAPI == null) {
            JSkillsAPI.jSkillsAPI = new JSkillsAPI();
        }
        return JSkillsAPI.jSkillsAPI;
    }

    @Override
    public Repository<UUID, Hero> getHeroes() {
        return HeroRepository.getInstance();
    }

    public Repository<String, Quirk> getQuirks() {
        return QuirkRepository.getInstance();
    }

    @Override
    public DatabaseProvider getDatabase() {
        return JSkills.getInstance().getDatabaseProvider();
    }
}
