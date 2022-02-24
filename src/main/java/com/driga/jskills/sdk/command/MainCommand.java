package com.driga.jskills.sdk.command;

import com.driga.jskills.sdk.inventory.QuirkListInventory;
import com.driga.jskills.sdk.provider.SkillProvider;
import com.driga.jskills.sdk.repository.HeroRepository;
import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.event.HeroChangeSkillLevelEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private HeroRepository heroRepository;
    private QuirkRepository quirkRepository;
    private SkillProvider skillProvider;

    public MainCommand() {
        heroRepository = HeroRepository.getInstance();
        quirkRepository = QuirkRepository.getInstance();
        skillProvider = SkillProvider.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.hasPermission("jskills.admin")){
            sender.sendMessage("§cVocê não tem permissão para executar esse comando");
            return false;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                JSkills.getInstance().getConfigManager().reloadConfig();
                skillProvider.reload();
                sender.sendMessage("§aAção concluida!");
                return false;
            }
        }
        if(args.length == 2){

            if(args[0].equalsIgnoreCase("reset")){
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null){
                    sender.sendMessage("§cPlayer não encontrado");
                    return false;
                }
                String quirkName = QuirkRepository.getInstance().random().getName();
                Hero hero = heroRepository.find(player.getUniqueId());
                hero.setQuirk(quirkName);
                Quirk quirk = quirkRepository.find(quirkName);
                new HeroChangeSkillLevelEvent(hero, quirk.getSkill1(), 0);
                new HeroChangeSkillLevelEvent(hero, quirk.getSkill2(), 0);
                new HeroChangeSkillLevelEvent(hero, quirk.getSkill3(), 0);
                sender.sendMessage("§aAção concluida!");
                return false;
            }

            if(args[0].equalsIgnoreCase("list")){
                if(args[1].equalsIgnoreCase("quirks")){
                    Player player = (Player) sender;
                    new QuirkListInventory(0, player).open(player);
                    return false;
                }
            }
        }

        if(args.length == 3){
            if(args[0].equalsIgnoreCase("set")){

                if(args[1].equalsIgnoreCase("quirk")){
                    Player player = Bukkit.getPlayer(args[2]);
                    if(player == null){
                        sender.sendMessage("§cPlayer não encontrado");
                        return false;
                    }
                    String quirk = QuirkRepository.getInstance().random().getName();
                    Hero hero = heroRepository.find(player.getUniqueId());
                    hero.setQuirk(quirk);
                    sender.sendMessage("§aAção concluida!");
                    return false;
                }
            }
        }

        if(args.length == 4){
            if(args[0].equalsIgnoreCase("set")){

                if(args[1].equalsIgnoreCase("quirk")){
                    Player player = Bukkit.getPlayer(args[2]);
                    String quirk = args[3];
                    if(player == null){
                        sender.sendMessage("§cPlayer não encontrado");
                        return false;
                    }
                    if(quirkRepository.find(quirk) == null){
                        sender.sendMessage("§cEssa Individualidade não existe");
                        sender.sendMessage("§cUse o comando '/jskills list quirks' para ver as Individualidades registradas");
                        return false;
                    }
                    Hero hero = heroRepository.find(player.getUniqueId());
                    hero.setQuirk(quirk);
                    sender.sendMessage("§aAção concluida!");
                    return false;
                }
            }
        }

        if(args.length == 5){
            if(args[1].equalsIgnoreCase("level")){
                if(args[0].equalsIgnoreCase("add")){
                    Player player = Bukkit.getPlayer(args[2]);
                    if(player == null){
                        sender.sendMessage("§cPlayer não encontrado");
                        return false;
                    }
                    int skillIndex;
                    try{
                        skillIndex = Integer.parseInt(args[3]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[3] + "' não é um número válido");
                        return false;
                    }
                    Hero hero = heroRepository.find(player.getUniqueId());
                    int current = hero.getSkillLevel(skillIndex);
                    int toAdd;
                    try{
                        toAdd = Integer.parseInt(args[4]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[4] + "' não é um número válido");
                        return false;
                    }
                    Skill skill = skillProvider.getSkillByIndex(skillIndex, hero.getQuirk());
                    new HeroChangeSkillLevelEvent(hero, skill, current + toAdd);
                    sender.sendMessage("§aAção concluida!");
                    return false;
                }

                if(args[0].equalsIgnoreCase("remove")){
                    Player player = Bukkit.getPlayer(args[2]);
                    if(player == null){
                        sender.sendMessage("§cPlayer não encontrado");
                        return false;
                    }
                    int skillIndex;
                    try{
                        skillIndex = Integer.parseInt(args[3]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[3] + "' não é um número válido");
                        return false;
                    }
                    Hero hero = heroRepository.find(player.getUniqueId());
                    int current = hero.getSkillLevel(skillIndex);
                    int toRemove;
                    try{
                        toRemove = Integer.parseInt(args[4]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[4] + "' não é um número válido");
                        return false;
                    }
                    Skill skill = skillProvider.getSkillByIndex(skillIndex, hero.getQuirk());
                    new HeroChangeSkillLevelEvent(hero, skill, current - toRemove);
                    sender.sendMessage("§aAção concluida!");
                    return false;
                }

                if(args[0].equalsIgnoreCase("set")){
                    Player player = Bukkit.getPlayer(args[2]);
                    if(player == null){
                        sender.sendMessage("§cPlayer não encontrado");
                        return false;
                    }
                    int skillIndex;
                    try{
                        skillIndex = Integer.parseInt(args[3]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[3] + "' não é um número válido");
                        return false;
                    }
                    Hero hero = heroRepository.find(player.getUniqueId());
                    int toSet;
                    try{
                        toSet = Integer.parseInt(args[4]);
                    }catch (NumberFormatException e){
                        sender.sendMessage("§c'" + args[4] + "' não é um número válido");
                        return false;
                    }
                    Skill skill = skillProvider.getSkillByIndex(skillIndex, hero.getQuirk());
                    new HeroChangeSkillLevelEvent(hero, skill, toSet);
                    sender.sendMessage("§aAção concluida!");
                    return false;
                }

            }
        }
        sender.sendMessage(getCommandList());
        return false;
    }

    public String[] getCommandList(){
        return new String[]{
                "§6/jskills set quirk <Player> <Individualidade> §f=> Muda a Individualidade de um player",
                "",
                "§6/jskills set quirk <Player> §f=> Seleciona uma nova Individualidade aleatória para um player",
                "",
                "§6/jskills add level <Player> <Skill> <Quantia> §f=> Adiciona uma Quantia ao level de uma skill de um player",
                "",
                "§6/jskills remove level <Player> <Skill> <Quantia> §f=> Remove uma Quantia ao level de uma skill de um player",
                "",
                "§6/jskills set level <Player> <Skill> <Level> §f=> Muda o level de uma skill de um player",
                "",
                "§6/jskills reset <Player> §f=> Seleciona uma nova Individualidade aleatória para um player e zera o level de todas as skills",
                "",
                "§6/jskills list quirks §f=> Lista todas as Individualidades registradas",
                "",
                "§6/jskills reload §f=> Recarrega a config",
        };
    }
}
