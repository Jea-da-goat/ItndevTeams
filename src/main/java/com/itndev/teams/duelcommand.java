package com.itndev.teams;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class duelcommand implements CommandExecutor {

    public static ConcurrentHashMap<Player, Player> duelq1 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Player, ArrayList<Player>> duelqtake1 = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Player, Boolean> isduelinghm = new ConcurrentHashMap<>();
    public static HashMap<Player, Player> dueler = new HashMap<>(); //상대방
    public static HashMap<String, Boolean> Arenausage = new HashMap<>();
    public static HashMap<Player, String> nameofArena = new HashMap<>();
    public static HashMap<String, ArrayList<Player>> Arenauser = new HashMap<>();
    public static HashMap<String, String> locations = new HashMap<>();
    public static HashMap<String, Boolean> cancelmovements = new HashMap<>();

    public static ConcurrentHashMap<Player, Player> pendingdueltag = new ConcurrentHashMap<>();

    public static HashMap<String, Long> duelcooldown = new HashMap<>();


    public static HashMap<Player, Integer> tournament = new HashMap<>();

    public static Boolean onGoingTournament = false;

    public static Boolean TournamentStarted = false;

    public static void AddToTournament(Player p) {
        tournament.put(p, 1);
    }

    public static void StartTournament(Player p1, Player p2) {

    }


    public static File arena;

    public static FileConfiguration arenastorage;

    public static void createarenastorage() {
        arena = new File(main.getInstance().getDataFolder(), "arena.yml");
        if(!arena.exists()) {
            try {
                arena.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        }
        arenastorage = YamlConfiguration.loadConfiguration(arena);
    }
    public static void resetlocalstorage() throws IOException {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        //arenastorage.set("locations.", null);
        arena.delete();
        arena.createNewFile();
        arenastorage.getKeys(false).forEach(key ->{
            arenastorage.set(key, null);
        });
        savearenaStorage();
        //customlocalstorage.set("teammember", null);
        //savearenaStorage();

    }


    public static void savearenaStorage() {

        try {
            arenastorage.save(arena);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static FileConfiguration getarenaStorage() {
        return arenastorage;
    }
    public static void reloadarenaStorage() {
        arenastorage = YamlConfiguration.loadConfiguration(arena);
    }


    public static void savearenalocations() {
        for (Map.Entry<String, String> entry : duelcommand.locations.entrySet())
            duelcommand.getarenaStorage().set("locations." + (String)entry.getKey(), entry.getValue());
        //savearenaStorage();
    }
    public static void restorearenalocations() {
        if(!duelcommand.arenastorage.contains("locations.")) {
            return;
        }
        duelcommand.arenastorage.getConfigurationSection("locations.").getKeys(false).forEach(key -> {
            String v = duelcommand.arenastorage.get("locations." + key).toString();
            duelcommand.locations.put(key, v);
        });
    }












    @Deprecated
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("대전")) {
            if(args.length == 2) {
                Player p = (Player) sender;
                if(args[0].equalsIgnoreCase("신청")) {
                    if (duelcooldown.containsKey(sender.getName())) {
                        int cooldownTime = 2;
                        long secondsLeft = ((Long) duelcooldown.get(sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                        if (secondsLeft > 0L) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l( &r&f대전 &c&l) &r&f해당 명령어는 &c" + secondsLeft + "&r&f초 후에 다시 사용 가능합니다"));
                            return true;
                        }
                    }
                    if(onGoingTournament) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l( &r&f대전 &c&l) &r&f현재 토너먼트가 진행중입니다"));
                        return true;
                    }
                    duelcooldown.put(sender.getName(), Long.valueOf(System.currentTimeMillis()));
                    Player duel = Bukkit.getPlayer(args[1]);
                    if(duel != null && duel.isOnline()) {
                        if(!isdueling(p) && !isdueling(duel)) {
                            Player k = null;
                            if(duelq1.containsKey(p)) {
                                k = duelq1.get(p);
                                duelqtake1.get(k).remove(p);
                            }
                            duelq1.put(p, duel);
                            if(duelqtake1.containsKey(duel)) {
                                ArrayList<Player> duelqlist = duelqtake1.get(duel);
                                duelqlist.add(p);
                                duelqtake1.put(duel, duelqlist);
                            } else {
                                ArrayList<Player> duelqlist = new ArrayList<>();
                                duelqlist.add(p);
                                duelqtake1.put(duel, duelqlist);
                            }
                            utils.sendmsg(p, "&4&l(!) &r&f성공적으로 &c" + duel.getName() + " &r&f에게 대전을 신청했습니다\n"
                                    + "&7 대전을 취소하려면 : /대전 취소 " + duel.getName() + "\n");
                            utils.sendmsg(duel, "&4&l(!) &c" + p.getName() + " &r&f이가 대전을 신청했습니다\n"
                                    + "&7 대전을 수락하려면 : /대전 수락 " + p.getName() + "\n");
                            TextComponent message = new TextComponent("      [클릭하여 대전수락]");
                            message.setBold(true);
                            message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/대전 수락 " + p.getName()));
                            duel.sendMessage(message);
                        } else {
                            utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 대전 중입니다");
                        }

                    } else {
                        utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 PVP서버에 접속해있지 않거나 오프라인입니다");
                    }












                } else if(args[0].equalsIgnoreCase("수락")){
                    Player duel = Bukkit.getPlayer(args[1]);
                    if(duel != null && duel.isOnline()) {
                        if(duelqtake1.containsKey(p) && duelqtake1.get(p).contains(duel)) {
                            if (!isdueling(p) && !isdueling(duel)) {
                                if(isArenaused() != "nofound") {
                                    if (duelq1.containsKey(duel) && duelq1.get(duel).equals(p)) {
                                        if(pendingdueltag.containsKey(p)) {
                                            utils.sendmsg(p, "&4&l(!) &r&f대전중에는 대전을 수락하실수 없습니다");
                                        }
                                        if(pendingdueltag.containsKey(duel)) {
                                            utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 대전 중입니다");
                                        }





                                        duelq1.remove(p, duel);
                                        if (duelqtake1.containsKey(p)) {
                                            ArrayList<Player> duelqlist = duelqtake1.get(p);
                                            duelqlist.remove(duel);
                                            duelqtake1.put(duel, duelqlist);
                                        }

                                        utils.sendmsg(p, "\n&f&l(!) &c5초&7후 &f" + duel.getName() + "&7(이)와의 대전이 시작됩니다. &c데미지를 받거나 움직일시 &7대전이 취소됩니다\n");
                                        utils.sendmsg(duel, "\n&f&l(!) &c5초&7후 &f" + p.getName() + "&7(이)와의 대전이 시작됩니다. &c데미지를 받거나 움직일시 &7대전이 취소됩니다\n");
                                        pendingdueltag.put(p, duel);
                                        pendingdueltag.put(duel, p);
                                        new BukkitRunnable() {
                                            public void run() {
                                                if(pendingdueltag.containsKey(p) && pendingdueltag.containsKey(duel)) {
                                                    if(!isdueling(p) && !isdueling(duel)) {
                                                        startduel(p, duel, isArenaused());
                                                    } else {
                                                        utils.sendmsg(p, "\n&c&l(!) &7양쪽모두 대전중이 아닐때만 대전이 가능합니다.\n");
                                                        utils.sendmsg(duel, "\n&c&l(!) &7양쪽모두 대전중이 아닐때만 대전이 가능합니다.\n");
                                                    }
                                                    pendingdueltag.remove(p);
                                                    pendingdueltag.remove(duel);
                                                } else {
                                                    utils.sendmsg(p, "\n&c&l(!) &7대전이 취소되었습니다\n");
                                                    utils.sendmsg(duel, "\n&c&l(!) &7대전이 취소되었습니다\n");
                                                }
                                            }

                                        }.runTaskLater(main.getInstance(), 100L);









                                    } else {
                                        utils.sendmsg(p, "&4&l(!) &r&f해당 유저가 보낸 대전은 만료되었거나 취소되었습니다");
                                    }
                                } else {
                                    utils.sendmsg(p, "&4&l(!) &r&f현재 모든 대전장들이 사용 중입니다. 나중에 다시시도해주세요." +
                                            "  &7:(");
                                }


                            } else {
                                utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 대전 중입니다");
                            }
                        } else {
                            utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 당신에게 대전신청을 한적이 없거나 만료됬습니다");
                        }
                    } else {
                        utils.sendmsg(p, "&4&l(!) &r&f해당 유저는 PVP서버에 접속해있지 않거나 오프라인입니다");
                    }




                } else if(args[0].equalsIgnoreCase("취소")) {







                    Player duel = Bukkit.getPlayer(args[1]);
                    if(duel != null) {
                        duelq1.remove(p, duel);

                        if(duelqtake1.containsKey(duel)) {
                            ArrayList<Player> duelqlist = duelqtake1.get(duel);
                            if(duelqlist.contains(p)) {
                                duelqlist.remove(p);
                            }
                            duelqtake1.put(duel, duelqlist);
                        } else {
                            ArrayList<Player> duelqlist = new ArrayList<>();
                            duelqtake1.put(duel, duelqlist);
                        }
                        utils.sendmsg(p, "&4&l(!) &r&f대전 신청 취소 완료");

                    } else {

                    }
                } else if(args[0].equalsIgnoreCase("설정") && p.hasPermission("itndevteams.admin")) {
                    String k = args[1];
                    Location loc = p.getLocation();
                    locations.put(k, loc2string(loc));
                    if(k.contains("=")) {
                        String[] parts = k.split("=");
                        Arenausage.put(parts[0], false);
                    }

                    utils.sendmsg(p, "해당 위치로 " + k + "설정완료");
                } else if(args[0].equalsIgnoreCase("셋업") && p.hasPermission("itndevteams.admin")) {
                    utils.sendmsg(p, "&c&lPENDING &7셋업중.....");
                    setupduels();
                } else if(args[0].equalsIgnoreCase("참가")) {
                    if(onGoingTournament) {
                        if(!TournamentStarted) {
                            if (tournament.containsKey(p)) {
                                utils.sendmsg(p, "&c&lERROR &r&7이미 토너먼트에 참가했습니다");
                                return true;
                            }
                            AddToTournament(p);
                            utils.sendmsg(p, "&c&lSUCCESS &r&7토너먼트에 참가했습니다");
                        } else {
                            utils.sendmsg(p, "&c&lERROR &r&7토너먼트가 이미 시작하였습니다. 참가가 불가능합니다");
                            return true;
                        }
                    } else {
                        utils.sendmsg(p, "&c&lERROR &r&7현재는 진행중인 토너먼트가 없습니다");
                    }
                } else if(args[0].equalsIgnoreCase("퇴장")) {
                    if(onGoingTournament) {
                        if(tournament.containsKey(p)) {
                            if(TournamentStarted) {
                                utils.sendmsg(p, "&c&lSUCCESS &r&7토너먼트가 이미 시작하였습니다. 퇴장이 불가능합니다");
                            } else {
                                utils.sendmsg(p, "&c&lSUCCESS &r&7토너먼트에서 퇴장했습니다");
                                tournament.remove(p);
                            }

                        }
                    } else {
                        utils.sendmsg(p, "&c&lERROR &r&7현재는 진행중인 토너먼트가 없습니다");
                    }
                }
            } else if(args.length < 1) {
                Player p = (Player) sender;
                utils.sendmsg(p, "&c&l(!) &r&f/대전 신청 &7(이름) &8: &f대전을 신청한다");


            }
        } else if(label.equalsIgnoreCase("아레나") && sender.hasPermission("itndevteams.admin")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("삭제")) {
                    Player p = (Player) sender;
                    if(locations.containsKey(args[1] + "=spec")) {
                        utils.sendmsg(p, "&c&lSUCESS &7성공적으로 아레나 " + args[1] + "에 대한 spec 위치를 삭제했습니다");
                        locations.remove(args[1] + "=spec");
                    }
                    if(locations.containsKey(args[1] + "=pos1")) {
                        utils.sendmsg(p, "&c&lSUCESS &7성공적으로 아레나 " + args[1] + "에 대한 pos1 위치를 삭제했습니다");
                        locations.remove(args[1] + "=pos1");
                    }
                    if(locations.containsKey(args[1] + "=pos2")) {
                        utils.sendmsg(p, "&c&lSUCESS &7성공적으로 아레나 " + args[1] + "에 대한 pos2 위치를 삭제했습니다");
                        locations.remove(args[1] + "=pos2");
                    }
                    if(Arenausage.containsKey(args[1])) {
                        Arenausage.remove(args[1]);
                        utils.sendmsg(p, "&c&lSUCESS &7아레나 등록 취소완료");
                    }
                } else if(args[0].equalsIgnoreCase("등록")) {
                    Player p = (Player) sender;
                    String arenaname2 = args[1];
                    Arenausage.put(arenaname2, false);
                    utils.sendmsg(p, "&c&lSUCESS &7성공적으로 아레나 등록 완료! 등록을 해야만 사용이 가능합니다 (또는 리부팅시 자동 적용되므로 주의 요함)");
                }
            } else if(args.length == 3) {
                if(args[0].equalsIgnoreCase("설정")) {
                    Player p = (Player) sender;
                    String arenaname = args[1];
                    Location loc = p.getLocation();
                    if(!arenaname.contains("=")) {
                        locations.put(arenaname + "=" + args[2], loc2string(loc));
                        utils.sendmsg(p, "&c&lSUCESS &7성공적으로 아레나 " + args[1] + "에 대한 " + args[2] + " 위치를 해당 위치로 설정했습니다");
                        utils.sendmsg(p, "&c&lWARNING &7아레나를 설정할때는 무조건 pos1, pos2, spec 위치를 전부 지정해야 합니다 (무조건 소문자로)");


                    } else {
                        utils.sendmsg(p, "&c&lWARNING &7해당 기호 =는 사용 불가능한 기호입니다");
                    }

                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("목록")) {
                    sender.sendMessage("&m&7----&c&l아레나 목록&m&7----");
                    for(String keys : Arenausage.keySet()) {
                        sender.sendMessage("&7= &6&l" + keys);
                    }
                } else if(args[0].equalsIgnoreCase("도움말")) {
                    utils.sendmsg((Player) sender, "&m&7----&c&l아레나 도움말&m&7----");
                    utils.sendmsg((Player) sender, "&7/아레나 삭제 &f(아레나이름) &8: &f해당 아레나의 등록과 좌표를 전체삭제한다");
                    utils.sendmsg((Player) sender, "&7/아레나 등록 &f(아레나이름) &8: &f해당 아레나를 등록한다. (좌표 3개 전부지정후 사용바람)");
                    utils.sendmsg((Player) sender, "&7/아레나 설정 &f(아레나이름) &f(pos1/pos2/spec [전부 무조건 소문자로]) &8: &f해당 아레나의 좌표 지점들을 설정한다");
                    utils.sendmsg((Player) sender, "&7/아레나 셋업 &8: &f등록되지 않은 모든 아레나들을 확인하여 등록한다. 사용 자제 바람");
                    utils.sendmsg((Player) sender, "&7/아레나 목록 &8: &f등록되어 사용가능한 아레나들의 목록을 확인한다");
                }
            }
        } else {
            sender.sendMessage("&c&lWARNING &7권한이 없습니다");
        }
        return false;
    }
    public static void setupduels() {
        for(String k : locations.keySet()) {
            if(k.contains("=")) {
                String[] parts = k.split("=");
                Arenausage.put(parts[0], false);
            }
        }
    }

    public static void startduel(Player p1, Player p2, String arena) {
        if(Arenausage.get(arena).equals(true)) {
            utils.sendmsg(p1, "\n&4&l(!) &c재확인 결과 아레나가 사용 중이므로 오류를 방지하기 위해 대전이 자동으로 취소됩니다.\n");
            utils.sendmsg(p2, "\n&4&l(!) &c재확인 결과 아레나가 사용 중이므로 오류를 방지하기 위해 대전이 자동으로 취소됩니다.\n");
            return;
        }

        String coords1 = duelcommand.locations.get(arena + "=pos1");
        String coords2 = duelcommand.locations.get(arena + "=pos2");
        System.out.println(coords1 + "/" + coords2);
        Location pos1 = string2loc(coords1);
        Location pos2 = string2loc(coords2);
        Arenausage.put(arena, true);
        nameofArena.put(p1, arena);
        nameofArena.put(p2, arena);
        ArrayList<Player> arenausers = new ArrayList<>();
        arenausers.add(p1);
        arenausers.add(p2);
        dueler.put(p1, p2);
        dueler.put(p2, p1);
        isduelinghm.put(p1, true);
        isduelinghm.put(p2, true);
        Arenauser.put(arena, arenausers);
        p1.teleport(pos1);
        p2.teleport(pos2);
        utils.sendmsg(p1, " \n    &6&l5초후 대전시작... \n ");
        utils.sendmsg(p2, " \n    &6&l5초후 대전시작... \n ");
        utils.sendmsg(p1, " \n    &c&l대전중 나가시면 엄중 처벌됩니다 \n ");
        utils.sendmsg(p2, " \n    &6&l대전중 나가시면 엄중 처벌됩니다 \n ");

        new BukkitRunnable() {
            int c = 0;
            @Override
            public void run() {
                if(p1 == null) {
                    cancel();
                }
                if(p2 == null) {
                    cancel();
                }
                if(c == 0) {
                    cancelmovements.put(p1.getUniqueId().toString(), true);
                    cancelmovements.put(p2.getUniqueId().toString(), true);

                    utils.sendmsg(p2, String.valueOf(5 - c) + "초");
                    utils.sendmsg(p1, String.valueOf(5 - c) + "초");
                } else if(c == 5) {
                    utils.sendmsg(p2, "시작");
                    utils.sendmsg(p1, "시작");
                    cancelmovements.remove(p1.getUniqueId().toString(), true);
                    cancelmovements.remove(p2.getUniqueId().toString(), true);
                    cancel();
                } else {
                    utils.sendmsg(p2, String.valueOf(5 - c) + "초");
                    utils.sendmsg(p1, String.valueOf(5 - c) + "초");
                }
                c = c + 1;

            }
        }.runTaskTimer(main.getInstance(), 20L, 20L);

    }
    public static void endduel(Player p1, Player p2, String arena, String p1name, String p2name) {
        nameofArena.remove(p1);
        nameofArena.remove(p2);
        Arenauser.remove(arena);
        dueler.remove(p1);
        dueler.remove(p2);
        Player loser;
        String spec1 = locations.get(arena + "=spec");
        Location spec = string2loc(spec1);
        String spawn1 = locations.get("spawn");

        Location spawn = string2loc(spawn1);//new Location(Bukkit.getWorld("flatworld"), 1000, 101, 1001);

        /*if(p1 == winner) {
            winner = p1;
            loser = p2;
            utils.sendmsg(winner, " \n &a&l" + winner.getName() + "&7이가 &c&l" + loser.getName() + "&7와의 듀얼에서 승리했습니다 \n ");
            new BukkitRunnable() {

                @Override
                public void run() {
                    utils.sendmsg(p1, ""); //winner
                    utils.sendmsg(p2, ""); //loser
                    p2.teleport(spec);
                    p1.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6&l승리!"), ChatColor.translateAlternateColorCodes('&', "&75초후 자동으로 스폰으로 이동됩니다"), 10, 80, 20);
                    p2.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&l사망 :("), ChatColor.translateAlternateColorCodes('&', "&75초후 자동으로 스폰으로 이동됩니다"), 10, 80, 20);
                    utils.sendmsg(p1, " \n&6&l승리!  \n&75초후 자동으로 스폰으로 이동됩니다 \n ");
                    utils.sendmsg(p2, " \n&c&l사망 :(  \n&75초후 자동으로 스폰으로 이동됩니다 \n ");
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            p1.teleport(spawn);
                            p2.teleport(spawn);
                            isduelinghm.remove(p1);
                            isduelinghm.remove(p2);

                        }
                    }.runTaskLater(main.getInstance(), 120L);

                }
            }.runTaskLater(main.getInstance(), 20L);
        } else if(p2 == winner) {*/

            new BukkitRunnable() {

                @Override
                public void run() {
                    utils.sendmsg(p2, "&a" + p2name + "&7이가 승리했습니다");
                    utils.sendmsg(p2, ""); //winner
                    utils.sendmsg(p1, ""); //loser
                    p1.teleport(spec);
                    p2.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6&l승리!"), ChatColor.translateAlternateColorCodes('&', "&75초후 자동으로 스폰으로 이동됩니다"), 10, 80, 20);
                    p1.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&l사망 :("), ChatColor.translateAlternateColorCodes('&', "&75초후 자동으로 스폰으로 이동됩니다"), 10, 80, 20);
                    utils.sendmsg(p2, " \n&6&l승리!  \n&75초후 자동으로 스폰으로 이동됩니다 \n ");
                    utils.sendmsg(p1, " \n&c&l사망 :(  \n&75초후 자동으로 스폰으로 이동됩니다 \n ");


                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            p1.teleport(spawn);
                            p2.teleport(spawn);
                            isduelinghm.remove(p1);
                            isduelinghm.remove(p2);
                            Arenausage.put(arena, false);

                        }
                    }.runTaskLater(main.getInstance(), 120L);

                }
            }.runTaskLater(main.getInstance(), 20L);
        }












    public void send2loc(Player p, String location) {
        p.teleport(string2loc(location));
    }
















    public Boolean isdueling(Player p) {
        if(isduelinghm.containsKey(p)) {
            if(isduelinghm.get(p) != null) {
                return isduelinghm.get(p);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public String isArenaused() {
        int c = 0;
        for(String arenas : Arenausage.keySet()) {
            c = c + 1;
            if(c == Arenausage.keySet().size()) {
                if(Arenausage.get(arenas).equals(false) || Arenausage.get(arenas) == null) {

                    return arenas;

                } else {
                    return "nofound";
                }
            } else {
                if(Arenausage.get(arenas).equals(false) || Arenausage.get(arenas) == null) {

                    return arenas;

                }
            }


        }
        return null;
    }

    public static String loc2string(Location loc) {

        String a = String.valueOf(Math.round(loc.getPitch()));
        String b = String.valueOf(Math.round(loc.getYaw()));
        String c = String.valueOf(loc.getWorld().getName());
        String d = String.valueOf(loc.getX());
        String e = String.valueOf(loc.getY());
        String f = String.valueOf(loc.getZ());

        return a + ":=:" + b + ":=:" + c + ":=:" + d + ":=:" + e + ":=:" + f;
    }
    public static Location string2loc(String coords) {
        if(coords.contains(":=:")) {
            String[] skadis = coords.split(":=:");
            if (skadis.length == 6) {
                int a = Integer.valueOf(skadis[0]);
                int b = Integer.valueOf(skadis[1]);
                String c = skadis[2];
                double d = Double.valueOf(skadis[3]);
                double e = Double.valueOf(skadis[4]);
                double f = Double.valueOf(skadis[5]);
                Location loc = new Location(Bukkit.getWorld(c), d, e, f);
                loc.setPitch(a);
                loc.setYaw(b);
                return loc;
            }
        }
        return null;


    }
}
