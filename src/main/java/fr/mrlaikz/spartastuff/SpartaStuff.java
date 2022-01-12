package fr.mrlaikz.spartastuff;

import fr.mrlaikz.spartastuff.commands.StuffCommand;
import fr.mrlaikz.spartastuff.listeners.InteractEvent;
import fr.mrlaikz.spartastuff.listeners.JoinQuitListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class SpartaStuff extends JavaPlugin {

    private Manager manager;

    @Override
    public void onEnable() {
        //CONFIG
        saveDefaultConfig();
        manager = new Manager(this);
        manager.loadArmors();

        //LISTENERS
        getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
        getServer().getPluginManager().registerEvents(new JoinQuitListeners(this), this);

        //COMMANDS
        getCommand("stuff").setExecutor(new StuffCommand(this));

        //MISC

        Bukkit.getOnlinePlayers().forEach(player -> manager.giveStuffEffect(player));
        getLogger().info("Plugin Actif");
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> manager.removeStuffEffect(player));
        getLogger().info("Plugin Innactif");
    }

    public Manager getManager() {
        return manager;
    }

    public String strConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }


}
