package fr.mrlaikz.spartastuff.listeners;

import fr.mrlaikz.spartastuff.Armor;
import fr.mrlaikz.spartastuff.Manager;
import fr.mrlaikz.spartastuff.SpartaStuff;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.potion.PotionEffect;

public class InteractEvent implements Listener {

    private SpartaStuff plugin;
    private Manager manager;

    public InteractEvent(SpartaStuff plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getSlotType().equals(InventoryType.SlotType.ARMOR) && e.getAction().equals(InventoryAction.PLACE_ALL)) {
            if(e.getCursor() != null && e.getCursor().hasItemMeta() && e.getCursor().getItemMeta().getDisplayName() != null) {
                String name = e.getCursor().getItemMeta().getDisplayName();
                for(Armor a : manager.getArmors()) {
                    if(a.getName().equals(name)) {
                        switch(e.getCursor().getType()) { //PUT EFFECT

                            case NETHERITE_HELMET:
                                try {
                                    if (p.getInventory().getChestplate().equals(a.getArmor().get(1)) &&
                                            p.getInventory().getLeggings().equals(a.getArmor().get(2)) &&
                                            p.getInventory().getBoots().equals(a.getArmor().get(3))) {

                                        p.addPotionEffect(new PotionEffect(a.getEffect(), 999999, a.getAmplifier()));
                                        break;
                                    }
                                } catch(NullPointerException npe) {}

                            case NETHERITE_CHESTPLATE:
                                try {
                                    if (p.getInventory().getHelmet().equals(a.getArmor().get(0)) &&
                                            p.getInventory().getLeggings().equals(a.getArmor().get(2)) &&
                                            p.getInventory().getBoots().equals(a.getArmor().get(3))) {

                                        p.addPotionEffect(new PotionEffect(a.getEffect(), 999999, a.getAmplifier()));
                                        break;
                                    }
                                } catch(NullPointerException npe) {}
                            case NETHERITE_LEGGINGS:
                                try {
                                    if (p.getInventory().getHelmet().equals(a.getArmor().get(0)) &&
                                            p.getInventory().getChestplate().equals(a.getArmor().get(1)) &&
                                            p.getInventory().getBoots().equals(a.getArmor().get(3))) {

                                        p.addPotionEffect(new PotionEffect(a.getEffect(), 999999, a.getAmplifier()));
                                        break;
                                    }
                                } catch(NullPointerException npe) {}
                            case NETHERITE_BOOTS:
                                try {
                                    if (p.getInventory().getHelmet().equals(a.getArmor().get(0)) &&
                                            p.getInventory().getChestplate().equals(a.getArmor().get(1)) &&
                                            p.getInventory().getLeggings().equals(a.getArmor().get(2))) {

                                        p.addPotionEffect(new PotionEffect(a.getEffect(), 999999, a.getAmplifier()));
                                        break;
                                    }
                                } catch(NullPointerException npe) {}
                        }

                    }
                }
            }
        }

        if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            for(Armor a : manager.getArmors()) {
                if(a.getName().equals(name)) {
                    p.removePotionEffect(a.getEffect());
                }
            }
        }
    }

}
