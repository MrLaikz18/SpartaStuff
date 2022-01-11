package fr.mrlaikz.spartastuff;

import org.bukkit.Effect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Manager {

    private SpartaStuff plugin;
    private FileConfiguration config;

    private ArrayList<Armor> listArmors = new ArrayList<Armor>();

    public Manager(SpartaStuff plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public ArrayList<Armor> getArmors() {
        return listArmors;
    }

    public void loadArmors() {
        listArmors.clear();
        for(String armors : config.getConfigurationSection("armors").getKeys(false)) {
            ItemStack helmet = config.getItemStack("armors." + armors + ".helmet");
            ItemStack chestplate = config.getItemStack("armors." + armors + ".chestplate");
            ItemStack leggings = config.getItemStack("armors." + armors + ".leggings");
            ItemStack boots = config.getItemStack("armors." + armors + ".boots");
            PotionEffectType type = PotionEffectType.getByName(config.getString("armors." + armors + ".effect"));
            int amp = config.getInt("armors." + armors + ".amplifier");
            Armor a = new Armor(armors, helmet, chestplate, leggings, boots, type, amp);
            listArmors.add(a);
        }
    }

    public void writeArmor(Player p, String name, PotionEffectType effect, int amp) {
        if(config.getConfigurationSection("armors." + name) == null) {
            config.set("armors." + name + ".helmet", p.getInventory().getHelmet());
            config.set("armors." + name + ".chestplate", p.getInventory().getChestplate());
            config.set("armors." + name + ".leggings", p.getInventory().getLeggings());
            config.set("armors." + name + ".boots", p.getInventory().getBoots());
            config.set("armors." + name + ".effect", effect);
            config.set("armors." + name + ".amplifier", amp);
            plugin.saveConfig();
        }
    }

}
