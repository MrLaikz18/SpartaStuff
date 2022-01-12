package fr.mrlaikz.spartastuff;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Manager {

    private SpartaStuff plugin;
    private FileConfiguration config;

    private List<Armor> listArmors = new ArrayList<>();

    public Manager(SpartaStuff plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public List<Armor> getArmors() {
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

    public void writeArmor(Player player, String name, PotionEffectType effect, int amp) {
        PlayerInventory inventory = player.getInventory();
        if(config.getConfigurationSection("armors." + name) == null) {
            config.set("armors." + name + ".helmet", inventory.getHelmet());
            config.set("armors." + name + ".chestplate", inventory.getChestplate());
            config.set("armors." + name + ".leggings", inventory.getLeggings());
            config.set("armors." + name + ".boots", inventory.getBoots());
            config.set("armors." + name + ".effect", effect);
            config.set("armors." + name + ".amplifier", amp);
            plugin.saveConfig();
        }
    }

    public Armor getSpecialArmor(ItemStack[] armorContent){
        if (Arrays.stream(armorContent).anyMatch(Objects::isNull)) {
            return null;
        }
        for (Armor armor : getArmors()) {
            if (Arrays.equals(armorContent, armor.getArmorContent())) {
                return armor;
            }
        }
        return null;
    }

    public void giveStuffEffect(Player player){
        Armor armor = getSpecialArmor(player.getInventory().getArmorContents());
        if (armor != null) {
            player.addPotionEffect(new PotionEffect(armor.getEffect(), Integer.MAX_VALUE, armor.getAmplifier()));
        }
    }

    public void removeStuffEffect(Player player){
        Armor armor = getSpecialArmor(player.getInventory().getArmorContents());
        if (armor != null) {
            player.removePotionEffect(armor.getEffect());
        }
    }

}
