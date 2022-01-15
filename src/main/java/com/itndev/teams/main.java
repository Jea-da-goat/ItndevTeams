package com.itndev.teams;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
//import com.earth2me.essentials.Essentials;
import com.sun.tools.jdi.Packet;
import net.kyori.adventure.sound.Sound;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public final class main extends JavaPlugin {

    FileConfiguration config = getConfig();
    public final Logger logger = Logger.getLogger("Minecraft");

    public static main instance;

    public Boolean chattoggle;

    public String clientname;

    public String duelmapworld;

    public String dbaddress;

    public int dbport;

    public String password;

    public Boolean teamtoggle;

    public Boolean ShouldUsePermLore;

    public static Economy econ = null;

    private static String RedisSyncKey = "";

    public static String getRedisSyncKey() {
        return RedisSyncKey;
    }

    //public static Essentials ess = null;

    public static main getInstance() {
        return instance;
    }

    @Deprecated
    @Override
    public void onEnable() {
        effectlist.effectlistsetup();

        instance = this;

        //ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        main.getInstance().teamtoggle = false;

        this.config.addDefault("clientname", "client1");
        this.config.addDefault("duelworldname", "duel");
        this.config.addDefault("bannedteamname", "fuck");
        this.config.addDefault("redissynckey", "synckey");
        this.config.addDefault("dbaddress", "db.itndev.com");
        this.config.addDefault("dbport", "6614");
        this.config.addDefault("redispassword", "54rg46ujhy7ju57wujndt35ytgryeutwefer4rt34rd34rsfg6hdf43truhgfwgr348yfgcs");
        this.config.addDefault("ShouldUsePermLore", "false");
        clientname = this.config.getString("clientname");
        duelmapworld = this.config.getString("duelworldname");
        dbaddress = this.config.getString("dbaddress");
        dbport = 6614;
        password = this.config.getString("redispassword");
        RedisSyncKey = this.config.getString("redissynckey");
        ShouldUsePermLore = Boolean.getBoolean(this.config.getString("ShouldUsePermLore"));

        this.config.options().copyDefaults(true);
        chattoggle = false;
        saveConfig();

        storage.createlocalstorage();
        //storage.getStorage().options().copyDefaults(true);
        storage.saveStorage();
        listener.createlocalstorage();
        listener.saveStorage();

        duelcommand.createarenastorage();
        //duelcommand.savearenaStorage();
        //duelcommand.restorearenalocations();

        jedis.jedisTest();
        Bukkit.getPluginManager().registerEvents(new listener(), (Plugin)this);
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("팀"))).setExecutor(new commands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("대전"))).setExecutor(new duelcommand());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("아레나"))).setExecutor(new duelcommand());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("정보"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("잠금"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("스폰"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("창고"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("뮤트"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("뮤트해제"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("핫타임"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("힐러"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("추첨시작"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("기본템"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("돈"))).setExecutor(new teamcommands());//exchangetest111
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("chattoggle"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("exchange"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("test111"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("faxcore"))).setExecutor(new teamcommands());
        ((PluginCommand) Objects.<PluginCommand>requireNonNull(getCommand("candestroy"))).setExecutor(new teamcommands());
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
        loadAllData();
        utils.gamemodefixer();


        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        pm.addPacketListener(new packetadapt(main.getInstance(), PacketType.Play.Client.TAB_COMPLETE));
        /*pm.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
            @Override
            //public void onPacketSending
            public void onPacketSending(PacketEvent e) {
                //PacketType packetType = e.getPacketType();
                PacketContainer packet = e.getPacket();
                String soundName = packet.getStrings().read(0);
                if(soundName.toLowerCase(Locale.ROOT).contains("player") || soundName.toLowerCase(Locale.ROOT).contains("player.attack")) {
                    e.setCancelled(true);
                    return;
                }
                if(soundName.equalsIgnoreCase("entity.player.attack.sweep") || soundName.equalsIgnoreCase("entity.player.attack.weak") || soundName.equalsIgnoreCase("entity.player.attack.knockback") || soundName.equalsIgnoreCase("entity.player.attack.strong") || soundName.equalsIgnoreCase("entity.player.attack.nodamage") || soundName.equalsIgnoreCase("entity.player.attack.crit")) {
                    e.setCancelled(true);
                    return;
                }
                if(soundName.equalsIgnoreCase("entity_player_attack_sweep") || soundName.equalsIgnoreCase("entity_player_attack_weak") || soundName.equalsIgnoreCase("entity_player_attack_knockback") || soundName.equalsIgnoreCase("entity_player_attack_strong") || soundName.equalsIgnoreCase("entity_player_attack_nodamage") || soundName.equalsIgnoreCase("entity_player_attack_crit")) {
                    e.setCancelled(true);
                    return;
                }
            }
        });*/
        packetadapt.Setupallowedcommands();

        String banname = this.config.getString("bannedteamname");
        if(banname.contains(",")) {
            String[] names = banname.split(",");
            for(String name : names) {
                commands.bannedteamname.put(name, name);
            }

        } else {
            commands.bannedteamname.put(banname, banname);
        }
        //duelcommand.setupduels();




        duelcommand.setupduels();


        /*
        * ====================================
        *
        *  LOOP TASKS UNDER
        *
        *  USED FOR DATABASE CONNECTIONS
        *
        *  RUNNING ASYNC
        *
        * =====================================
        * */
        startuploop.StartUpLoopsTEMP();

    }

    @Override
    public void onDisable() {

        jedis.getJedisPool().close();
        instance = null;
        try {
            storage.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            listener.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            duelcommand.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(!listener.uuidname.isEmpty()) {
            listener.onSaveuuidnameData();
        }
        if(!listener.nameuuid.isEmpty()) {
            listener.onSavenameuuidData();
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
            listener.onSavenamenameData();
        }
        if(!duelcommand.locations.isEmpty()) {
            duelcommand.savearenalocations();
        }
        listener.saveStorage();
        storage.saveStorage();
        duelcommand.savearenaStorage();



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
        try {
            storage.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            listener.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            duelcommand.resetlocalstorage();
        } catch (IOException e) {
            e.printStackTrace();
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
        if(!listener.uuidname.isEmpty()) {
            listener.onSaveuuidnameData();
        }
        if(!listener.nameuuid.isEmpty()) {
            listener.onSavenameuuidData();
        }
        if(!listener.namename.isEmpty()) {
            listener.onSavenamenameData();
        }
        if(!duelcommand.locations.isEmpty()) {
            duelcommand.savearenalocations();
        }





        duelcommand.savearenaStorage();
        saveConfig();
    }
    public void loadAllData() {
        if (listener.customlocalstorage.contains("nameuuid.")) {
            listener.onRestorenameuuidData();
        }
        if (listener.customlocalstorage.contains("uuidname.")) {
            listener.onRestoreuuidnameData();
        }
        if (storage.customlocalstorage.contains("teams.")) {
            storage.onRestoreteamsData();
        }
        if (storage.customlocalstorage.contains("teammember.")) {
            storage.onRestoreteammemberData();
        }
        if (storage.customlocalstorage.contains("teamrank.")) {
            storage.onRestoreteamrankData();
        }
        if (storage.customlocalstorage.contains("teampvp.")) {
            storage.onRestoreteampvpData();
        }
        if (listener.customlocalstorage.contains("namename.")) {
            listener.onRestorenamenameData();
        }
        duelcommand.restorearenalocations();
    }
    public static String Args2String(String[] args, int start) {
        String L = "";
        for (int x = start; x < args.length; x++)
            L = L + args[x] + " ";
        L = L.substring(0, L.length() - 1);
        return L;
    }
}
