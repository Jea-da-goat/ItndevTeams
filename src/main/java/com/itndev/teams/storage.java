package com.itndev.teams;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class storage {
    public static HashMap<String, String> teampvp = new HashMap<>();

    public static HashMap<String, String> teams = new HashMap<>();

    public static HashMap<String, String> teamrank = new HashMap<>();

    public static HashMap<String, ArrayList<String>> teammember = new HashMap<>();

    public static HashMap<String, String> disbandq = new HashMap<>();

    public static File file;

    public static FileConfiguration customlocalstorage;

    public static void createlocalstorage() {
        file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        }
        customlocalstorage = YamlConfiguration.loadConfiguration(file);
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
            customlocalstorage.set("teamrank." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestoreteamrankData() {
        customlocalstorage.getConfigurationSection("teamrank.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("teamrank." + key).toString();
            storage.teamrank.put(key, v);
        });
    }

    public static void onSaveteampvpData() {
        for (Map.Entry<String, String> entry : storage.teampvp.entrySet())
            customlocalstorage.set("teampvp." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestoreteampvpData() {
        customlocalstorage.getConfigurationSection("teampvp.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("teampvp." + key).toString();
            storage.teampvp.put(key, v);
        });
    }
    public static void onSaveteamsData() {
        for (Map.Entry<String, String> entry : storage.teams.entrySet())
            customlocalstorage.set("teams." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestoreteamsData() {
        customlocalstorage.getConfigurationSection("teams.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("teams." + key).toString();
            storage.teams.put(key, v);
        });
    }

    public static void onSaveteammemberData() {
        for (Map.Entry<String, ArrayList<String>> entry : storage.teammember.entrySet())
            customlocalstorage.set("teammember." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestoreteammemberData() {
        customlocalstorage.getConfigurationSection("teammember.").getKeys(false).forEach(key -> {
            ArrayList<String> v = (ArrayList<String>)customlocalstorage.get("teammember." + key);
            storage.teammember.put(key, v);
        });
    }











    //uuidname nameuuid
    public static void onSaveuuidnameData() {
        for (Map.Entry<String, String> entry : listener.uuidname.entrySet())
            customlocalstorage.set("uuidname." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestoreuuidnameData() {
        customlocalstorage.getConfigurationSection("uuidname.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("uuidname." + key).toString();
            listener.uuidname.put(key, v);
        });
    }
    public static void onSavenameuuidData() {
        for (Map.Entry<String, String> entry : listener.nameuuid.entrySet())
            customlocalstorage.set("nameuuid." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestorenameuuidData() {
        customlocalstorage.getConfigurationSection("nameuuid.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("nameuuid." + key).toString();
            listener.nameuuid.put(key, v);
        });
    }
    public static void onSavenamenameData() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            customlocalstorage.set("namename." + (String)entry.getKey(), entry.getValue());
        saveStorage();
    }
    public static void onRestorenamenameData() {
        customlocalstorage.getConfigurationSection("namename.").getKeys(false).forEach(key -> {
            String v = customlocalstorage.get("namename." + key).toString();
            listener.namename.put(key, v);
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
