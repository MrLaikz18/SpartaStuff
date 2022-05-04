package fr.mrlaikz.spartastuff.commands;

import com.destroystokyo.paper.Namespaced;
import fr.mrlaikz.spartastuff.Armor;
import fr.mrlaikz.spartastuff.Manager;
import fr.mrlaikz.spartastuff.SpartaStuff;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

                    if(args[0].equalsIgnoreCase("lvlup")) {
                        for(ItemStack it : p.getInventory().getArmorContents()) {
                            List<String> lore = it.getLore();
                            if(lore.size() <= 5) {
                                lore.add("");
                                lore.add("§aNiveau d'amélioration:");
                                lore.add("1");
                            } else {
                                int lvl = Integer.parseInt(lore.get(6));
                                lore.remove(6);
                                lore.add(String.valueOf(lvl+1));
                            }
                            it.setLore(lore);
                        }

                        p.sendMessage("§aL'armure a bien été améliorée !");

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
                    }
                }
                if(args.length == 4) {
                    if(args[0].equalsIgnoreCase("set")) {
                        if(p.getInventory().getArmorContents().length == 4) {
                            String name = args[1];
                            PotionEffectType eff = PotionEffectType.getByName(args[2]);
                            int amp = Integer.parseInt(args[3]);
                            manager.writeArmor(p, name, eff, amp);
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
