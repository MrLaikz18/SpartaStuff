package fr.mrlaikz.spartastuff;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Armor {

    private String name;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private PotionEffectType effect;
    private int amplifier;

    public Armor(String name, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, PotionEffectType effect, int amplifier) {
        this.name = name;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.effect = effect;
        this.amplifier = amplifier;
    }

    public ArrayList<ItemStack> getArmor() {
        ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
        armor.add(helmet);
        armor.add(chestplate);
        armor.add(leggings);
        armor.add(boots);
        return armor;
    }

    public PotionEffectType getEffect() {
        return effect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public String getName() {
        return name;
    }

}
