package com.itndev.teams;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class main extends JavaPlugin {

    FileConfiguration config = getConfig();
    public final Logger logger = Logger.getLogger("Minecraft");

    public static main instance;

    public String clientname;

    public String duelmapworld;

    public String dbaddress;

    public String password;

    public Boolean teamtoggle;

    public static Economy econ = null;

    public static main getInstance() {
        return instance;
    }

    @Deprecated
    @Override
    public void onEnable() {

        instance = this;
        main.getInstance().teamtoggle = false;

        this.config.addDefault("clientname", "client1");
        this.config.addDefault("duelworldname", "duel");
        this.config.addDefault("bannedteamname", "fuck");
        this.config.addDefault("dbaddress", "db.itndev.com");
        this.config.addDefault("redispassword", "password");
        clientname = this.config.getString("clientname").toString();
        duelmapworld = this.config.getString("duelworldname");
        dbaddress = this.config.getString("dbaddress");
        password = this.config.getString("redispassword");
        this.config.options().copyDefaults(true);
        saveConfig();

        storage.createlocalstorage();
        //storage.getStorage().options().copyDefaults(true);
        storage.saveStorage();
        jedis.jedisTest();
        Bukkit.getPluginManager().registerEvents(new listener(), (Plugin)this);
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("팀"))).setExecutor(new commands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("대전"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("정보"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("checkclientname"))).setExecutor(new commands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("checkhashmaps"))).setExecutor(new commands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("updateredis"))).setExecutor(new commands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("syncteams"))).setExecutor(new synctask());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("teamadmin"))).setExecutor(new synctask());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            System.out.println("sucess! hooked into placeholderapi");
            new placeholder().register();
        } else {
            System.out.println("placeholderapi not working ... might make trouble");
        }
        if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }




        if (getConfig().contains("nameuuid.")) {
            storage.onRestorenameuuidData();
        }
        if (getConfig().contains("uuidname.")) {
            storage.onRestoreuuidnameData();
        }
        if (getConfig().contains("teams.")) {
            storage.onRestoreteamsData();
        }
        if (getConfig().contains("teammember.")) {
            storage.onRestoreteammemberData();
        }
        if (getConfig().contains("teamrank.")) {
            storage.onRestoreteamrankData();
        }
        if (getConfig().contains("teampvp.")) {
            storage.onRestoreteampvpData();
        }
        if (getConfig().contains("namename.")) {
            storage.onRestorenamenameData();
        }
        String banname = this.config.getString("bannedteamname");
        if(banname.contains(",")) {
            String[] names = banname.split(",");
            for(String name : names) {
                commands.bannedteamname.put(name, name);
            }

        } else {
            commands.bannedteamname.put(banname, banname);
        }





    }

    @Override
    public void onDisable() {

        instance = null;




        if(!listener.uuidname.isEmpty()) {
            storage.onSaveuuidnameData();
        }
        if(!listener.nameuuid.isEmpty()) {
            storage.onSavenameuuidData();
        }
        if(!storage.teammember.isEmpty()) {
            storage.onSaveteammemberData();
        }
        if(!storage.teams.isEmpty()) {
            storage.onSaveteamsData();
        }
        if(!storage.teampvp.isEmpty()) {
            storage.onSaveteampvpData();
        }
        if(!storage.teamrank.isEmpty()) {
            storage.onSaveteamrankData();
        }
        if(!listener.namename.isEmpty()) {
            storage.onSavenamenameData();
        }
        saveConfig();

        // Plugin shutdown logic
    }
    public static void sendmsg(Player p, String v) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', v));

    }
    public static void senduuidmsg(String uuid, String v) {
        Player p = Bukkit.getPlayer(uuid);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', v));
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

    public void saveAllData() {
        if(!listener.uuidname.isEmpty()) {
            storage.onSaveuuidnameData();
        }
        if(!listener.nameuuid.isEmpty()) {
            storage.onSavenameuuidData();
        }
        if(!storage.teammember.isEmpty()) {
            storage.onSaveteammemberData();
        }
        if(!storage.teams.isEmpty()) {
            storage.onSaveteamsData();
        }
        if(!storage.teampvp.isEmpty()) {
            storage.onSaveteampvpData();
        }
        if(!storage.teamrank.isEmpty()) {
            storage.onSaveteamrankData();
        }
        if(!listener.namename.isEmpty()) {
            storage.onSavenamenameData();
        }
        saveConfig();
    }
}
