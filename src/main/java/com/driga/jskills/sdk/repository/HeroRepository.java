package com.driga.jskills.sdk.repository;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeroRepository implements Repository<UUID, Hero> {

    private static HeroRepository heroRepository;
    private Map<UUID, Hero> heroMap;


    public HeroRepository() {
        this.heroMap = new HashMap<UUID, Hero>();
    }

    public static HeroRepository getInstance() {
        if (HeroRepository.heroRepository == null) {
            HeroRepository.heroRepository = new HeroRepository();
        }
        return HeroRepository.heroRepository;
    }

    @Override
    public Map<UUID, Hero> getMap() {
        return this.heroMap;
    }

    @Override
    public void put(UUID key, Hero value) {
        this.heroMap.put(key, value);
    }

    @Override
    public void remove(UUID key, Hero value) {
        this.heroMap.remove(key, value);
    }

    @Override
    public Hero find(UUID key) {
        return this.heroMap.get(key);
    }
}
