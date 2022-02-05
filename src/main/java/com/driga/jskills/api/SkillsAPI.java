package com.driga.jskills.api;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.repository.Repository;
import com.driga.jskills.sdk.database.DatabaseProvider;

import java.util.UUID;


public interface SkillsAPI {

    Repository<UUID, Hero> getHeroes();

    Repository<String, Quirk> getQuirks();

    DatabaseProvider getDatabase();
}
