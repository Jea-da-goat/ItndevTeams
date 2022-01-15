package com.itndev.teams;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class commands implements CommandExecutor {

    public static HashMap<String, Long> create = new HashMap<>();
    public static HashMap<String, Long> all = new HashMap<>();
    public static HashMap<String, ArrayList<String>> inviteq = new HashMap<>();
    public static ConcurrentHashMap<Player, String> teamchat = new ConcurrentHashMap<>();
    public static HashMap<String, String> bannedteamname = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equalsIgnoreCase("팀")) {
            if(main.getInstance().teamtoggle != true) {
                Player p = (Player) sender;
                //팀명령어
                String uuid = listener.name2uuid(p.getName());
                if (listener.nameuuid.isEmpty()) {
                    p.sendMessage("empty...");
                }
                //
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("생성")) {
                        if (create.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) create.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        create.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        if (storage.teamrank.get(p.getUniqueId().toString()).equals("nomad")) {
                            double money = main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId()));
                            if (money >= 50000) {

                                if ((args[1].toString().length() >= 3 || p.hasPermission("itndevteams.donation.charlimit")) && args[1].toString().length() <= 10 && !args[1].contains(":=:") && !args[1].contains("&")) {

                                    if (!bannedteamname.keySet().stream().anyMatch(key -> args[1].toLowerCase(Locale.ROOT).contains(key))) {

                                        if (!storage.teams.containsKey(args[1].toLowerCase(Locale.ROOT)) || p.hasPermission("itndevteams.bypass.bannedteamname")) {
                                            String uuidofleader = p.getUniqueId().toString();
                                            //지옥이 시작된다 새끼야
                                            //storage.teampvp.put(uuidofleader, args[1].toLowerCase(Locale.ROOT));
                                            jedis.RedisUpdateQ.put("update:=:teampvp:=:add:=:" + uuidofleader + ":=:add:=:" + args[1].toLowerCase(Locale.ROOT), "update:=:teampvp:=:add:=:" + uuidofleader + ":=:add:=:" + args[1].toLowerCase(Locale.ROOT));


                                            //storage.teams.put(args[1].toLowerCase(Locale.ROOT), args[1]);
                                            jedis.RedisUpdateQ.put("update:=:teams:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + args[1], "update:=:teams:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + args[1]);


                                            //storage.teamrank.put(uuidofleader, "leader");
                                            jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuidofleader + ":=:add:=:" + "leader", "update:=:teamrank:=:add:=:" + uuidofleader + ":=:add:=:" + "leader");


                                            //ArrayList<String> members = new ArrayList<>();
                                            //members.add(uuidofleader);
                                            //storage.teammember.put(args[1].toLowerCase(Locale.ROOT), members);
                                            jedis.RedisUpdateQ.put("update:=:teammember:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + uuidofleader, "update:=:teammember:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + uuidofleader);


                                            //시발 여기다

                                            main.econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), 50000);

                                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 팀 " + args[1] + "을 50000원을 지불하고 생성했습니다");
                                            //jedis.RedisChatSyncQ.put("notify:=:" + uuidofleader + ":=:" + uuidofleader + ":=:" + "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 팀 " + args[1] + "을 300원을 지불하고 생성했습니다" + ":=:" + "true", "notify:=:" + uuidofleader + ":=:" + uuidofleader + ":=:" + "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 팀 " + args[1] + "을 300원을 지불하고 생성했습니다" + ":=:" + "true");


                                        } else {
                                            main.sendmsg(p, lang.alreadyname);
                                        }
                                    } else {
                                        main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 팀이름은 금지되어 있거나 부적절한 단어가 들어가 있습니다");
                                    }
                                } else {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f팀 이름은 반드시 3~10자 사이이며 &와 같은 특수문자가 들어가서는 안됩니다. 후원하시면 3글자보다 짧은 이름을 지정하실수 있습니다");
                                }
                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f돈이 부족합니다. 적어도 50000원보다는 많아야 팀을 생성하실수 있습니다");
                            }
                            //작업중
                        } else {
                            main.sendmsg(p, lang.createalreadyteam);
                        }
                    } else if (args[0].equalsIgnoreCase("초대")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&7초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        if (storage.teampvp.containsKey(uuid)) {
                            if (storage.teamrank.get(uuid).equals("leader") || storage.teamrank.get(uuid).equals("coleader")) {
                                if (!storage.teampvp.containsKey(listener.name2uuid(args[1].toLowerCase(Locale.ROOT)))) {
                                    if (listener.namename.containsKey(args[1].toLowerCase(Locale.ROOT))) {
                                        if (storage.teammember.get(storage.teampvp.get(uuid)).size() < 50) {
                                            if (inviteq.containsKey(args[1].toLowerCase(Locale.ROOT))) {
                                                //ArrayList<String> invite = inviteq.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT)));
                                                //invite.add(storage.teampvp.get(uuid));

                                                //inviteq.put(invited.getUniqueId().toString(), invite);
                                                String inviteduuid = listener.name2uuid(args[1].toLowerCase(Locale.ROOT));
                                                jedis.RedisUpdateQ.put("update:=:inviteq:=:add:=:" + inviteduuid + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:inviteq:=:add:=:" + inviteduuid + ":=:add:=:" + storage.teampvp.get(uuid));


                                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "에게 초대장을 보냈습니다");


                                                //main.sendmsg(invited, "&a&o&l[ &r&f팀 &a&o&l] &r&f" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + "");
                                                jedis.RedisChatSyncQ.put("notify:=:" + p.getUniqueId().toString() + ":=:" + inviteduuid + ":=:" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + ":=:" + "false", "notify:=:" + p.getUniqueId().toString() + ":=:" + inviteduuid + ":=:" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + ":=:" + "false");
                                            } else {
                                                String inviteduuid = listener.name2uuid(args[1].toLowerCase(Locale.ROOT));
                                                jedis.RedisUpdateQ.put("update:=:inviteq:=:add:=:" + inviteduuid + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:inviteq:=:add:=:" + inviteduuid + ":=:add:=:" + storage.teampvp.get(uuid));


                                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "에게 초대장을 보냈습니다");


                                                //main.sendmsg(invited, "&a&o&l[ &r&f팀 &a&o&l] &r&f" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + "");
                                                jedis.RedisChatSyncQ.put("notify:=:" + p.getUniqueId().toString() + ":=:" + inviteduuid + ":=:" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + ":=:" + "false", "notify:=:" + p.getUniqueId().toString() + ":=:" + inviteduuid + ":=:" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + ":=:" + "false");
                                            /*ArrayList<String> invite = new ArrayList<>();
                                            invite.add(storage.teampvp.get(uuid));

                                            inviteq.put(invited.getUniqueId().toString(), invite);
                                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + invited.getName() + "에게 초대장을 보냈습니다");
                                            main.sendmsg(invited, "&a&o&l[ &r&f팀 &a&o&l] &r&f" + storage.teams.get(storage.teampvp.get(uuid)) + "에서 당신을 팀으로 초대했습니다. /팀 수락 " + storage.teams.get(storage.teampvp.get(uuid)) + "");*/
                                            }

                                        } else {
                                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f정원초과! 초대를 하시려면 팀 내의 다른 사람을 추방해야 합니다");
                                        }
                                    } else {
                                        main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 서버에 접속한적이 없습니다.");
                                    }


                                } else {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 이미 다른 팀에 소속되어 있습니다");
                                }

                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더나 부리더만 초대를 할수 있습니다");
                            }

                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f소속된 팀이 없습니다");
                        }

                    } else if(args[0].equalsIgnoreCase("초대취소")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        String name = args[1];
                        if(listener.nameuuid.containsKey(name)) {
                            String sendeduuid = listener.nameuuid.get(name);
                            String teamname = storage.teampvp.get(p.getUniqueId().toString());
                            if(inviteq.containsKey(sendeduuid) && inviteq.get(sendeduuid).contains(storage.teampvp.get(p.getUniqueId().toString()))) {
                                jedis.RedisUpdateQ.put("update:=:inviteq:=:add:=:" + sendeduuid + ":=:remove:=:" + teamname, "update:=:inviteq:=:add:=:" + sendeduuid + ":=:remove:=:" + teamname);
                                utils.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저 &c" + name + "&r&f 이에게 보낸 초대장을 취소했습니다");
                                jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + sendeduuid + ":=:" + storage.teams.get(teamname) + "에서 보낸 초대장이 취소되었습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + sendeduuid + ":=:" + storage.teams.get(teamname) + "에서 보낸 초대장이 취소되었습니다" + ":=:" + "false");
                            } else {
                                utils.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저 &c" + name + "&r&f 이에게 초대장을 보낸적이 없습니다");
                            }
                        } else {
                            utils.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저 &c" + name + "&r&f (은)는 서버에 접속한적이 없습니다");
                        }
                    } else if (args[0].equalsIgnoreCase("수락")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        if (inviteq.containsKey(uuid) && inviteq.get(uuid).contains(args[1].toLowerCase(Locale.ROOT))) {
                            if (inviteq.get(uuid).contains(args[1].toLowerCase(Locale.ROOT))) {
                                if (!storage.teampvp.containsKey(p.getUniqueId().toString())) {


                                    //inviteq.remove(uuid);
                                    jedis.RedisUpdateQ.put("update:=:inviteq:=:remove:=:" + uuid + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:inviteq:=:remove:=:" + uuid + ":=:add:=:" + storage.teampvp.get(uuid));


                                    //storage.teampvp.put(uuid, args[1].toLowerCase(Locale.ROOT));
                                    jedis.RedisUpdateQ.put("update:=:teampvp:=:add:=:" + uuid + ":=:add:=:" + args[1].toLowerCase(Locale.ROOT), "update:=:teampvp:=:add:=:" + uuid + ":=:add:=:" + args[1].toLowerCase(Locale.ROOT));


                                    //storage.teamrank.put(uuid, "member");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "member", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "member");


                                /*ArrayList<String> members = storage.teammember.get(args[1].toLowerCase(Locale.ROOT));
                                ArrayList<String> members1 = members;
                                members1.add(uuid);
                                storage.teammember.put(uuid, members1);*/
                                    jedis.RedisUpdateQ.put("update:=:teammember:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid, "update:=:teammember:=:add:=:" + args[1].toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid);


                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + storage.teams.get(args[1].toLowerCase(Locale.ROOT)) + "에 가입했습니다");
                                /*for(Player membersitis : Bukkit.getServer().getOnlinePlayers()) {
                                    if(members.contains(membersitis.getUniqueId().toString())) {
                                        main.sendmsg(membersitis, "&a&o&l[ &r&f팀 &a&o&l] &r&f" + p.getName() + "님이 당신의 팀에 가입했습니다");

                                    }
                                }*/

                                    jedis.RedisChatSyncQ.put("notify:=:" + p.getUniqueId().toString() + ":=:" + p.getUniqueId().toString() + ":=:" + p.getName() + "님이 당신의 팀에 가입했습니다" + ":=:" + "true", "notify:=:" + p.getUniqueId().toString() + ":=:" + p.getUniqueId().toString() + ":=:" + p.getName() + "님이 당신의 팀에 가입했습니다" + ":=:" + "true");

                                } else {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f이미 다른 팀에 들어가 있습니다.");
                                }
                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f이미 이 팀에 들어가 계십니다");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f이 팀으로부터 온 초대장이 없습니다");
                        }
                        //여기다가 참가명령어

                /*} else if(args[0].equalsIgnoreCase("리더이전")) {
                    if (all.containsKey(sender.getName())) {
                        int cooldownTime = 2;
                        long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                        if (secondsLeft > 0L) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l(!) &7해당 명령어는 &c" + secondsLeft + "&7초 후에 다시 사용 가능합니다"));
                            return true;
                        }
                    }
                    all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                    //여기다가 리더이전명령어
                    if(storage.teamrank.get(uuid).equals("leader")) {
                        if(storage.teampvp.get(uuid).equals(storage.teampvp.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))))) {
                            if(!storage.teamrank.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))).equals("coleader")) {

                                storage.teamrank.put(listener.name2uuid(args[1]), "leader");
                                main.sendmsg(p, "성공적으로 부리더로 지정했습니다");
                            } else {
                                main.sendmsg(p, "해당 유저는 이미 부리더입니다");
                            }
                        } else {
                            main.sendmsg(p, "해당 유저는 당신의 팀에 소속되어 있지 않습니다");
                        }
                    } else {
                        main.sendmsg(p, "리더만 부리더를 지정할수 있습니다");
                    }*/


                    } else if (args[0].equalsIgnoreCase("부리더지정")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 부리더지정명령어
                        if (storage.teamrank.get(uuid).equals("leader")) {
                            if (storage.teampvp.get(uuid).equals(storage.teampvp.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))))) {
                                if (!storage.teamrank.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))).equals("coleader")) {

                                    //storage.teamrank.put(listener.name2uuid(args[1]), "coleader");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + listener.name2uuid(args[1]) + ":=:add:=:" + "coleader", "update:=:teamrank:=:add:=:" + listener.name2uuid(args[1]) + ":=:add:=:" + "coleader");
                                    jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 멤버에서 부리더로 등급이 변경됬습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 멤버에서 부리더로 등급이 변경됬습니다" + ":=:" + "false");

                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 부리더로 지정했습니다");
                                } else {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 이미 부리더입니다");
                                }
                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 당신의 팀에 소속되어 있지 않습니다");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더만 부리더를 지정할수 있습니다");
                        }


                    } else if (args[0].equalsIgnoreCase("부리더해제")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 부리더해제 명령어
                        if (storage.teamrank.get(uuid).equals("leader")) {
                            if (storage.teampvp.get(uuid).equals(storage.teampvp.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))))) {
                                if (storage.teamrank.get(listener.name2uuid(args[1].toLowerCase(Locale.ROOT))).equals("coleader")) {

                                    //storage.teamrank.put(listener.name2uuid(args[1].toLowerCase(Locale.ROOT)), "member");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:add:=:" + "member", "update:=:teamrank:=:add:=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:add:=:" + "member");
                                    jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 부리더에서 멤버로 등급이 변경됬습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 부리더에서 멤버로 등급이 변경됬습니다" + ":=:" + "false");

                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 부리더에서 해제했습니다");
                                } else {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 부리더가 아닙니다");
                                }
                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 유저는 당신의 팀에 소속되어 있지 않습니다");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더만 부리더를 해제할수 있습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("추방")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        String k = storage.teamrank.get(uuid);
                        String uuid2 = listener.name2uuid(args[1].toLowerCase(Locale.ROOT));
                        if (k.equals("nomad")) {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f속한 팀이 없습니다");
                        } else if (storage.teampvp.get(uuid).equals(storage.teampvp.get(uuid2))) {
                            if (k.equals("member")) {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f당신은 권한이 없습니다");
                            } else if (k.equals("coleader")) {

                                if (storage.teamrank.get(uuid2).equals("member")) {


                                    //storage.teamrank.put(uuid2, "nomad");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid2 + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid2 + ":=:add:=:" + "nomad");

                                    //storage.teampvp.remove(uuid2);
                                    jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + uuid2 + ":=:add:=:" + "nomad", "update:=:teamrank:=:remove:=:" + uuid2 + ":=:add:=:" + "nomad");


                                    //ArrayList<String> list = storage.teammember.get(storage.teampvp.get(uuid2));
                                    //list.remove(uuid2);
                                    //storage.teammember.put(storage.teampvp.get(uuid2), list);
                                    jedis.RedisUpdateQ.put("update:=:teammember:=:add:=:" + storage.teampvp.get(uuid2) + ":=:remove:=:" + uuid2, "update:=:teammember:=:add:=:" + storage.teampvp.get(uuid2) + ":=:remove:=:" + uuid2);


                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "을 팀에서 추방했습니다");
                                    jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "이가 당신의 팀에서 추방됬습니다" + ":=:" + "true", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "이가 당신의 팀에서 추방됬습니다" + ":=:" + "true");
                                    jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 팀 &c" + storage.teams.get(storage.teampvp.get(uuid2)) + " &f에서 추방됬습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 팀 &c" + storage.teams.get(storage.teampvp.get(uuid2)) + " &f에서 추방됬습니다" + ":=:" + "false");


                                } else if (storage.teamrank.get(uuid2).equals("coleader") && storage.teamrank.get(uuid2).equals("leader")) {
                                    main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f자기랑 등급이 같거나 높은 사람을 추방할수 없습니다");

                                }
                            } else if (k.equals("leader")) {

                                //storage.teamrank.put(uuid2, "nomad");
                                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid2 + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid2 + ":=:add:=:" + "nomad");

                                //storage.teampvp.remove(uuid2);
                                jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + uuid2 + ":=:add:=:" + "nomad", "update:=:teampvp:=:remove:=:" + uuid2 + ":=:add:=:" + "nomad");

                                jedis.RedisUpdateQ.put("update:=:teammember:=:add:=:" + storage.teampvp.get(uuid2) + ":=:remove:=:" + uuid2, "update:=:teammember:=:add:=:" + storage.teampvp.get(uuid2) + ":=:remove:=:" + uuid2);


                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 " + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "을 팀에서 추방했습니다");
                                jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "이가 당신의 팀에서 추방됬습니다" + ":=:" + "true", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + listener.name2name(args[1].toLowerCase(Locale.ROOT)) + "이가 당신의 팀에서 추방됬습니다" + ":=:" + "true");
                                jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 팀 &c" + storage.teams.get(storage.teampvp.get(uuid2)) + " &f에서 추방됬습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + listener.name2uuid(args[1].toLowerCase(Locale.ROOT)) + ":=:" + "당신은 팀 &c" + storage.teams.get(storage.teampvp.get(uuid2)) + " &f에서 추방됬습니다" + ":=:" + "false");
                            }
                        }

                        //여기다가 추방명령어

                    } else if (args[0].equalsIgnoreCase("정보")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 정보명령어
                        ArrayList<String> list = storage.teammember.get(args[1].toLowerCase(Locale.ROOT));
                        String memlist = "";
                        String leadermem = "";

                        if (storage.teams.containsKey(args[1].toLowerCase(Locale.ROOT))) {
                            for (String uuids : list) {
                                if (storage.getrank2(uuids).equals("leader")) {
                                    leadermem = listener.name2name(listener.uuid2name(uuids));
                                } else {
                                    memlist = memlist + listener.name2name(listener.uuid2name(uuids)) + ", ";
                                }
                            }
                            main.sendmsg(p, "&m&l----------&a&l[&f" + storage.teams.get(args[1].toLowerCase(Locale.ROOT)) + "&a&l]&f&m&l----------\n" +
                                    "&a&l리더\n" +
                                    "&f" + leadermem + "\n" +
                                    " \n" +
                                    "&f&l멤버\n" +
                                    "&f" + memlist + "\n" +
                                    " \n" +
                                    "&m&l----------&a&l[&f" + storage.teams.get(args[1].toLowerCase(Locale.ROOT)) + "&a&l]&f&m&l----------\n");
                        } else {
                            main.sendmsg(p, "&a&l&o[ &f팀 &a&l&o] &r&f해당 팀은 존재하지 않습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("소속")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        String name = args[1].toLowerCase(Locale.ROOT);

                        if (listener.nameuuid.containsKey(name)) {
                            if(storage.teams.get(storage.teampvp.get(listener.name2uuid(name))) != null) {
                                main.sendmsg(p, "&a&l&o[ &f팀 &a&l&o] &r&f" + listener.name2name(name) + "의 소속팀 : " + storage.teams.get(storage.teampvp.get(listener.name2uuid(name))));
                            } else {
                                main.sendmsg(p, "&a&l&o[ &f팀 &a&l&o] &r&f" + listener.name2name(name) + "는 소속된 팀이 없습니다 &7(무소속)");
                            }
                        } else {
                            main.sendmsg(p, "&a&l&o[ &f팀 &a&l&o] &r&f해당 유저는 서버에 접속한적이 없습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("이름")) { // 응 아니야 안써
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 이름명령어

                    } else if (args[0].equalsIgnoreCase("teamname")) {
                        if (p.hasPermission("itndevteams.admin")) {
                            main.sendmsg(p, storage.getformattedteamname(p) + "/" + storage.getteamname(p));
                        }
                    }
                } else if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("나가기")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        if (storage.teampvp.containsKey(uuid)) {
                            if (!storage.teamrank.get(uuid).equals("leader")) {
                                String oldteamname = storage.teampvp.get(uuid).toString();
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 팀 " + storage.teams.get(oldteamname) + "에서 나갔습니다");
                                jedis.RedisChatSyncQ.put("notify:=:" + oldteamname.toLowerCase() + ":=:" + "TeamChat" + ":=:" + p.getName() + "이가 당신의 팀을 떠났습니다" + ":=:" + "true", "notify:=:" + uuid + ":=:" + "SIBAL" + ":=:" + p.getName() + "이가 당신의 팀을 떠났습니다" + ":=:" + "true");
                                //storage.teamrank.put(uuid, "nomad");
                                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad");



                                //storage.teampvp.remove(uuid);
                                jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teampvp:=:remove:=:" + uuid + ":=:add:=:" + "nomad");
                                jedis.RedisUpdateQ.put("update:=:teammember:=:add:=:" + oldteamname + ":=:remove:=:" + uuid, "update:=:teammember:=:add:=:" + oldteamname + ":=:remove:=:" + uuid);





                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더 역할을 딴 멤버에게 넘기거나 /팀 해체 로 팀을 해체하세요");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f소속된 팀이 없습니다");
                        }
                        //여기다가 나가기명령어
                    } else if (args[0].equalsIgnoreCase("채팅")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 채팅명령
                        if (!storage.getrank(p).equals("nomad")) {
                            if (!teamchat.containsKey(p)) {
                                teamchat.put(p, "true");
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f팀 채팅에 입장했습니다");
                            } else {
                                teamchat.remove(p);
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f팀 채팅에서 퇴장했습니다");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f소속된 팀이 없습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("해체")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 해체명령어
                        if (storage.teamrank.get(uuid).equals("leader")) {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f진짜로 팀을 해체하시겠습니까? /팀 해체수락 을 쳐서 팀을 해체하실수 있습니다");
                            storage.disbandq.put(storage.teampvp.get(uuid), "yes");


                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더만 팀을 해체할수 있습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("해체수락")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        if (storage.teamrank.get(uuid).equals("leader")) {
                            String k = storage.teampvp.get(uuid);
                            if (storage.disbandq.containsKey(k)) {
                                ArrayList<String> teamembers = storage.teammember.get(k);
                                jedis.RedisChatSyncQ.put("notify:=:" + "SIBAL" + ":=:" + p.getUniqueId().toString() + ":=:" + "팀이 해체되었습니다" + ":=:" + "true", "notify:=:" + "SIBAL" + ":=:" + p.getUniqueId().toString() + ":=:" + "팀이 해체되었습니다" + ":=:" + "true");

                                for (String mem : teamembers) {
                                    //storage.teampvp.remove(mem);
                                    jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + mem + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:teampvp:=:remove:=:" + mem + ":=:add:=:" + storage.teampvp.get(uuid));

                                    jedis.RedisChatSyncQ.put("notify:=:" + uuid + ":=:" + mem + ":=:" + "당신의 팀이 해체되었습니다" + ":=:" + "false", "notify:=:" + uuid + ":=:" + mem + ":=:" + "당신의 팀이 해체되었습니다" + ":=:" + "false");

                                    //storage.teamrank.put(mem, "nomad");
                                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + mem + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + mem + ":=:add:=:" + "nomad");
                                }
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f성공적으로 팀을 해체했습니다");


                                //sendmsgtoteam(k ,"&a&o&l[ &r&f팀 &a&o&l] &r&f팀이 해체되었습니다", uuid);


                                //storage.teams.remove(k);
                                jedis.RedisUpdateQ.put("update:=:teams:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:teams:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + storage.teampvp.get(uuid));

                                //storage.teammember.remove(k);
                                jedis.RedisUpdateQ.put("update:=:teammember:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + storage.teampvp.get(uuid), "update:=:teammember:=:remove:=:" + k.toLowerCase(Locale.ROOT) + ":=:add:=:" + storage.teampvp.get(uuid));

                                storage.disbandq.remove(k);

                            } else {
                                main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f/팀 해체 를 한 후 다시 명령어를 치셔야 해체가 가능합니다");
                            }
                        } else {
                            main.sendmsg(p, "&a&o&l[ &r&f팀 &a&o&l] &r&f리더만 팀을 해체할수 있습니다");
                        }

                    } else if (args[0].equalsIgnoreCase("도움말")) {
                        if (all.containsKey(sender.getName())) {
                            int cooldownTime = 2;
                            long secondsLeft = ((Long) all.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                            if (secondsLeft > 0L) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f해당 명령어는 &c" + secondsLeft + "&f초 후에 다시 사용 가능합니다"));
                                return true;
                            }
                        }
                        all.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                        //여기다가 도움말명령어
                        sendteamhelpmsg(p);
                    } else {
                        sendteamhelpmsg(p);
                    }

                } else {
                    sendteamhelpmsg(p);

                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f점검상의 이유로 팀 관련 명령어가 일시 비활성화 됬습니다. 5분만 기다려 주십시오"));

            }
        }  else if(label.equalsIgnoreCase("checkclientname") && sender.hasPermission("redistest.checkclientname")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7ClientName = " + main.getInstance().clientname));

        } else if(label.equalsIgnoreCase("updateredis") && sender.hasPermission("redistest.update")) {

            String key;
            String value;
            Long time = System.currentTimeMillis();
            if(args.length == 6) {
                key = String.valueOf(time);
                value = args[0] + ":=:" + args[1] + ":=:" + args[2] + ":=:" + args[3] + ":=:" + args[4] + ":=:" + args[5];
                jedis.RedisUpdateQ.put(key, value);
            } else if(args.length == 4) {
                key = String.valueOf(time);
                value = args[0] + ":=:" + args[1] + ":=:" + args[2] + ":=:" + args[3];
                jedis.RedisUpdateQ.put(key, value);
            }



        }
        return false;
    }
    public static void sendmsgtoteam(String teamname, String message, String exp) {
        for(Player players : Bukkit.getOnlinePlayers()) {
            if(storage.teammember.get(teamname).contains(players.getUniqueId().toString())) {
                if(players.getUniqueId().toString().equals(exp)) {
                    //do nothing
                } else {
                    main.sendmsg(players, message);
                }
            }
        }

    }
    public void sendteamhelpmsg(Player p) {
        main.sendmsg(p, "&r&f&m----------&a&l[&f팀 도움말&a&l]&f&m----------&r\n" +
                "&r/팀 생성 &7(팀이름) &8: &f팀을 생성한다 &7(팀 생성비용 50000원)\n" +
                "&r/팀 초대 &7(이름) &8: &f해당 유저를 팀에 초대한다 &7(2분후 만료)\n" +
                "&r/팀 초대취소 &7(이름) &8: &f해당 유저에게 보낸 초대장을 취소한다\n" +
                "&r/팀 수락 &7(팀이름) &8: &f해당 팀이 보낸 초대장을 수락한다\n" +
                "&r/팀 추방 &7(이름) &8: &f해당 유저를 팀에서 추방한다\n" +
                "&r/팀 나가기 &8: &f팀에서 나간다\n" +
                "&r/팀 부리더지정 &7(이름) &8: &f해당 유저를 부리더로 승급시킨다\n" +
                "&r/팀 부리더해제 &7(이름) &8: &f해당 유저를 부리더에서 해임한다\n" +
                "&r/팀 정보 &7(팀이름) &8: &f해당 팀의 정보를 보여준다\n" +
                "&r/팀 소속 &7(이름) &8: &f해당 유저의 팀 소속을 보여준다\n" +
                "&r/팀 채팅 &8: &f팀 채팅을 키거나 끈다\n" +
                "&r/팀 해체 &8: &f팀을 해체한다.\n" +
                "&r&f&m----------&a&l[&f팀 도움말&a&l]&f&m----------&r" +
                "");

    }
}
