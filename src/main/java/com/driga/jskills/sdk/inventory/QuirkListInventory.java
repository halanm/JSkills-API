package com.driga.jskills.sdk.inventory;

import com.driga.jskills.sdk.repository.QuirkRepository;
import com.driga.jskills.JSkills;
import com.driga.jskills.api.prototype.Quirk;
import com.driga.jskills.sdk.inventory.item.ItemBuilder;
import com.driga.jskills.sdk.inventory.sustainer.InventorySustainer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class QuirkListInventory extends InventorySustainer {

    private JSkills jSkills;
    private QuirkRepository quirkRepository;
    private Integer page;
    private Player player;

    public QuirkListInventory(Integer page, Player player) {
        super("Individualidades", MenuSize.SIX_LINES);
        this.jSkills = JSkills.getInstance();
        this.quirkRepository = QuirkRepository.getInstance();
        this.page = page;
        this.player = player;
        this.render();
    }

    private void render(){
        int slot = 0;
        int size = 0;
        String[] quirks = quirkRepository.getMap().keySet().toArray(new String[quirkRepository.getMap().keySet().size()]);
        int max = (this.page + 1) * 44;
        int index = this.page * 44;
        size = quirks.length - 1;
        int maxPages = (int) Math.ceil(size / 45);
        if(page != 0){
            max = (this.page + 1) * 45;
            index = this.page * 45;
        }
        if(size > max){
            size = max;
        }
        for(int i = 0; i <= 44; i++){
            this.setItem(i, new ItemBuilder(Material.AIR));
        }

        for(int i = index; i <= size; i++){
            String quirkName = quirks[i];
            Quirk quirk = quirkRepository.find(quirkName);

            String skill1 = quirk.getSkill1().getName() + ", ";
            String skill2 = quirk.getSkill2().getName() + ", ";
            String skill3 = quirk.getSkill3().getName();

            String name = "§6" + quirk.getName();
            String lore = "§bSkills: §a[" + skill1 + skill2 + skill3 + "]";

            this.setItem(slot, new ItemBuilder(Material.EYE_OF_ENDER).name(name).lore(lore));
            slot++;
        }
        slot = 44;
        for(int i = 0; i <= 8; i++){
            slot++;
            this.setItem(slot, new ItemBuilder(160, 1, (byte)15).name(" ").lore(""));
        }
        this.setItem(49, new ItemBuilder(Material.ENCHANTED_BOOK).name("§6Lista de Individualidades")
                .lore("§bPágina " + (this.page + 1)));
        if(this.page != 0){
            this.setItem(48, new ItemBuilder(Material.NETHER_STAR).name("§6Voltar")
                    .lore("§bPágina " + this.page), e ->{
                this.page = this.page - 1;
                render();
                this.open(player);
            });
        }
        if(this.page != maxPages){
            this.setItem(50, new ItemBuilder(Material.NETHER_STAR).name("§6Avançar")
                    .lore("§bPágina " + (this.page + 2)), e ->{
                this.page = this.page + 1;
                render();
                this.open(player);
            });
        }
    }
}
