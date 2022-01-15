package com.itndev.teams;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class admincommands{

    public static void commandexecute(CommandSender sender, String label, String[] args) {
        if(label.equalsIgnoreCase("teamadmin")) {
            if(sender.hasPermission("teamadmin.force.bypass")) {

                Player p = (Player) sender;
                if (args.length == 0) {
                    utils.sendmsg(p, "&c&lCMD &r&7/teamadmin help");
                    return;
                }
                if(args[0].equalsIgnoreCase("help")) {

                } else if(args[0].equalsIgnoreCase("forcecreate")) {

                } else if(args[0].equalsIgnoreCase("forcedelete")) {
                    if(args.length <= 1) {
                        utils.sendmsg(p, "&c&lERROR &r&7명령어 사용법 : /teamadmin forcedelete &f(팀이름)");
                        return;
                    }

                    String k = args[1].toString().toLowerCase(Locale.ROOT);
                    ArrayList<String> teamembers = storage.teammember.get(k);
                    jedis.RedisChatSyncQ.put("notify:=:" + "SIBAL" + ":=:" + p.getUniqueId().toString() + ":=:" + "팀이 해체되었습니다" + ":=:" + "true", "notify:=:" + "SIBAL" + ":=:" + p.getUniqueId().toString() + ":=:" + "팀이 해체되었습니다" + ":=:" + "true");

                    for (String mem : teamembers) {
                        //storage.teampvp.remove(mem);
                        jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + mem + ":=:add:=:" + k, "update:=:teampvp:=:remove:=:" + mem + ":=:add:=:" + k);

                        jedis.RedisChatSyncQ.put("notify:=:" + p.getUniqueId().toString() + ":=:" + mem + ":=:" + "당신의 팀이 해체되었습니다" + ":=:" + "false", "notify:=:" + p.getUniqueId().toString() + ":=:" + mem + ":=:" + "당신의 팀이 해체되었습니다" + ":=:" + "false");

                        //storage.teamrank.put(mem, "nomad");
                        jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + mem + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + mem + ":=:add:=:" + "nomad");
                    }
                    main.sendmsg(p, "&a&o&l[ &r&f팀&c&l(ADMIN) &a&o&l] &r&f성공적으로 팀을 해체했습니다");


                    //sendmsgtoteam(k ,"&a&o&l[ &r&f팀 &a&o&l] &r&f팀이 해체되었습니다", uuid);


                    //storage.teams.remove(k);
                    jedis.RedisUpdateQ.put("update:=:teams:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + k, "update:=:teams:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + k);

                    //storage.teammember.remove(k);
                    jedis.RedisUpdateQ.put("update:=:teammember:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + k, "update:=:teammember:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + k);

                } else if(args[0].equalsIgnoreCase("forcekick")) {

                } else if(args[0].equalsIgnoreCase("forcechange")) {
                    if(args.length >= 4 && args[1].equalsIgnoreCase("leader")) {

                        String teamname = args[2];
                        String teamnewleader = args[3];
                        String teamnewleaderUUID = listener.nameuuid.get(args[3]);
                        ArrayList<String> teammembers = storage.teammember.get(teamname);
                        if(teammembers.contains(teamnewleaderUUID)) {
                            for(String memuuid : storage.teammember.get(teamname)) {
                                if(storage.teamrank.get(memuuid).equalsIgnoreCase("leader")) {
                                    //storage.teamrank.put(memuuid, "nomad");
                                    utils.sendmsg(p, "&c&lSUCESS &7성공적으로 팀리더를 뺐습니다");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + memuuid + ":=:add:=:" + "member", "update:=:teamrank:=:add:=:" + memuuid + ":=:add:=:" + "member");
                                }
                            }
                            utils.sendmsg(p, "&c&lSUCESS &7성공적으로 팀리더를 " + listener.namename.get(teamnewleader) + "(이)로 바꿨습니다");
                            jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + teamnewleaderUUID + ":=:add:=:" + "leader", "update:=:teamrank:=:add:=:" + teamnewleaderUUID + ":=:add:=:" + "leader");
                        } else {
                            utils.sendmsg(p, "&c&lERROR &7팀에 소속된 사람을 리더로 설정해야 합니다");
                        }
                    } else if(args.length >= 4 && args[1].equalsIgnoreCase("teamname")) {

                    }
                } else if(args[0].equalsIgnoreCase("seteffect")) {
                    if(args.length >= 3) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta itemmeta = item.getItemMeta();
                        NamespacedKey namekey = new NamespacedKey(main.getInstance(), "REMOVEAMOUNT");
                        itemmeta.getPersistentDataContainer().set(namekey, PersistentDataType.INTEGER, Integer.valueOf(args[1]));
                        NamespacedKey namekey2 = new NamespacedKey(main.getInstance(), "CLICKTOUSEITEM");
                        itemmeta.getPersistentDataContainer().set(namekey2, PersistentDataType.STRING, args[2]);
                        item.setItemMeta(itemmeta);
                        utils.sendmsg(p, "&c&lSUCESS &7성공적으로 설정완료 : " + args[1] + " / " + args[2]);
                    } else {
                        utils.sendmsg(p, "&c&lERROR &7사용법 : /faxskills seteffect (int) (String)");
                    }
                }



            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lERROR &r&7no permission"));

            }
        }
    }
}
