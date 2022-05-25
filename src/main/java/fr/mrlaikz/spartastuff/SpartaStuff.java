package fr.mrlaikz.spartastuff;

import fr.mrlaikz.spartastuff.commands.StuffCommand;
import fr.mrlaikz.spartastuff.listeners.InteractEvent;
import fr.mrlaikz.spartastuff.listeners.JoinQuitListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SpartaStuff extends JavaPlugin {

    private Manager manager;
    public static SpartaStuff instance;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        //CONFIG
        saveDefaultConfig();
        manager = new Manager(this);
        manager.loadArmors();
        instance = this;
        setupEconomy();

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

    public static SpartaStuff getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;

    }

    public static Economy getEconomy() {
        return econ;
    }
}
