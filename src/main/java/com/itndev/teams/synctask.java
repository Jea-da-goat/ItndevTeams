package com.itndev.teams;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.HashMap;

public class synctask implements CommandExecutor {

    public static HashMap<String, String> tempteammembers = new HashMap<>();

    @Deprecated
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(label.equalsIgnoreCase("syncteams")) {
            if(sender.hasPermission("itndevteams.sync")) {
                Player p = (Player) sender;
                if(args.length == 2) {
                    if (args[0].equalsIgnoreCase("upload")) {
                        Jedis jedissync = new Jedis(main.getInstance().dbaddress, 6614, 30000);
                        jedissync.auth("54rg46ujhy7ju57wujndt35ytgryeutwefer4rt34rd34rsfg6hdf43truhgfwgr348yfgcs");
                        String id = args[1];
                        Pipeline pipeline = jedissync.pipelined();
                        pipeline.hmset(id + ":teampvp", storage.teampvp);
                        pipeline.hmset(id + ":teams", storage.teams);
                        pipeline.hmset(id + ":teamrank", storage.teamrank);

                        for (String key : storage.teammember.keySet()) {
                            String v = Array2String(storage.teammember.get(key));
                            tempteammembers.put(key, v);
                        }
                        pipeline.hmset(id + ":teammember", tempteammembers);
                        tempteammembers.clear();

                        pipeline.hmset(id + ":namename", listener.namename);
                        pipeline.hmset(id + ":nameuuid", listener.nameuuid);
                        pipeline.hmset(id + ":uuidname", listener.uuidname);

                        pipeline.expire(id + ":teampvp", 60);
                        pipeline.expire(id + ":teams", 60);
                        pipeline.expire(id + ":teamrank", 60);
                        pipeline.expire(id + ":teammember", 60);
                        pipeline.expire(id + ":namename", 60);
                        pipeline.expire(id + ":nameuuid", 60);
                        pipeline.expire(id + ":uuidname", 60);
                        pipeline.sync();
                        jedissync.close();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l서버 싱크를 위해 id : " + id + " 로 싱크 데이터를 업로드했습니다. 60초후 만료됩니다"));


                    } else if (args[0].equalsIgnoreCase("download")) {

                        Jedis jedissync = new Jedis(main.getInstance().dbaddress, 6614, 30000);
                        jedissync.auth("54rg46ujhy7ju57wujndt35ytgryeutwefer4rt34rd34rsfg6hdf43truhgfwgr348yfgcs");
                        String id = args[1];
                        //Pipeline pipeline = jedissync.pipelined();
                        HashMap<String, String> teamrank = new HashMap<>();
                        HashMap<String, String> teampvp = new HashMap<>();
                        HashMap<String, String> teams = new HashMap<>();
                        HashMap<String, String> tempteammember = new HashMap<>();
                        HashMap<String, ArrayList<String>> teammember = new HashMap<>();

                        HashMap<String, String> namename = new HashMap<>();
                        HashMap<String, String> nameuuid = new HashMap<>();
                        HashMap<String, String> uuidname = new HashMap<>();
                        teamrank = (HashMap<String, String>) jedissync.hgetAll(id + ":teamrank");
                        teampvp = (HashMap<String, String>) jedissync.hgetAll(id + ":teampvp");
                        teams = (HashMap<String, String>) jedissync.hgetAll(id + ":teams");
                        tempteammember = (HashMap<String, String>) jedissync.hgetAll(id + ":teammember");
                        for (String keys : tempteammember.keySet()) {
                            ArrayList<String> memlist = String2Array(tempteammember.get(keys));
                            teammember.put(keys, memlist);
                        }

                        namename = (HashMap<String, String>) jedissync.hgetAll(id + ":namename");
                        nameuuid = (HashMap<String, String>) jedissync.hgetAll(id + ":nameuuid");
                        uuidname = (HashMap<String, String>) jedissync.hgetAll(id + "uuidname");

                        storage.teamrank.clear();
                        storage.teampvp.clear();
                        storage.teams.clear();
                        storage.teammember.clear();

                        listener.namename.clear();
                        listener.nameuuid.clear();
                        listener.uuidname.clear();

                        storage.teamrank = teamrank;
                        storage.teampvp = teampvp;
                        storage.teams = teams;
                        storage.teammember = teammember;

                        listener.namename = namename;
                        listener.nameuuid = nameuuid;
                        listener.uuidname = uuidname;
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[!] &f성공적으로 SYNC 테스크 완료"));


                        //storage.teamrank.clear();


                        //pipeline.sync();
                        jedissync.close();
                    }
                } else if(args[0].equalsIgnoreCase("teamtoggle")) {
                    if(main.getInstance().teamtoggle == false ) {
                        main.getInstance().teamtoggle = true;
                        utils.sendmsg(p, "&4&l[!] &f&l팀 명령어 일시 비활성화");
                    } else if(main.getInstance().teamtoggle == true){
                        main.getInstance().teamtoggle = false;
                        utils.sendmsg(p, "&4&l[!] &f&l팀 명령어 활성화 완료");
                    } else {
                        utils.sendmsg(p, "&c&lERROR &r&ferror code 301");
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l[!] &f&l사용법 : /syncteams (download/upload) ID"));
                }
            }
        } else if(label.equalsIgnoreCase("teamadmin")) {
            if(args[0].equalsIgnoreCase("save")) {
                main.getInstance().saveAllData();
                utils.sendmsg((Player) sender, "&c&lSUCESS &7saved data to localstorage ( flatfile )");
            }
        }

        return false;
    }

    public String Array2String(ArrayList<String> k) {
        String stringedarray = "";
        int round = 0;
        for(String v : k) {
            round = round + 1;
            if(round == k.size()) {
                stringedarray = stringedarray + v;
            } else {
                stringedarray = stringedarray + v + ":";
            }

        }
        return stringedarray;

    }
    public ArrayList<String> String2Array(String k) {
        if(k.contains(":")) {
            String[] parts = k.split(":");
            int length = parts.length;
            ArrayList<String> arrayedstring = new ArrayList<>();
            for(int V = 0; V < length; V++) {
                if(parts[V] != null) {
                    arrayedstring.add(parts[V]);

                }

            }
            return arrayedstring;
        }

        return null;
    }
}
