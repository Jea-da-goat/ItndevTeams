package com.itndev.teams;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class skills {

    public static HashMap<String, HashMap<String, Integer>> Cooldowns = new HashMap<>(); //uuid , skillname , cooltime seconds



    public void Skills() {




    }

    public void Skillsloop() {



        new BukkitRunnable() {

            HashMap<String, Integer> cache;

            @Override
            public void run() {
                for(String puuid : Cooldowns.keySet()) {
                    cache = Cooldowns.get(puuid);

                    for(String skillid : cache.keySet()) {
                        int k = cache.get(skillid) - 1;


                        if(k > 0) {
                            cache.put(skillid, k);
                        }

                    }

                    cache.clear();


                }

            }
        }.runTaskTimerAsynchronously(main.getInstance(), 20L, 20);

    }

    public static Integer cooldownforskill(String UUID, String skillname) {
        if(Cooldowns.containsKey(UUID)) {
            if(Cooldowns.get(UUID).containsKey(skillname)) {
                int a = Cooldowns.get(UUID).get(skillname);
                return a;
            }
        }
        return 0;

    }




}
