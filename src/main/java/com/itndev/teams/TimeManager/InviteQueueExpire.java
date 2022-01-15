package com.itndev.teams.TimeManager;

import com.itndev.teams.commands;
import com.itndev.teams.listener;
import com.itndev.teams.main;
import com.itndev.teams.utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class InviteQueueExpire {

    private static HashMap<String, Integer> queueexpire = new HashMap<>();

    public static void invitequeueexpire() {
        HashMap<String, ArrayList<String>> hm = commands.inviteq;
        new BukkitRunnable() {
            @Override
            public void run() {
                for(String key : hm.keySet()) {
                    for(String teamname : hm.get(key)) {
                        String keyname = key + "=" + teamname;
                        int time = queueexpire.get(keyname);
                        if(queueexpire.containsKey(keyname)) {
                           queueexpire.put(keyname, time - 1);
                           if(time < 2) {
                               queueexpire.remove(keyname);
                               ArrayList<String> templist = commands.inviteq.get(key);
                               if(templist.contains(teamname)) {
                                   templist.remove(teamname);
                                   commands.inviteq.put(key, templist);
                                   utils.sendmsg(Bukkit.getPlayer(listener.uuid2name(key)), "&a&o&l[ &r&f팀 &a&o&l] &r&f&c" + teamname + " &r&f 에서 온 초대가 만료되었습니다");
                               }
                           }
                        } else {
                            queueexpire.put(keyname, 120);
                        }
                    }
                }
                this.cancel();
            }
        }.runTaskTimerAsynchronously(main.getInstance(), 20L, 20L);

    }
}
