package com.itndev.teams;

import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class jedis {
    public static HashMap<String, String> RedisUpdateQ = new HashMap<>();
    public static HashMap<String, String> RedisChatSyncQ = new HashMap<>();

    public static Integer c = 0;






    @Deprecated
    public static void jedisTest() {
        Jedis jedis = new Jedis(main.getInstance().dbaddress, 6614, 30000);
        jedis.auth("54rg46ujhy7ju57wujndt35ytgryeutwefer4rt34rd34rsfg6hdf43truhgfwgr348yfgcs");
        jedis.close();

        JedisPoolConfig poolconfig = new JedisPoolConfig();
        poolconfig.setMaxIdle(8);
        poolconfig.setMaxTotal(32);
        JedisPool jedispool = new JedisPool(poolconfig, main.getInstance().dbaddress, 6614, 5000, "54rg46ujhy7ju57wujndt35ytgryeutwefer4rt34rd34rsfg6hdf43truhgfwgr348yfgcs");





        new BukkitRunnable() {



            /*HashMap<String, String> commandlist1 = new HashMap<>();
            HashMap<String, String> commandlist2 = new HashMap<>();
            HashMap<String, String> commandlist3 = new HashMap<>();
            HashMap<String, String> commandlist4 = new HashMap<>();

            HashMap<String, String> chatmsg1 = new HashMap<>();
            HashMap<String, String> chatmsg2 = new HashMap<>();
            HashMap<String, String> chatmsg3 = new HashMap<>();
            HashMap<String, String> chatmsg4 = new HashMap<>();*/


            @Override
            public void run(){


                    //Pipeline pipeline = jedis.pipelined();



                    HashMap<String, String> commandlist1 = new HashMap<>();
                    HashMap<String, String> commandlist2 = new HashMap<>();
                    HashMap<String, String> commandlist3 = new HashMap<>();
                    HashMap<String, String> commandlist4 = new HashMap<>();

                    HashMap<String, String> chatmsg1 = new HashMap<>();
                    HashMap<String, String> chatmsg2 = new HashMap<>();
                    HashMap<String, String> chatmsg3 = new HashMap<>();
                    HashMap<String, String> chatmsg4 = new HashMap<>();
                    try(Jedis jedis1 = jedispool.getResource()) {
                        if(jedis1.isConnected()) {
                            commandlist1 = (HashMap<String, String>) jedis1.hgetAll(String.valueOf("client1").toString() + "");
                            commandlist2 = (HashMap<String, String>) jedis1.hgetAll(String.valueOf("client2").toString() + "");
                            commandlist3 = (HashMap<String, String>) jedis1.hgetAll(String.valueOf("client3").toString() + "");
                            commandlist4 = (HashMap<String, String>) jedis1.hgetAll(String.valueOf("client4").toString() + "");

                            chatmsg1 = (HashMap<String, String>) jedis1.hgetAll("client1chatmsg".toString());
                            chatmsg2 = (HashMap<String, String>) jedis1.hgetAll("client2chatmsg".toString());
                            chatmsg3 = (HashMap<String, String>) jedis1.hgetAll("client3chatmsg".toString());
                            chatmsg4 = (HashMap<String, String>) jedis1.hgetAll("client4chatmsg".toString());

                            if (!RedisUpdateQ.isEmpty()) {
                                jedis1.del(String.valueOf(main.getInstance().clientname).toString() + "");
                                jedis1.hmset(String.valueOf(main.getInstance().clientname).toString() + "", RedisUpdateQ);
                                //jedis.expire(String.valueOf(main.getInstance().clientname) + "", 1);
                                jedis1.expireAt(String.valueOf(main.getInstance().clientname.toString()).toString(), System.currentTimeMillis() + 200);
                                RedisUpdateQ.clear();
                            } else {
                                jedis1.del(String.valueOf(main.getInstance().clientname) + "");
                                RedisUpdateQ.clear();
                            }
                            if(!RedisChatSyncQ.isEmpty()) {
                                jedis1.del(String.valueOf(main.getInstance().clientname + "chatmsg").toString());
                                //jedis.
                                jedis1.hmset(String.valueOf(main.getInstance().clientname + "chatmsg").toString(), RedisChatSyncQ);
                                jedis1.expireAt(String.valueOf(main.getInstance().clientname + "chatmsg").toString(), System.currentTimeMillis() + 200);
                                RedisChatSyncQ.clear();
                            } else {
                                jedis1.del(String.valueOf(main.getInstance().clientname + "chatmsg").toString());
                            }


                        } else {

                            System.out.println("Not connected to any Redis Server!!!");
                        }
                    }

                    /*if(jedis.hgetAll(String.valueOf("client1").toString() + "".toString()) instanceof HashMap) {
                        commandlist1 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client1").toString() + "");
                    }
                    if(jedis.hgetAll(String.valueOf("client2").toString() + "".toString()) instanceof HashMap) {
                        commandlist2 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client2").toString() + "".toString());
                    }
                    if(jedis.hgetAll(String.valueOf("client3").toString() + "".toString()) instanceof HashMap) {
                        commandlist3 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client3").toString() + "".toString());
                    }
                    if(jedis.hgetAll(String.valueOf("client4").toString() + "".toString()) instanceof HashMap) {
                        commandlist4 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client4").toString() + "".toString());
                    }*/


                    //commandlist2 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client2"));
                    //commandlist3 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client3"));
                    //commandlist4 = (HashMap<String, String>) jedis.hgetAll(String.valueOf("client4"));

                    /*chatmsg1 = (HashMap<String, String>) jedis.hgetAll("client1chatmsg".toString());
                    chatmsg2 = (HashMap<String, String>) jedis.hgetAll("client2chatmsg".toString());
                    chatmsg3 = (HashMap<String, String>) jedis.hgetAll("client3chatmsg".toString());
                    chatmsg4 = (HashMap<String, String>) jedis.hgetAll("client4chatmsg".toString());*/

                    /*try {
                        jedis.del(String.valueOf(main.getInstance().clientname));
                        jedis.del(String.valueOf(main.getInstance().clientname + "chatmsg"));
                    } catch (Throwable e) {
                        System.out.println(e.getMessage());

                    }*/





                    //HashMap update
                    if (!commandlist1.isEmpty()) {
                        for (String longtime : commandlist1.keySet()) {
                            updatehashmap(longtime, longtime);
                        }
                    }
                    commandlist1.clear();
                    if (!commandlist2.isEmpty()) {
                        for (String longtime : commandlist2.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist2.remove(longtime);
                        }
                    }
                    commandlist2.clear();
                    if (!commandlist3.isEmpty()) {
                        for (String longtime : commandlist3.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist3.remove(longtime);
                        }
                    }
                    commandlist3.clear();
                    if (!commandlist4.isEmpty()) {
                        for (String longtime : commandlist4.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist4.remove(longtime);
                        }
                    }
                    commandlist4.clear();




                    //Chat
                    if (!chatmsg1.isEmpty()) {
                        for (String longtime : chatmsg1.keySet()) {
                            updatehashmap(longtime, longtime);
                        }
                    }
                    chatmsg1.clear();
                    if (!chatmsg2.isEmpty()) {
                        for (String longtime : chatmsg2.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist2.remove(longtime);
                        }
                    }
                    chatmsg2.clear();
                    if (!chatmsg3.isEmpty()) {
                        for (String longtime : chatmsg3.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist3.remove(longtime);
                        }
                    }
                    chatmsg3.clear();
                    if (!chatmsg4.isEmpty()) {
                        for (String longtime : chatmsg4.keySet()) {
                            updatehashmap(longtime, longtime);
                            //commandlist4.remove(longtime);
                        }
                    }
                    chatmsg4.clear();


                    //jedis.hmset(main.clientname, RedisUpdateQ);
                    /*if (!RedisUpdateQ.isEmpty()) {
                        jedis.del(String.valueOf(main.getInstance().clientname).toString() + "");
                        jedis.hmset(String.valueOf(main.getInstance().clientname).toString() + "", RedisUpdateQ);
                        //jedis.expire(String.valueOf(main.getInstance().clientname) + "", 1);
                        jedis.expire(String.valueOf(main.getInstance().clientname.toString()).toString(), 1);
                        RedisUpdateQ.clear();
                    } else {
                        jedis.del(String.valueOf(main.getInstance().clientname) + "");
                        RedisUpdateQ.clear();
                    }
                    if(!RedisChatSyncQ.isEmpty()) {
                        jedis.del(String.valueOf(main.getInstance().clientname + "chatmsg").toString());
                        //jedis.
                        jedis.hmset(String.valueOf(main.getInstance().clientname + "chatmsg").toString(), RedisChatSyncQ);
                        jedis.expire(String.valueOf(main.getInstance().clientname + "chatmsg").toString(), 1);
                        RedisChatSyncQ.clear();
                    } else {
                        jedis.del(String.valueOf(main.getInstance().clientname + "chatmsg").toString());
                    }*/
                    /*try {
                        jedis.del(main.getInstance().clientname);
                        jedis.del(main.getInstance().clientname + "chatmsg");
                    } catch (Throwable e) {
                        System.out.println(e.getMessage());

                    }*/




            }

        }.runTaskTimerAsynchronously(main.getInstance(), 1L, 1L);//runTaskTimer(main.getInstance(), 5L, 5L);




    }

    ArrayList d = new ArrayList();
    public static void savejedis(Jedis jedis) {
        Pipeline pipeline = jedis.pipelined();
        Map<String, String> stringedteammember = new HashMap<>();
        //stringedteammember.put(list2string(storage.teammember));
        pipeline.hmset("teammember", stringedteammember);


        pipeline.sync();

    }

    public static ArrayList string2list(String k) {
        if(k.contains("-")) {
            String[] parts = k.split("-");
            ArrayList<String> memlist = new ArrayList<>();
            for(String d : parts) {
                memlist.add(d);
            }
            return memlist;
        }
        return null;
    }
    public static String list2string(ArrayList<String> list) {
        String k = "";
        int i = 0;
        for(String c : list) {
            i = i + 1;
            if(list.size() >= i) {
                k = k + c;
            } else {
                k = k + c + "-";
            }


        }
        return k;
    }

















    public static void updatehashmap(String k, String time) {
        long c = 3000;
        //example command "update:=:hashmap1~4:=:add/remove:=:key:=:add/remove/(앞에 remove일 경우 여기랑 이 뒤는 쓸 필요 없다):=:value"
        if(c > 600) {
            String[] args = k.split(":=:");
            if(args[0].equalsIgnoreCase("update")) {
                if(args[1].equalsIgnoreCase("teampvp")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(storage.teampvp.containsKey(key)) {
                                storage.teampvp.remove(key);
                                storage.teampvp.put(key, value);
                            } else {
                                storage.teampvp.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(storage.teampvp.containsKey(key)) {

                                storage.teampvp.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(storage.teampvp.containsKey(key)) {
                            storage.teampvp.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("teamrank")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(storage.teamrank.containsKey(key)) {
                                storage.teamrank.remove(key);
                                storage.teamrank.put(key, value);
                            } else {
                                storage.teamrank.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(storage.teamrank.containsKey(key)) {

                                storage.teamrank.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(storage.teamrank.containsKey(key)) {
                            storage.teamrank.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("teams")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(storage.teams.containsKey(key)) {
                                storage.teams.remove(key);
                                storage.teams.put(key, value);
                            } else {
                                storage.teams.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(storage.teams.containsKey(key)) {

                                storage.teams.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(storage.teams.containsKey(key)) {
                            storage.teams.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("teammember")) {


                    //해쉬맵4는 값이 목록이다 조심하자
                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3];
                        String value = args[5];
                        if(args[4].equalsIgnoreCase("add")) {
                            if (!storage.teammember.isEmpty()) { //비어있지 않으면
                                if (storage.teammember.containsKey(key)) {

                                    //해당 키가 있으면
                                    ArrayList<String> updatelist = storage.teammember.get(key);
                                    if(!updatelist.contains(value)) {
                                        updatelist.add(value);
                                    }
                                    storage.teammember.put(key, updatelist);
                                } else {

                                    //해당 키가 없으면
                                    ArrayList<String> updatelist2 = new ArrayList<>();
                                    if(!updatelist2.contains(value)) {
                                        updatelist2.add(value);
                                    }
                                    storage.teammember.put(key, updatelist2);
                                }
                            } else { //만약 비어있으면
                                ArrayList<String> updatelist3 = new ArrayList<>();
                                updatelist3.add(value);
                                storage.teammember.put(key, updatelist3);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {
                            if (!storage.teammember.isEmpty()) { //비어있지 않으면
                                if (storage.teammember.containsKey(key)) {

                                    //해당 키가 있으면
                                    ArrayList<String> updatelist = storage.teammember.get(key);
                                    if(updatelist.contains(value)) {
                                        updatelist.remove(value);
                                    }
                                    storage.teammember.put(key, updatelist);
                                } else {

                                    //해당 키가 없으면
                                    //없으니까 하지 말자
                                }
                            } else { //만약 비어있으면
                                //시발 존재하지도 않는데 어떻게 없애냐 뭔시발 병신이냐
                            }
                        }
                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(storage.teammember.containsKey(key)) {
                            storage.teammember.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("inviteq")) {


                    //해쉬맵4는 값이 목록이다 조심하자
                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3];
                        String value = args[5];
                        if(args[4].equalsIgnoreCase("add")) {
                            if (!commands.inviteq.isEmpty()) { //비어있지 않으면
                                if (commands.inviteq.containsKey(key)) {

                                    //해당 키가 있으면
                                    ArrayList<String> updatelist = commands.inviteq.get(key);
                                    if(!updatelist.contains(value)) {
                                        updatelist.add(value);
                                    }
                                    commands.inviteq.put(key, updatelist);
                                } else {

                                    //해당 키가 없으면
                                    ArrayList<String> updatelist2 = new ArrayList<>();
                                    if(!updatelist2.contains(value)) {
                                        updatelist2.add(value);
                                    }
                                    commands.inviteq.put(key, updatelist2);
                                }
                            } else { //만약 비어있으면
                                ArrayList<String> updatelist3 = new ArrayList<>();
                                updatelist3.add(value);
                                commands.inviteq.put(key, updatelist3);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {
                            if (!commands.inviteq.isEmpty()) { //비어있지 않으면
                                if (commands.inviteq.containsKey(key)) {

                                    //해당 키가 있으면
                                    ArrayList<String> updatelist = commands.inviteq.get(key);
                                    if(updatelist.contains(value)) {
                                        updatelist.remove(value);
                                    }
                                    commands.inviteq.put(key, updatelist);
                                } else {

                                    //해당 키가 없으면
                                    //없으니까 하지 말자
                                }
                            } else { //만약 비어있으면
                                //시발 존재하지도 않는데 어떻게 없애냐 뭔시발 병신이냐
                            }
                        }
                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(storage.teammember.containsKey(key)) {
                            storage.teammember.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("namename")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(listener.namename.containsKey(key)) {
                                listener.namename.remove(key);
                                listener.namename.put(key, value);
                            } else {
                                listener.namename.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(listener.namename.containsKey(key)) {

                                listener.namename.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(listener.namename.containsKey(key)) {
                            listener.namename.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("uuidname")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(listener.uuidname.containsKey(key)) {
                                listener.uuidname.remove(key);
                                listener.uuidname.put(key, value);
                            } else {
                                listener.uuidname.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(listener.uuidname.containsKey(key)) {

                                listener.uuidname.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(listener.uuidname.containsKey(key)) {
                            listener.uuidname.remove(key);
                        }
                    }

                } else if(args[1].equalsIgnoreCase("nameuuid")) {

                    if(args[2].equalsIgnoreCase("add")) {
                        String key = args[3]; //키
                        String value = args[5]; //추가하고 싶은 값

                        if(args[4].equalsIgnoreCase("add")) {
                            if(listener.nameuuid.containsKey(key)) {
                                listener.nameuuid.remove(key);
                                listener.nameuuid.put(key, value);
                            } else {
                                listener.nameuuid.put(key, value);
                            }
                        } else if(args[4].equalsIgnoreCase("remove")) {

                            if(listener.nameuuid.containsKey(key)) {

                                listener.nameuuid.remove(key);

                                //할거 없다
                            } else {
                                //할거 없다
                            }
                        }

                    } else if(args[2].equalsIgnoreCase("remove")) {
                        String key = args[3];
                        if(listener.nameuuid.containsKey(key)) {
                            listener.nameuuid.remove(key);
                        }
                    }

                } else {
                    utils.broadcastwarn("data update command dropped... (Error) : No HashMap with the name " + args[1] + " found");
                }

            } else if(args[0].equalsIgnoreCase("ping")) {
                jedis.c = Math.toIntExact(c + 1L);
                if(c == 4) {
                    jedis.c = 0;
                    System.out.println("Pinged from Redis Database");
                }
            } else if(args[0].equalsIgnoreCase("chat")) {
                String playeruuid = args[1];
                String message = args[2];
                utils.teamchat(playeruuid, message);
            } else if(args[0].equalsIgnoreCase("notify")) {

                String playeruuid = args[1];
                String targetuuid = args[2];
                String message = args[3];
                String trueorfalse = args[4];
                utils.teamnotify(playeruuid, targetuuid, message, trueorfalse);
            } else {
                System.out.println("[WARNING (REDIS)] WRONG COMMAND USAGE FROM REDIS" + " (" + k + ")");
            }


        } else if(c <= 600) {
            String warn = "data update failed... trying to update data that should already been processed or update has been duplicated / processed delayed (" + String.valueOf(c) + ")";
            utils.broadcastwarn(warn);
        }

    }



}