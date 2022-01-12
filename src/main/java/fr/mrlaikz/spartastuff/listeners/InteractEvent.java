package fr.mrlaikz.spartastuff.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import fr.mrlaikz.spartastuff.Armor;
import fr.mrlaikz.spartastuff.Manager;
import fr.mrlaikz.spartastuff.SpartaStuff;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.Objects;

public class InteractEvent implements Listener {

    private Manager manager;

    public InteractEvent(SpartaStuff plugin) {
        this.manager = plugin.getManager();
    }

    @EventHandler
    public void onEquip(PlayerArmorChangeEvent e){
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (e.getNewItem() != null && e.getNewItem().getType() != Material.AIR) {
            Armor armor = getSpecialArmor(inventory.getArmorContents());
            if (armor != null) {
                PotionEffect currentEffect = player.getPotionEffect(armor.getEffect());
                if(currentEffect == null || currentEffect.getAmplifier() < armor.getAmplifier()){
                    player.addPotionEffect(new PotionEffect(armor.getEffect(), Integer.MAX_VALUE, armor.getAmplifier()));
                }
            }
        } else {
            ItemStack[] oldArmorContent = inventory.getArmorContents().clone();
            switch (e.getSlotType()){
                case FEET -> oldArmorContent[0] = e.getOldItem();
                case LEGS -> oldArmorContent[1] = e.getOldItem();
                case CHEST -> oldArmorContent[2] = e.getOldItem();
                case HEAD -> oldArmorContent[3] = e.getOldItem();
            }
            Armor oldArmor = getSpecialArmor(oldArmorContent);
            if(oldArmor != null){
                player.removePotionEffect(oldArmor.getEffect());
            }
        }
    }

    private Armor getSpecialArmor(ItemStack[] armorContent){
        if (Arrays.stream(armorContent).anyMatch(Objects::isNull)) {
            return null;
        }
        for (Armor armor : manager.getArmors()) {
            if (Arrays.equals(armorContent, armor.getArmorContent())) {
                return armor;
            }
        }
        return null;
    }

}
