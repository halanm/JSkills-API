package com.driga.jskills.sdk.repository;

import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.repository.Repository;

import java.util.*;

public class QuirkRepository implements Repository<String, Quirk> {

    private static QuirkRepository quirkRepository;
    private Map<String, Quirk> qurikMap;


    public QuirkRepository() {
        this.qurikMap = new HashMap<String, Quirk>();
    }

    public static QuirkRepository getInstance() {
        if (QuirkRepository.quirkRepository == null) {
            QuirkRepository.quirkRepository = new QuirkRepository();
        }
        return QuirkRepository.quirkRepository;
    }

    @Override
    public Map<String, Quirk> getMap() {
        return this.qurikMap;
    }

    @Override
    public void put(String key, Quirk value) {
        this.qurikMap.put(key, value);
    }

    @Override
    public void remove(String key, Quirk value) {
        this.qurikMap.remove(key, value);
    }

    @Override
    public Quirk find(String key) {
        return this.qurikMap.get(key);
    }

    public Quirk random() {
        List<String> keysAsArray = new ArrayList<String>(qurikMap.keySet());
        Random r = new Random();
        return this.qurikMap.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
    }

    public void registerQuirk(String quirkName, Quirk quirk){
        this.put(quirkName, quirk);
        JSkills.getInstance().getSkillProvider().generateSection(quirk);
    }
}
