package fr.mrlaikz.spartastuff;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Manager {

    private SpartaStuff plugin;
    private FileConfiguration config;

    private static List<Armor> listArmors = new ArrayList<>();

    public Manager(SpartaStuff plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public static List<Armor> getArmors() {
        return listArmors;
    }

    public void loadArmors() {
        listArmors.clear();
        if(config.getConfigurationSection("armors") != null) {
            for (String armors : config.getConfigurationSection("armors").getKeys(false)) {
                ItemStack helmet = config.getItemStack("armors." + armors + ".helmet");
                ItemStack chestplate = config.getItemStack("armors." + armors + ".chestplate");
                ItemStack leggings = config.getItemStack("armors." + armors + ".leggings");
                ItemStack boots = config.getItemStack("armors." + armors + ".boots");
                PotionEffectType type = PotionEffectType.getByName(config.getString("armors." + armors + ".effect"));
                int amp = config.getInt("armors." + armors + ".amplifier");
                int id = config.getInt("armors." + armors + ".id");
                Armor a = new Armor(armors, helmet, chestplate, leggings, boots, type, amp, id);
                listArmors.add(a);
            }
        }
    }

    public void writeArmor(ItemStack content[], String name, PotionEffectType effect, int amp, int id) {
        if(!config.isConfigurationSection("armors." + name)) {
            config.set("armors." + name + ".helmet", content[0]);
            config.set("armors." + name + ".chestplate", content[1]);
            config.set("armors." + name + ".leggings", content[2]);
            config.set("armors." + name + ".boots", content[3]);
            config.set("armors." + name + ".effect", effect.getName());
            config.set("armors." + name + ".amplifier", amp);
            config.set("armors." + name + ".id", id);
            plugin.saveConfig();
        } else {
            plugin.getLogger().info("Impossible d'écrire cette armure");
        }
    }

    public Armor getSpecialArmor(ItemStack[] armorContent){
        NamespacedKey keyid = new NamespacedKey(plugin, "id");
        if (Arrays.stream(armorContent).anyMatch(Objects::isNull)) {
            return null;
        }
        if(!armorContent[0].hasItemMeta() || !armorContent[0].getItemMeta().getPersistentDataContainer().has(keyid)) {
            return null;
        }
        int id0 = armorContent[0].getItemMeta().getPersistentDataContainer().get(keyid, PersistentDataType.INTEGER);
        for(ItemStack it : armorContent) {
            int idb = it.getItemMeta().getPersistentDataContainer().get(keyid, PersistentDataType.INTEGER);
            if(idb != id0) {
                return null;
            }
        }
        return getArmorById(id0);
    }

    public Armor getArmorById(int id) {
        for(Armor a : getArmors()) {
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public int getArmorId(ItemStack content[]) {
        Armor a = getSpecialArmor(content);
        ItemStack it = a.getArmorContent()[0];
        ItemMeta itM = it.getItemMeta();
        NamespacedKey k = new NamespacedKey(plugin, "id");
        return itM.getPersistentDataContainer().get(k, PersistentDataType.INTEGER);
    }

    public void giveStuffEffect(Player player){
        Armor armor = getSpecialArmor(player.getInventory().getArmorContents());
        if (armor != null) {
            int lvl = getArmorLevel(player.getInventory().getArmorContents());
            player.addPotionEffect(new PotionEffect(armor.getEffect(), Integer.MAX_VALUE, armor.getAmplifier()+lvl));
        }
    }

    public void removeStuffEffect(Player player){
        Armor armor = getSpecialArmor(player.getInventory().getArmorContents());
        if (armor != null) {
            player.removePotionEffect(armor.getEffect());
        }
    }

    public int getArmorLevel(ItemStack armorContent[]) {
        Armor a = getSpecialArmor(armorContent);
        if(a == null) {
            return 0;
        }
        ItemMeta itM = armorContent[0].getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "level");
        if(itM.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
            return itM.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        }
        return 0;
    }

}
