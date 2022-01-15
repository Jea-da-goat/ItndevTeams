package com.itndev.teams.ItemUtils;

import com.itndev.teams.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ItemFixer {


    @Deprecated
    public static Boolean checkifbroken(ItemStack broken) {
        ItemStack item = broken.clone();
        if(item != null && item.getType() != Material.AIR && item.hasItemMeta()) {
            ItemMeta tempmeta = item.getItemMeta();
            tempmeta.setDisplayName(tempmeta.getDisplayName());
            tempmeta.setLore(tempmeta.getLore());
            item.setItemMeta(tempmeta);
        }
        if(item != null && item.getType() != Material.AIR && broken.isSimilar(item)) {
            return false;
        } else {
            return true;
        }
    }

    @Deprecated
    public static void FixInventory(Player p) {
        for(ItemStack item : p.getInventory().getContents()) {
            if(item != null) {
                if(item.hasItemMeta()) {
                    ItemMeta tempmeta = item.getItemMeta();
                    tempmeta.setDisplayName(tempmeta.getDisplayName().toString());
                    tempmeta.setLore(tempmeta.getLore());
                    item.setItemMeta(tempmeta);
                }
            }
        }
    }


    @Deprecated
    public static void ItemFixTask() {
        new BukkitRunnable() {
            public void run() {
                ArrayList<Player> brokenusers = new ArrayList<>();
                for(Player online : Bukkit.getOnlinePlayers()) {
                    for(ItemStack item : online.getInventory().getContents()) {
                        if(item != null && item.getType() != Material.AIR && checkifbroken(item.clone())) {
                            if(!brokenusers.contains(online)) {
                                brokenusers.add(online);
                            }
                            break;
                        }
                    }
                }
                if(!brokenusers.isEmpty()) {
                    new BukkitRunnable() {
                        public void run() {
                            for(Player broken : brokenusers) {
                                FixInventory(broken);
                            }
                        }
                    }.runTask(main.getInstance());
                }
            }
        }.runTaskTimerAsynchronously(main.getInstance(), 20L, 60L);
    }

}
