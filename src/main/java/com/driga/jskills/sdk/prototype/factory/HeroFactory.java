package com.driga.jskills.sdk.prototype.factory;

import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.sdk.database.DatabaseProvider;
import com.driga.jskills.sdk.prototype.JHero;
import com.driga.jskills.sdk.provider.HeroProvider;
import com.driga.jskills.sdk.repository.QuirkRepository;
import org.bukkit.entity.Player;

import java.util.Optional;

public class HeroFactory {

    private static HeroFactory heroFactory;
    private final JSkills jskills;
    private final DatabaseProvider databaseProvider;

    public HeroFactory() {
        this.jskills = JSkills.getInstance();
        this.databaseProvider = this.jskills.getDatabaseProvider();
    }

    public static HeroFactory getInstance() {
        if (HeroFactory.heroFactory == null) {
            HeroFactory.heroFactory = new HeroFactory();
        }
        return HeroFactory.heroFactory;
    }

    public Hero find(Player player) {
        Optional<Hero> optional = this.databaseProvider.query(Hero.class, "SELECT * FROM players WHERE name = ?", resultSet -> {
            Hero hero = new JHero(player.getName(), resultSet.getString("quirk"), resultSet.getString("skillsLevel"));
            resultSet.close();
            return hero;
        }, player.getName());
        if (optional.isPresent()) {
            return optional.get();
        }
        String quirk = "None";
        String map = HeroProvider.getInstance().defaultMap().toString();
        final Hero hero2 = new JHero(player.getName(), quirk, map);
        this.databaseProvider.execute("INSERT INTO players VALUES (?,?,?)", player.getName(), quirk, map);
        return hero2;
    }
}
