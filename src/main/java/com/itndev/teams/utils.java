package com.itndev.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class utils {

    public static void broadcastwarn(String k) {

        System.out.println("[Warning] " + k);

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.translateAlternateColorCodes('&', "[Warning] " + k));
        }

    }

    @Deprecated
    public static void teamchat(String playeruuid, String message) {
        ArrayList<String> playeruuidarray = storage.teammember.get(storage.teampvp.get(playeruuid));
        for(String playeruuids : playeruuidarray) {
            OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
            if(msgpl.isOnline()) {
                Player pl = (Player) msgpl;
                main.sendmsg(pl, "&a&l(&f팀채팅&a&l) &r&f" + listener.name2name(listener.uuid2name(playeruuid)) + " &7: &r&f" + message);
            }


        }
    }
    @Deprecated
    public static void teamnotify(String playeruuid, String targetuuid, String message, String trueorfalse) {
        if(trueorfalse.equalsIgnoreCase("true")) {
            if (targetuuid.equalsIgnoreCase("SIBAL")) {
                for (String playeruuids : storage.teammember.get(storage.teampvp.get(playeruuid))) {
                    if (playeruuids != targetuuid) {
                        OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
                        if(msgpl.isOnline()) {
                            Player pl = (Player) msgpl;
                            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                        }
                        //msgpl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                    }
                }

            } else {
                for (String playeruuids : storage.teammember.get(storage.teampvp.get(playeruuid))) {
                    if (playeruuids != targetuuid) {
                        OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
                        if(msgpl.isOnline()) {
                            Player pl = (Player) msgpl;
                            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                        }
                        //msgpl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                    }
                }
                Bukkit.getPlayer(listener.uuid2name(targetuuid)).sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
            }
        } else {

            Bukkit.getPlayer(listener.uuid2name(targetuuid)).sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
        }
    }
    public static void sendmsg(Player p, String msg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}