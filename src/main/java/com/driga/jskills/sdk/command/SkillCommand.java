package com.driga.jskills.sdk.command;

import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.sdk.inventory.PlayerSkillsInventory;
import com.driga.jskills.sdk.repository.HeroRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkillCommand implements CommandExecutor {

    private HeroRepository heroRepository;

    public SkillCommand() {
        heroRepository = HeroRepository.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cVocê não pode executar esse comando");
            return false;
        }
        Player player = (Player) sender;
        Hero hero = heroRepository.find(player.getUniqueId());
        if(hero.getQuirk().equals("None")){
            player.sendMessage("§cVocê ainda não tem uma individualidade");
            return false;
        }
        new PlayerSkillsInventory(hero, hero.getQuirk()).open(player);

        return false;
    }
}
