package com.itndev.teams;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class storage {
    public static ConcurrentHashMap<String, String> teampvp = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> teams = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> teamrank = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ArrayList<String>> teammember = new ConcurrentHashMap<>();

    public static HashMap<String, String> disbandq = new HashMap<>(); //flushable

    public static HashMap<String, String> proxyonline = new HashMap<>();

    public static File file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");

    public static FileConfiguration customlocalstorage;

    public static void createlocalstorage() {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        }
        customlocalstorage = YamlConfiguration.loadConfiguration(file);
    }
    public static void resetlocalstorage() throws IOException {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        file.delete();
        file.createNewFile();
        customlocalstorage.getKeys(false).forEach(key ->{
            customlocalstorage.set(key, null);
        });
        saveStorage();


    }


    public static void saveStorage() {

        try {
            customlocalstorage.save(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static FileConfiguration getStorage() {
        return customlocalstorage;
    }
    public static void reloadStorage() {
        customlocalstorage = YamlConfiguration.loadConfiguration(file);
    }







    public static void onSaveteamrankData() {
        for (Map.Entry<String, String> entry : storage.teamrank.entrySet())
            storage.getStorage().set("teamrank." + (String)entry.getKey(), entry.getValue());
        //saveStorage();
    }
    public static void onRestoreteamrankData() {
        storage.getStorage().getConfigurationSection("teamrank.").getKeys(false).forEach(key -> {
            String v = storage.getStorage().get("teamrank." + key).toString();
            storage.teamrank.put(key, v);
        });
    }

    public static void onSaveteampvpData() {
        for (Map.Entry<String, String> entry : storage.teampvp.entrySet())
            storage.getStorage().set("teampvp." + (String)entry.getKey(), entry.getValue());
        //saveStorage();
    }
    public static void onRestoreteampvpData() {
        storage.getStorage().getConfigurationSection("teampvp.").getKeys(false).forEach(key -> {
            String v = storage.getStorage().get("teampvp." + key).toString();
            storage.teampvp.put(key, v);
        });
    }
    public static void onSaveteamsData() {
        for (Map.Entry<String, String> entry : storage.teams.entrySet())
            storage.getStorage().set("teams." + (String)entry.getKey(), entry.getValue());
        //saveStorage();
    }
    public static void onRestoreteamsData() {
        storage.getStorage().getConfigurationSection("teams.").getKeys(false).forEach(key -> {
            String v = storage.getStorage().get("teams." + key).toString();
            storage.teams.put(key, v);
        });
    }

    public static void onSaveteammemberData() {
        for (Map.Entry<String, ArrayList<String>> entry : storage.teammember.entrySet())
            storage.getStorage().set("teammember." + (String)entry.getKey(), entry.getValue());
        //saveStorage();
    }
    public static void onRestoreteammemberData() {
        storage.getStorage().getConfigurationSection("teammember.").getKeys(false).forEach(key -> {
            ArrayList<String> v = (ArrayList<String>)storage.getStorage().get("teammember." + key);
            storage.teammember.put(key, v);
        });
    }





















































    public static String getteamname(Player p) {

        if(!storage.teamrank.get(p.getUniqueId().toString()).equals("nomad")) {
            return storage.teams.get(storage.teampvp.get(p.getUniqueId().toString()));
        } else {
            return "";
        }

    }
    public static String getformattedteamname(Player p) {

        if(!getteamname(p).equals("")) {
            return "&f[&a" + getteamname(p) + "&f]";
        } else {
            return "";
        }

    }
    public static String getrank(Player p) {
        return storage.teamrank.get(p.getUniqueId().toString());

    }
    public static String getrank2(String v) {
        return storage.teamrank.get(v);
    }
    public static String getrankformattedteamname(Player p) {
        if(getrank(p).equals("nomad")) {
            return "";
        } else if(getrank(p).equals("member")) {
            return "";
        } else if(getrank(p).equals("coleader")) {
            return "&a&l부리더 ";
        } else if(getrank(p).equals("leader")) {
            return "&a&l리더 ";
        } else {
            return "";
        }
    }

}
