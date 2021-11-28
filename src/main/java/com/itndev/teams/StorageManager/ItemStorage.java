package com.itndev.teams.StorageManager;

import com.itndev.teams.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemStorage {

    public static File ItemStorageFile = new File(main.getInstance().getDataFolder(), "ItemStorage.yml");

    public static FileConfiguration ItemStorage;

    public static void createlocalstorage() {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        if(!ItemStorageFile.exists()) {
            try {
                ItemStorageFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        ItemStorage = YamlConfiguration.loadConfiguration(ItemStorageFile);
    }
    public static void resetItemStorage() throws IOException {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        ItemStorageFile.delete();
        ItemStorageFile.createNewFile();
        ItemStorage.getKeys(false).forEach(key -> {
            ItemStorage.set(key, null);
        });
        saveItemStorage();
    }
    public static void saveItemStorage() {
        try {
            ItemStorage.save(ItemStorageFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static FileConfiguration getItemStorage() {
        return ItemStorage;
    }
    public static void reloadItemStorage() {
        ItemStorage = YamlConfiguration.loadConfiguration(ItemStorageFile);
    }
}
