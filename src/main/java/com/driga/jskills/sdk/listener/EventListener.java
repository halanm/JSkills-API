package com.driga.jskills.sdk.listener;

import com.driga.jskills.sdk.inventory.item.ItemBuilder;
import com.driga.jskills.sdk.prototype.factory.HeroFactory;
import com.driga.jskills.sdk.provider.SkillProvider;
import com.driga.jskills.sdk.repository.HeroRepository;
import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Hero;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.api.prototype.Skill;
import com.driga.jskills.sdk.event.SkillUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EventListener implements Listener {

    private SkillProvider skillProvider;
    private HeroRepository heroRepository;
    private QuirkRepository quirkRepository;
    private HeroFactory heroFactory;

    public EventListener() {
        skillProvider = SkillProvider.getInstance();
        heroRepository = HeroRepository.getInstance();
        quirkRepository = QuirkRepository.getInstance();
        heroFactory = HeroFactory.getInstance();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onInteract(PlayerInteractEvent event){
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            return;
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        if (event.getAction().equals(Action.PHYSICAL)) {
            return;
        }
        Player player = event.getPlayer();
        Hero hero = heroRepository.find(player.getUniqueId());
        if(skillProvider.getSkillByItem(player.getItemInHand(), hero) != null){
            event.setCancelled(true);
            Skill skill = skillProvider.getSkillByItem(player.getItemInHand(), hero);
            new SkillUseEvent(hero, skill);
        }

        if(player.getItemInHand() == null){
            return;
        }
        if(!player.getItemInHand().hasItemMeta()){
            return;
        }
        if(player.getItemInHand().getItemMeta().getDisplayName() == null){
            return;
        }

        if(player.getItemInHand().getItemMeta().getDisplayName().equals("§cSem Skill")){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onInteract(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Hero hero = heroRepository.find(player.getUniqueId());
        if(skillProvider.getSkillByItem(player.getItemInHand(), hero) != null){
            event.setCancelled(true);
            Skill skill = skillProvider.getSkillByItem(player.getItemInHand(), hero);
            new SkillUseEvent(hero, skill);
        }

        if(player.getItemInHand() == null){
            return;
        }
        if(!player.getItemInHand().hasItemMeta()){
            return;
        }
        if(player.getItemInHand().getItemMeta().getDisplayName() == null){
            return;
        }

        if(player.getItemInHand().getItemMeta().getDisplayName().equals("§cSem Skill")){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Hero hero = null;
        if (heroRepository.find(player.getUniqueId()) == null) {
            hero = heroFactory.find(player);
            heroRepository.put(player.getUniqueId(), hero);
        }else{
            hero = heroRepository.find(player.getUniqueId());
        }

        if(!hero.getQuirk().equals("None")){
            Quirk quirk = quirkRepository.find(hero.getQuirk());
            player.getInventory().setItem(6, skillProvider.getSkillItem(quirk.getSkill1(), hero));
            player.getInventory().setItem(7, skillProvider.getSkillItem(quirk.getSkill2(), hero));
            player.getInventory().setItem(8, skillProvider.getSkillItem(quirk.getSkill3(), hero));

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "modelapply " + quirk.getName().toLowerCase() + " " + player.getName());
                }
            }.runTaskLater(JSkills.getInstance(), 10);
        }else{
            ItemBuilder builder = new ItemBuilder(35, 1, (byte)14);
            builder.name("§cSem Skill").lore("§cVocê ainda não tem uma individualidade");
            player.getInventory().setItem(6, builder.build());
            player.getInventory().setItem(7, builder.build());
            player.getInventory().setItem(8, builder.build());
        }
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent event) {
        if(!event.getItemDrop().getItemStack().hasItemMeta()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack drop = event.getItemDrop().getItemStack();
        Hero hero = heroFactory.find(player);

        if(drop.getItemMeta().getDisplayName().equals("§cSem Skill") || skillProvider.getSkillByItem(drop, hero) != null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onClick(InventoryClickEvent event) {
        if (event.getHotbarButton() == 6 | event.getHotbarButton() == 7 | event.getHotbarButton() == 8) {
            event.setCancelled(true);
            return;
        }
        if (event.getSlot() < 0) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Hero hero = heroFactory.find(player);

        if(item == null){
            return;
        }
        if(!item.hasItemMeta()){
            return;
        }
        if(item.getItemMeta().getDisplayName() == null){
            return;
        }

        if(item.getItemMeta().getDisplayName().equals("§cSem Skill") || skillProvider.getSkillByItem(item, hero) != null){
            event.setCancelled(true);
        }
    }
}
