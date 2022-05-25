package fr.mrlaikz.spartastuff.menu;

import fr.iban.bukkitcore.menu.Menu;
import fr.mrlaikz.spartastuff.SpartaStuff;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StuffMenu extends Menu {

    private List<Material> blacklist = new ArrayList<Material>();

    public StuffMenu(Player player) {
        super(player);
    }

    @Override
    public String getMenuName() {
        return "§b§lMenu Armure";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem().getType().equals(Material.CHEST)) {
            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getLore().contains("§cVous avez atteint le niveau maximal !")) {
                return;
            }

            ItemStack armor[] = p.getInventory().getArmorContents();
            int lvl = 0;
            if(armor[0].hasItemMeta() && armor[0].getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpartaStuff.getInstance(), "level"))) {
                lvl = armor[0].getItemMeta().getPersistentDataContainer().get(new NamespacedKey(SpartaStuff.getInstance(), "level"), PersistentDataType.INTEGER);
            }

            Economy eco = SpartaStuff.getEconomy();
            if(eco.getBalance(p) >= (lvl+1)*100000) {
                for(ItemStack it : p.getInventory().getArmorContents()) {
                    ItemMeta itM = it.getItemMeta();
                    NamespacedKey k = new NamespacedKey(SpartaStuff.getInstance(), "level");
                    if(itM.getPersistentDataContainer().has(k, PersistentDataType.INTEGER)) {
                        lvl = itM.getPersistentDataContainer().get(k, PersistentDataType.INTEGER);
                        lvl +=1;
                        itM.getPersistentDataContainer().set(k, PersistentDataType.INTEGER, lvl);
                        it.setItemMeta(itM);
                    } else {
                        itM.getPersistentDataContainer().set(k, PersistentDataType.INTEGER, 1);
                        it.setItemMeta(itM);
                    }
                }
                eco.withdrawPlayer(p.getName(), lvl*100000);
                p.sendMessage("§aAmélioration réalisée avec succès !");
                open();
            } else {
                p.sendMessage("§cVous n'avez pas assez d'argent pour faire cela !");
            }



        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        blacklist.add(Material.NETHERITE_HELMET);
        blacklist.add(Material.NETHERITE_CHESTPLATE);
        blacklist.add(Material.NETHERITE_LEGGINGS);
        blacklist.add(Material.NETHERITE_BOOTS);

        ItemStack[] armor = player.getInventory().getArmorContents();

        int i = 3;
        for(ItemStack it : armor) {
            inventory.setItem(13+(9*i), it);
            i--;
        }

        ItemStack up = new ItemStack(Material.CHEST);
        ItemMeta upM = up.getItemMeta();
        upM.setDisplayName("§6§lAméliorer");
        List<String> lore = new ArrayList<String>();
        int lvl = 0;
        if(armor[0].hasItemMeta() && armor[0].getItemMeta().getPersistentDataContainer().has(new NamespacedKey(SpartaStuff.getInstance(), "level"))) {
            lvl = armor[0].getItemMeta().getPersistentDataContainer().get(new NamespacedKey(SpartaStuff.getInstance(), "level"), PersistentDataType.INTEGER);
        }
        if(lvl != 2) {
            lore.add("§aPassage au niveau " + (lvl+1)%10);
            lore.add("§aValeur de l'amélioration: " + (lvl+1)*100000);
        } else {
            lore.add("§cVous avez atteint le niveau maximal !");
        }
        upM.setLore(lore);
        up.setItemMeta(upM);
        inventory.setItem(4, up);

    }
}
