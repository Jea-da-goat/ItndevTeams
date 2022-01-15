package com.itndev.teams;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class utils {

    public static void broadcastwarn(String k) {

        System.out.println("[Warning] " + k);

        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.translateAlternateColorCodes('&', "[Warning] " + k));
        }

    }
    public static void executecommandasop(Player p, String cmd) {
        if(p.isOp()) {
            p.performCommand(cmd);
            return;
        }
        p.setOp(true);
        p.performCommand(cmd);
        p.setOp(false);

    }

    @Deprecated
    public static void teamchat(String playeruuid, String message) {
        ArrayList<String> playeruuidarray = storage.teammember.get(storage.teampvp.get(playeruuid));
        for(String playeruuids : playeruuidarray) {
            OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
            if(msgpl.isOnline()) {
                Player pl = (Player) msgpl;
                //main.sendmsg(pl, "&a&l[&f팀채팅&a&l] &r&f" + listener.name2name(listener.uuid2name(playeruuid)) + " &7: &r&f" + message);
                pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[ &f팀채팅 &a&l] &r&f" + listener.name2name(listener.uuid2name(playeruuid)) + " &7: &r&3") + message);
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
                        if (msgpl.isOnline()) {
                            Player pl = (Player) msgpl;
                            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                        }
                        //msgpl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                    }
                }

            } else if(targetuuid.equalsIgnoreCase("TeamChat")){
                for(String playeruuids : storage.teammember.get(storage.teampvp.get(playeruuid))) {
                    OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
                    if (msgpl.isOnline()) {
                        Player pl = (Player) msgpl;
                        pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&o&l[ &r&f팀 &a&o&l] &r&f" + message));
                    }
                }
            } else {
                for (String playeruuids : storage.teammember.get(storage.teampvp.get(playeruuid))) {
                    if (playeruuids != targetuuid) {
                        OfflinePlayer msgpl = Bukkit.getOfflinePlayer(listener.uuid2name(playeruuids));
                        if(msgpl.isOnline() && !msgpl.getUniqueId().equals(targetuuid)) {
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
    public static void eventitem() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            utils.sendmsg(p, "&3&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n" +
                    "  \n" +
                    "   &6&l(&f&l핫타임&6&l) &c5&f분후 핫타임 이벤트가 시작됩니다\n" +
                    "   &c&l(&4&l!&c&l) &c인벤토리에 여유공간을 확보해주세요\n" +
                    "  \n" +
                    "&3&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
        }
        new BukkitRunnable() {

            @Override
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()) {
                    utils.sendmsg(p, "&3&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n" +
                            "  \n" +
                            "   &6&l(&f&l핫타임&6&l) &c1&f분후 핫타임 이벤트가 시작됩니다\n" +
                            "   &c&l(&4&l!&c&l) &c인벤토리에 여유공간을 확보해주세요\n" +
                            "  \n" +
                            "&3&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
                }
                new BukkitRunnable() {

                    @Override
                    public void run() {

                        //10
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            utils.sendmsg(p, "\n" +
                                    "   &6&l(&f&l핫타임&6&l) &c10 &f초\n" +
                                    "\n");
                        }
                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                //9
                                for(Player p : Bukkit.getOnlinePlayers()) {
                                    utils.sendmsg(p, "\n" +
                                            "   &6&l(&f&l핫타임&6&l) &c9 &f초\n" +
                                            "\n");
                                }
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        //8
                                        for(Player p : Bukkit.getOnlinePlayers()) {
                                            utils.sendmsg(p, "\n" +
                                                    "   &6&l(&f&l핫타임&6&l) &c8 &f초\n" +
                                                    "\n");
                                        }
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {

                                                //7
                                                for(Player p : Bukkit.getOnlinePlayers()) {
                                                    utils.sendmsg(p, "\n" +
                                                            "   &6&l(&f&l핫타임&6&l) &c7 &f초\n" +
                                                            "\n");
                                                }
                                                new BukkitRunnable() {

                                                    @Override
                                                    public void run() {

                                                        //6
                                                        for(Player p : Bukkit.getOnlinePlayers()) {
                                                            utils.sendmsg(p, "\n" +
                                                                    "   &6&l(&f&l핫타임&6&l) &c6 &f초\n" +
                                                                    "\n");
                                                        }
                                                        new BukkitRunnable() {

                                                            @Override
                                                            public void run() {

                                                                //5
                                                                for(Player p : Bukkit.getOnlinePlayers()) {
                                                                    utils.sendmsg(p, "\n" +
                                                                            "   &6&l(&f&l핫타임&6&l) &c5 &f초\n" +
                                                                            "\n");
                                                                }
                                                                new BukkitRunnable() {

                                                                    @Override
                                                                    public void run() {

                                                                        //4
                                                                        for(Player p : Bukkit.getOnlinePlayers()) {
                                                                            utils.sendmsg(p, "\n" +
                                                                                    "   &6&l(&f&l핫타임&6&l) &c4 &f초\n" +
                                                                                    "\n");
                                                                        }
                                                                        new BukkitRunnable() {

                                                                            @Override
                                                                            public void run() {

                                                                                //3
                                                                                for(Player p : Bukkit.getOnlinePlayers()) {
                                                                                    utils.sendmsg(p, "\n" +
                                                                                            "   &6&l(&f&l핫타임&6&l) &c3 &f초\n" +
                                                                                            "\n");
                                                                                }
                                                                                new BukkitRunnable() {

                                                                                    @Override
                                                                                    public void run() {

                                                                                        //2
                                                                                        for(Player p : Bukkit.getOnlinePlayers()) {
                                                                                            utils.sendmsg(p, "\n" +
                                                                                                    "   &6&l(&f&l핫타임&6&l) &c2 &f초\n" +
                                                                                                    "\n");
                                                                                        }
                                                                                        new BukkitRunnable() {

                                                                                            @Override
                                                                                            public void run() {

                                                                                                //1
                                                                                                for(Player p : Bukkit.getOnlinePlayers()) {
                                                                                                    utils.sendmsg(p, "\n" +
                                                                                                            "   &6&l(&f&l핫타임&6&l) &c1 &f초\n" +
                                                                                                            "\n");
                                                                                                }
                                                                                                new BukkitRunnable() {

                                                                                                    @Override
                                                                                                    public void run() {

                                                                                                        //실행

                                                                                                        for(Player p : Bukkit.getOnlinePlayers()) {
                                                                                                            executecommandasop(p, "kit event");
                                                                                                            utils.sendmsg(p, "\n" +
                                                                                                                    "   &6&l(&f&l핫타임&6&l) &f핫타임 아이템 &c지급완료\n" +
                                                                                                                    "\n");
                                                                                                        }
                                                                                                    }
                                                                                                }.runTaskLater(main.getInstance(), 20);
                                                                                            }
                                                                                        }.runTaskLater(main.getInstance(), 20);
                                                                                    }
                                                                                }.runTaskLater(main.getInstance(), 20);
                                                                            }
                                                                        }.runTaskLater(main.getInstance(), 20);
                                                                    }
                                                                }.runTaskLater(main.getInstance(), 20);
                                                            }
                                                        }.runTaskLater(main.getInstance(), 20);
                                                    }
                                                }.runTaskLater(main.getInstance(), 20);
                                            }
                                        }.runTaskLater(main.getInstance(), 20);
                                    }
                                }.runTaskLater(main.getInstance(), 20);
                            }
                        }.runTaskLater(main.getInstance(), 20);
                    }
                }.runTaskLater(main.getInstance(), 1000);

            }
        }.runTaskLater(main.getInstance(), 4800);
    }
    public static void runcommandfromconsole(String cmd) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
    public static void sendonlineplayers(String msg) {
        for(Player online : Bukkit.getOnlinePlayers()) {
            utils.sendmsg(online, ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void gamemodefixer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> survivalplayers = new ArrayList<>();
                for(Player onlines : Bukkit.getOnlinePlayers()) {
                    if(onlines.getGameMode().equals(GameMode.SURVIVAL)) {
                        survivalplayers.add(onlines);
                    }
                }
                if(!survivalplayers.isEmpty()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for(Player survivals : survivalplayers) {
                                survivals.setGameMode(GameMode.ADVENTURE);
                            }
                        }
                    }.runTask(main.getInstance());
                }
            }
        }.runTaskTimerAsynchronously(main.getInstance(), 20L, 20L);
    }


    public static void randomselfromplayerlist(ArrayList<Player> players, String temp) {
        sendonlineplayers("&8&l[]&7&m----------------------&8&l[]\n" +
                "  &f&l(!) &c&l5초&6&l후 추첨을 시작합니다!\n" +
                "&8&l[]&7&m----------------------&8&l[]\n");

        new BukkitRunnable() {

            @Override
            public void run() {

                sendonlineplayers("\n" +
                        "  &6&l5초\n" +
                        "\n");
                new BukkitRunnable() {

                    @Override
                    public void run() {

                        sendonlineplayers("\n" +
                                "  &6&l4초\n" +
                                "\n");
                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                sendonlineplayers("\n" +
                                        "  &6&l3초\n" +
                                        "\n");
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {

                                        sendonlineplayers("\n" +
                                                "  &6&l2초\n" +
                                                "\n");
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {

                                                sendonlineplayers("\n" +
                                                        "  &6&l1초\n" +
                                                        "\n");
                                                new BukkitRunnable() {

                                                    @Override
                                                    public void run() {
                                                        if(temp != null) {
                                                            Player tempplayer = Bukkit.getPlayer(temp);
                                                            if(tempplayer != null) {
                                                                sendonlineplayers("&8&l[]&7&m----------------------&8&l[]\n" +
                                                                        "  &f&l(!) &6&l추첨완료! &7&l" + tempplayer.getName() + "&f이가 당첨되었습니다\n" +
                                                                        "&8&l[]&7&m----------------------&8&l[]\n");
                                                                return;
                                                            } else {

                                                            }
                                                        }
                                                        Random random = new Random();
                                                        int c = Bukkit.getOnlinePlayers().size();
                                                        int v = random.nextInt(c);
                                                        int vplus = v + 1;
                                                        int k = 0;
                                                        Player selected = null;
                                                        for(Player onlines : Bukkit.getOnlinePlayers()) {
                                                            k = k + 1;
                                                            if(k == vplus) {
                                                                selected = onlines;
                                                                break;
                                                            }
                                                        }
                                                        if(selected != null) {
                                                            sendonlineplayers("&8&l[]&7&m----------------------&8&l[]\n" +
                                                                    "  &f&l(!) &6&l추첨완료! &7&l" + selected.getName() + "&f이가 당첨되었습니다\n" +
                                                                    "&8&l[]&7&m----------------------&8&l[]\n");
                                                        } else {
                                                            sendonlineplayers("\n" +
                                                                    "  &c&lERROR &f오류발생 ! 추첨이 취소되었습니다\n" +
                                                                    "\n");
                                                        }



                                                    }
                                                }.runTaskLater(main.getInstance(), 20L);

                                            }
                                        }.runTaskLater(main.getInstance(), 20L);

                                    }
                                }.runTaskLater(main.getInstance(), 20L);

                            }
                        }.runTaskLater(main.getInstance(), 20L);

                    }
                }.runTaskLater(main.getInstance(), 20L);

            }
        }.runTaskLater(main.getInstance(), 20L);
    }
    public static double finaldmg(Player Attacker, Player Damaged, double Damage) {
        double finald = 0;
        double def = 0;
        double atk = 0;
        if(effectlist.DefLore.containsKey(Damaged)) {
            def = effectlist.DefLore.get(Damaged)/2;
        }
        if(effectlist.AtkLore.containsKey(Attacker)) {
            atk = effectlist.AtkLore.get(Attacker)/2;
        }
        finald = Damage + atk - def;
        if(finald <= 0) {
            finald = 1;
        }
        return finald;
    }
    public static void limiteditems(Player Attacker, Player Damaged, Integer customdata) {
        if(customdata.equals(1234001)) {
            if(Attacker.getInventory().getItemInMainHand().getAmount() == 1) {
                Attacker.getInventory().getItemInMainHand().setAmount(0);
            } else {
                Attacker.getInventory().getItemInMainHand().setAmount(Attacker.getInventory().getItemInMainHand().getAmount() - 1);
            }
            utils.sendmsg(Attacker, "&3&l[ &a디버프 &3&l] &f" + Damaged.getName() +" 이에게 &8구속 1&f을 &c20초&f간 부여했습니다");
            PotionEffect po = new PotionEffect(PotionEffectType.SLOW, 400, 0);
            utils.sendmsg(Damaged, "&3&l[ &c디버프 &3&l] &f" + Attacker.getName() +" 이가 당신을 디버프 했습니다");
            Damaged.addPotionEffect(po);

        } else if(customdata.equals(1234002)) {

        }

    }

    public static void lottery(Player p) {
        Random random = new Random();
        OfflinePlayer op = Bukkit.getOfflinePlayer(p.getUniqueId());
        int unchainspec = random.nextInt(1001);
        if(unchainspec == 1000) {

            utils.sendonlineplayers("&3&l[ &6&l복권 &3&l] &f&l" + p.getName() + " &r&f이가 1000만원짜리 &6&l행운&r&f의 복권에 당첨됬습니다");
            utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l1000만원(이)가 당신의 계좌에 입금되었습니다");
            main.econ.depositPlayer(op, 10000000);
        } else if(unchainspec < 1000 &&unchainspec >= 950) {
            utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l50만원짜리 행운의 복권에 당첨되었습니다");
            utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l50만원(이)가 당신의 계좌에 입금되었습니다");
            main.econ.depositPlayer(op, 500000);
        } else if(unchainspec >= 1 && unchainspec < 300) {
            utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l30만원짜리 행운의 복권에 당첨되었습니다");
            utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l30만원(이)가 당신의 계좌에 입금되었습니다");
            main.econ.depositPlayer(op, 300000);
        } else {
            int maxpotatoes = random.nextInt(10);
            if(maxpotatoes >= 7) {
                //20
                utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l20만원짜리 행운의 복권에 당첨되었습니다");
                utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l20만원(이)가 당신의 계좌에 입금되었습니다");
                main.econ.depositPlayer(op, 200000);
            } else {
                //10
                utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l10만원짜리 행운의 복권에 당첨되었습니다");
                utils.sendmsg(p, "&3&l[ &6&l복권 &3&l] &f&l10만원(이)가 당신의 계좌에 입금되었습니다");
                main.econ.depositPlayer(op, 100000);
            }
        }
    }
    public static void tagliveupdater() {

        if(true) {
            return;
        }
        new BukkitRunnable() {
            public void run() {
                for(Player onlines : Bukkit.getOnlinePlayers()) {
                    nametagmanagertemp(onlines);
                }
            }
        }.runTaskTimerAsynchronously(main.getInstance(), 20L, 20L);
    }
    public static void nametagmanagertemp(Player Target) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            try {
                PacketContainer packet = updateNametagtemp((LivingEntity) Target, "",Target, onlinePlayer);
                ProtocolLibrary.getProtocolManager().sendServerPacket(onlinePlayer, packet);
            } catch (InvocationTargetException ex) {
                //getLogger().severe("Unable to update nametag packet for player " + onlinePlayer.getName() + "! Stack trace:");
                ex.printStackTrace();
                return;
            }
        }
    }

    public static PacketContainer updateNametagtemp(LivingEntity entity, String nametag2, Player TagPlayer, Player Target) {
        //String nametag = nametag2;
        String nametag = gettagforplayer(TagPlayer, Target);
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity);
        WrappedDataWatcher.Serializer chatSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer(true);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, chatSerializer),
                Optional.of(WrappedChatComponent.fromChatMessage(nametag)[0].getHandle()));

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
        return packet;


    }
    public static String gettagforplayer(Player TagPlayer, Player Target) {
        String finaltag = "";
        String playername = TagPlayer.getName();
        if(isSameTeam(TagPlayer, Target)) {
            finaltag = ChatColor.translateAlternateColorCodes('&', "&a" + playername);
        } else {
            finaltag = ChatColor.translateAlternateColorCodes('&', "&c" + playername);
        }
        return finaltag;
    }
    public static Boolean isSameTeam(Player p1, Player p2) {
        if(storage.getrank(p1).equals("nomad")) {
            return false;
        }
        if(storage.getrank(p2).equals("nomad")) {
            return false;
        }
        if(storage.getteamname(p1).equals(storage.getteamname(p2))) {
            return true;
        } else {
            return false;
        }
    }

    public static void giveTeamEffect(Player target, double raidus, PotionEffect effect, Boolean toteams) {
        ArrayList<Player> effectlist = new ArrayList<>();
        if(toteams) {
            effectlist.add(target);
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(target.getWorld() == p.getWorld()) {
                if (target.getLocation().distance(p.getLocation()) <= raidus) {
                    if (utils.isSameTeam(target, p) == toteams) {
                        effectlist.add(p);
                    }
                }
            }
        }
        if(!effectlist.isEmpty()) {
            if(!toteams) {
                effectlist.remove(target);
            }
            new BukkitRunnable() {
                public void run() {
                    for(Player k : effectlist) {
                        for(PotionEffect po : k.getActivePotionEffects()) {
                            if(po.getType() == effect.getType()) {
                                if(po.getAmplifier() <= effect.getAmplifier()) {
                                    k.removePotionEffect(effect.getType());
                                }
                            }
                        }
                        k.addPotionEffect(effect);
                    }
                }
            }.runTask(main.getInstance());
        }
    }
}