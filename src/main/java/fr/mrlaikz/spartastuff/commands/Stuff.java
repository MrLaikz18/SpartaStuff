package fr.mrlaikz.spartastuff.commands;

import fr.mrlaikz.spartastuff.Armor;
import fr.mrlaikz.spartastuff.Manager;
import fr.mrlaikz.spartastuff.SpartaStuff;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class Stuff implements CommandExecutor {

    private SpartaStuff plugin;
    private Manager manager;

    public Stuff(SpartaStuff plugin) {
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
                        plugin.reloadConfig();
                        p.sendMessage(plugin.strConfig("message.reloaded"));
                    }

                    if(args[0].equalsIgnoreCase("get")) {
                        for(Armor a : manager.getArmors()) {
                            p.sendMessage(a.getName());
                        }
                    }
                }

                if(args.length == 4) {
                    if(args[0].equalsIgnoreCase("set")) {
                        String name = args[1];
                        PotionEffectType eff = PotionEffectType.getByName(args[2]);
                        int amp = Integer.parseInt(args[3]);
                        manager.writeArmor(p, name, eff, amp);
                        p.sendMessage(plugin.strConfig("message.armor_added"));
                    }
                }

            }
        }

        return false;
    }
}
