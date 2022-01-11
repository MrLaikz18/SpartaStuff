package fr.mrlaikz.spartastuff;

import fr.mrlaikz.spartastuff.commands.Stuff;
import fr.mrlaikz.spartastuff.listeners.InteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

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

        //COMMANDS
        getCommand("stuff").setExecutor(new Stuff(this));

        //MISC
        getLogger().info("Plugin Actif");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Innactif");
    }

    public Manager getManager() {
        return manager;
    }

    public String strConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }


}
