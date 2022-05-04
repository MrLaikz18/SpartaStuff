package fr.mrlaikz.spartastuff;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Armor {

    private String name;
    private PotionEffectType effect;
    private int amplifier;
    private ItemStack[] armorContent;

    public Armor(String name, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, PotionEffectType effect, int amplifier) {
        this.name = name;
        this.effect = effect;
        this.amplifier = amplifier;
        armorContent = new ItemStack[4];
        armorContent[0] = boots;
        armorContent[1] = leggings;
        armorContent[2] = chestplate;
        armorContent[3] = helmet;
    }

    public ItemStack[] getArmorContent(){
        return armorContent;
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
