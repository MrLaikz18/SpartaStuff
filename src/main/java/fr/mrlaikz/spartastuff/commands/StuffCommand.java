package fr.mrlaikz.spartastuff.commands;

import fr.mrlaikz.spartastuff.Armor;
import fr.mrlaikz.spartastuff.Manager;
import fr.mrlaikz.spartastuff.SpartaStuff;
import fr.mrlaikz.spartastuff.menu.StuffMenu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StuffCommand implements CommandExecutor {

    private SpartaStuff plugin;
    private Manager manager;

    public StuffCommand(SpartaStuff plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0) {
                Armor a = manager.getSpecialArmor(p.getInventory().getArmorContents());
                if(a == null) {
                    p.sendMessage("§cVous ne possedez pas l'armure complète !");
                    return false;
                }
                StuffMenu menu = new StuffMenu(p);
                menu.open();
            }

            if(p.hasPermission("sparta.stuff")) {

                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        Bukkit.getOnlinePlayers().forEach(player -> manager.removeStuffEffect(player));
                        plugin.reloadConfig();
                        manager.loadArmors();
                        Bukkit.getOnlinePlayers().forEach(player -> manager.giveStuffEffect(player));
                        p.sendMessage(plugin.strConfig("message.reloaded"));
                    }

                    if(args[0].equalsIgnoreCase("get")) {
                        for(Armor a : manager.getArmors()) {
                            p.sendMessage(a.getName());
                        }
                    }

                    if(args[0].equalsIgnoreCase("info")) { 
                        ItemStack h = p.getInventory().getHelmet();
                        p.sendMessage("ID: "+h.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "id"), PersistentDataType.INTEGER));
                        p.sendMessage("LVL: " + h.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "level"), PersistentDataType.INTEGER));
                    }

                    if(args[0].equalsIgnoreCase("lvlup")) {
                        for(ItemStack it : p.getInventory().getArmorContents()) {
                            ItemMeta itM = it.getItemMeta();
                            NamespacedKey k = new NamespacedKey(SpartaStuff.getInstance(), "level");
                            if(itM.getPersistentDataContainer().has(k, PersistentDataType.INTEGER)) {
                                int lvl = itM.getPersistentDataContainer().get(k, PersistentDataType.INTEGER);
                                lvl +=1;
                                itM.getPersistentDataContainer().set(k, PersistentDataType.INTEGER, lvl);
                                it.setItemMeta(itM);
                            } else {
                                itM.getPersistentDataContainer().set(k, PersistentDataType.INTEGER, 1);
                                it.setItemMeta(itM);
                            }
                        }
                    }

                }else if(args.length == 2){
                    if(args[0].equalsIgnoreCase("getArmor")) {
                        String name = args[1];
                        Optional<Armor> armorOptional = manager.getArmors().stream().filter(a -> a.getName().equalsIgnoreCase(name)).findFirst();
                        if(armorOptional.isPresent()){
                            for (ItemStack itemStack : armorOptional.get().getArmorContent()) {
                                p.getInventory().addItem(itemStack);
                            }
                        }else{
                            p.sendMessage("§cCette armure n'existe pas.");
                        }
                    } else if(args[0].equalsIgnoreCase("getbyid")) {
                        int id = Integer.parseInt(args[1]);
                        Armor a = manager.getArmorById(id);
                        if(a != null){
                            for (ItemStack itemStack : a.getArmorContent()) {
                                p.getInventory().addItem(itemStack);
                            }
                        }else{
                            p.sendMessage("§cCette armure n'existe pas.");
                        }
                    }
                }
                if(args.length == 5) {
                    if(args[0].equalsIgnoreCase("set")) {
                        if(p.getInventory().getArmorContents().length == 4) {
                            String name = args[1];
                            PotionEffectType eff = PotionEffectType.getByName(args[2]);
                            int amp = Integer.parseInt(args[3]);
                            int id = Integer.parseInt(args[4]);

                            ItemStack content[] = new ItemStack[4];
                            int i = 0;
                            for(ItemStack it : p.getInventory().getArmorContents()) {
                                ItemMeta itM = it.getItemMeta();
                                itM.getPersistentDataContainer().set(new NamespacedKey(plugin, "id"), PersistentDataType.INTEGER, id);
                                it.setItemMeta(itM);
                                content[i] = it;
                                i++;
                            }

                            manager.writeArmor(content, name, eff, amp, id);
                            p.sendMessage(plugin.strConfig("message.armor_added"));
                        } else {
                            p.sendMessage("§cTu n'a pas toute l'armure sur toi !");
                        }
                    }
                }

            }
        }

        return false;
    }
}
