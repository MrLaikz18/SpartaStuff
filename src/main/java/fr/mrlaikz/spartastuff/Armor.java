package fr.mrlaikz.spartastuff;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Armor {

    private int id;
    private String name;
    private PotionEffectType effect;
    private int amplifier;
    private ItemStack[] armorContent;

    public Armor(String name, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, PotionEffectType effect, int amplifier, int id) {
        this.name = name;
        this.effect = effect;
        this.amplifier = amplifier;
        armorContent = new ItemStack[4];
        armorContent[0] = helmet;
        armorContent[1] = chestplate;
        armorContent[2] = leggings;
        armorContent[3] = boots;
        this.id = id;
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

    public int getId() {
        return id;
    }

}
