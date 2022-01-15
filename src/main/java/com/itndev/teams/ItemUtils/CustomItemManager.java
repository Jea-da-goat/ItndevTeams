package com.itndev.teams.ItemUtils;

import com.itndev.teams.main;
import com.itndev.teams.utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CustomItemManager {

    //public HashMap<UUID, String> uuidnametype = new HashMap<>();
    public static HashMap<String, Long> uuidnamecooldown = new HashMap<>();


    @Deprecated
    public static Boolean CheckCooldown(Player p, String iteminfo, String itemname, int cooldownTime) {
        Long time = System.currentTimeMillis();
        if(uuidnamecooldown.containsKey(p.getUniqueId() + "=" + iteminfo)) {
            long secondsLeft = uuidnamecooldown.get(p.getUniqueId() + "=" + iteminfo) / 1000L + cooldownTime - time / 1000L;
            if(secondsLeft > 0L) {
                utils.sendmsg(p, "&3&l[ &f&oCooldown &3&l] &f해당 아이템 &7[&r " + itemname + " &r&7] &f을(를) 다시 사용하려면 아직 &c" + secondsLeft + "초&f가 남았습니다");
                return false;

            } else {
                uuidnamecooldown.put(p.getUniqueId() + "=" + iteminfo, time);
                return true;

            }
        } else {
            uuidnamecooldown.put(p.getUniqueId() + "=" + iteminfo, time);
            return true;

        }

    }

    @Deprecated
    public static void ItemManagerAdvanced(Player p, ItemStack item) {
        //double radius = 0;
        NamespacedKey namekey = new NamespacedKey(main.getInstance(), "CLICKTOUSEITEM");
        NamespacedKey namekey2 = new NamespacedKey(main.getInstance(), "REMOVEAMOUNT");
        String k = item.getItemMeta().getPersistentDataContainer().get(namekey, PersistentDataType.STRING);
        int c = item.getItemMeta().getPersistentDataContainer().get(namekey2, PersistentDataType.INTEGER);
        String[] temp = k.split("=");
        String[] tempcooldown = temp[temp.length - 1].split("-");
        int cooldown = Integer.valueOf(tempcooldown[1]);
        if(!CustomItemManager.CheckCooldown(p, k, item.getItemMeta().getDisplayName(), cooldown)) {
            return;
        }
        if(!k.contains("=")) {
            return;
        }
        item.setAmount(item.getAmount() - c);

        new BukkitRunnable() {
            @Override
            public void run() {
                String iteminfo = k;
                if(iteminfo.contains("=")) {
                    String[] iteminfoparts = iteminfo.split("=");
                    for(int num = 0; num < iteminfoparts.length; num++) {
                        if(!iteminfoparts[num].contains("hide")) {
                            String[] tempdata1 = iteminfoparts[num].split(":");
                            String[] tempdata2 = tempdata1[1].split("-");
                            PotionEffect po = new PotionEffect(PotionEffectType.getByName(tempdata2[0]), Integer.valueOf(tempdata2[1]) * 20, Integer.valueOf(tempdata2[2]));
                            if(tempdata1[0].equalsIgnoreCase("type1")) {
                                utils.giveTeamEffect(p, Double.valueOf(tempdata2[3]), po, true);
                            } else if(tempdata1[0].equalsIgnoreCase("type2")) {
                                utils.giveTeamEffect(p, Double.valueOf(tempdata2[3]), po, false);
                            }
                        }
                    }
                }
                this.cancel();
            }
        }.runTaskAsynchronously(main.getInstance());
    }

    @Deprecated
    public static void ItemManager(Player p, ItemStack item) {
        //double radius = 0;
        NamespacedKey namekey = new NamespacedKey(main.getInstance(), "CLICKTOUSEITEM");
        String k = item.getItemMeta().getPersistentDataContainer().get(namekey, PersistentDataType.STRING);
        String[] temp = k.split("=");
        String[] tempcooldown = temp[temp.length - 1].split("-");
        int cooldown = Integer.valueOf(tempcooldown[1]);
        if(!CustomItemManager.CheckCooldown(p, k, item.getItemMeta().getDisplayName(), cooldown)) {
            return;
        }
        if(!k.contains("=")) {
            return;
        }
        item.setAmount(item.getAmount() - 1);

        new BukkitRunnable() {
            public void run() {
                String iteminfo = k;


                /*String[] temp = iteminfo.split("=");
                String[] tempcooldown = temp[temp.length - 1].split("-");
                int cooldown = Integer.valueOf(tempcooldown[1]);*/
                //String exp = "type1:potiontype-duration-radius=hide-30";
                //String exp2 = "type1:potiontype-duration-amplifier-radius=type1:potiontype-duration-radius=hide-30";
                //type1 = to team , type2 = to enemy


                if(iteminfo.contains("=")) {
                    String[] iteminfoparts = iteminfo.split("=");
                    for(int num = 0; num < iteminfoparts.length; num++) {
                        if(!iteminfoparts[num].contains("hide")) {
                            String[] tempdata1 = iteminfoparts[num].split(":");
                            String[] tempdata2 = tempdata1[1].split("-");
                            PotionEffect po = new PotionEffect(PotionEffectType.getByName(tempdata2[0]), Integer.valueOf(tempdata2[1]) * 20, Integer.valueOf(tempdata2[2]));
                            if(tempdata1[0].equalsIgnoreCase("type1")) {
                                utils.giveTeamEffect(p, Double.valueOf(tempdata2[3]), po, true);
                            } else if(tempdata1[0].equalsIgnoreCase("type2")) {
                                utils.giveTeamEffect(p, Double.valueOf(tempdata2[3]), po, false);
                            }

                        }
                    }
                }
                this.cancel();
            }
        }.runTaskAsynchronously(main.getInstance());





    }
}
