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
            manager.giveStuffEffect(player);
        } else {
            ItemStack[] oldArmorContent = inventory.getArmorContents().clone();
            switch (e.getSlotType()){
                case FEET -> oldArmorContent[0] = e.getOldItem();
                case LEGS -> oldArmorContent[1] = e.getOldItem();
                case CHEST -> oldArmorContent[2] = e.getOldItem();
                case HEAD -> oldArmorContent[3] = e.getOldItem();
            }
            Armor oldArmor = manager.getSpecialArmor(oldArmorContent);
            if(oldArmor != null){
                player.removePotionEffect(oldArmor.getEffect());
            }
        }

    }

}
