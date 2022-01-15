package com.itndev.teams;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class teamcommands implements CommandExecutor {
    public static HashMap<String, Long> all = new HashMap<>();
    public static HashMap<String, Long> moneycmd = new HashMap<>();
    public static HashMap<Player, Player> duelrq = new HashMap<>();

    public static HashMap<Player, String> duel = new HashMap<>();

    public static HashMap<String, ArrayList<Player>> duels = new HashMap<>();

    public static HashMap<String, ArrayList<Location>> duelmap = new HashMap<>();

    public void setupDuels() {
        Location arena1 = string2loc("world:123:123:123");
        arena1.setPitch(0);
        arena1.setYaw(180);
        Location arena2 = arena1.add(0, 0, -14);
        arena2.setPitch(0);
        arena2.setYaw(0);


    }
    public void Duel(Player p1, Player p2) {

    }
    public void placeglass(Location loc) {
        Location loc1 = loc.add(0, 1, 1);
        Location loc2 = loc.add(0, 1, -1);
        Location loc3 = loc.add(1, 1, 0);
        Location loc4 = loc.add(-1, 1, 0);
        Location loc5 = loc.add(0, 2, 0);
        loc1.getBlock().setType(Material.GLASS);
        loc2.getBlock().setType(Material.GLASS);
        loc3.getBlock().setType(Material.GLASS);
        loc4.getBlock().setType(Material.GLASS);
        loc5.getBlock().setType(Material.GLASS);
    }
    public void removeglass(Location loc) {
        Location loc1 = loc.add(0, 1, 1);
        Location loc2 = loc.add(0, 1, -1);
        Location loc3 = loc.add(1, 1, 0);
        Location loc4 = loc.add(-1, 1, 0);
        Location loc5 = loc.add(0, 2, 0);
        loc1.getBlock().setType(Material.AIR);
        loc2.getBlock().setType(Material.AIR);
        loc3.getBlock().setType(Material.AIR);
        loc4.getBlock().setType(Material.AIR);
        loc5.getBlock().setType(Material.AIR);

    }

    @Deprecated
    @Override
    public boolean onCommand(CommandSender Sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("정보")) {
            if(args.length >= 1) {
                Player p = (Player) Sender;
                if(Bukkit.getPlayer(args[0]) != null) {
                    Player Target = Bukkit.getPlayer(args[0]);
                    @Deprecated
                    Inventory inv = Bukkit.createInventory(p, 54, ChatColor.translateAlternateColorCodes('&', "&e" + Target.getName() + "&f님의 인벤토리"));
                    ArrayList<Integer> exp = new ArrayList<>();
                    exp.addAll(Arrays.asList(10, 19, 28, 37, 13, 14, 15, 22, 23, 24, 31, 32, 33));
                    ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                    ItemMeta glassmeta = glass.getItemMeta();
                    glassmeta.setDisplayName("");
                    glass.setItemMeta(glassmeta);

                    for(int x = 0; x < 54; x++) {
                        if(!exp.contains(x)) {
                            inv.setItem(x, glass);
                        }

                    }
                    inv.setItem(10, Target.getInventory().getHelmet());
                    inv.setItem(19, Target.getInventory().getChestplate());
                    inv.setItem(28, Target.getInventory().getLeggings());
                    inv.setItem(37, Target.getInventory().getBoots());
                    inv.setItem(13, Target.getInventory().getItem(0));
                    inv.setItem(14, Target.getInventory().getItem(1));
                    inv.setItem(15, Target.getInventory().getItem(2));
                    inv.setItem(22, Target.getInventory().getItem(3));
                    inv.setItem(23, Target.getInventory().getItem(4));
                    inv.setItem(24, Target.getInventory().getItem(5));
                    inv.setItem(31, Target.getInventory().getItem(6));
                    inv.setItem(32, Target.getInventory().getItem(7));
                    inv.setItem(33, Target.getInventory().getItem(8));
                    p.openInventory(inv);
                } else {
                    main.sendmsg(p, "&c&l(!) &7해당 유저는 오프라인입니다");
                }
            } else {
                main.sendmsg((Player)Sender, "&c&l(!) &7올바른 명령어 사용법 : &f/정보 (이름)");
            }
        } else if(label.equalsIgnoreCase("잠금")) {
            Player p = (Player) Sender;
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("도움말")) {
                    sendlockhelp(p);
                } else if(args[0].equalsIgnoreCase("이전") && args.length >= 2) {
                    //p.performCommand("blocklocker 2 " + args[1]);
                    utils.executecommandasop(p, "blocklocker 2 " + args[1]);
                } else if(args[0].equalsIgnoreCase("2")) {
                    if(args.length >= 2) {
                        p.performCommand("blocklocker 3 " + args[1]);
                    } else {
                        p.performCommand("blocklocker 3 ");
                    }
                } else if(args[0].equalsIgnoreCase("3")) {
                    if(args.length >= 2) {
                        p.performCommand("blocklocker 4 " + args[1]);
                    } else {
                        p.performCommand("blocklocker 4 ");
                    }
                } else if(args[0].equalsIgnoreCase("1")) {
                    utils.sendmsg(p, "&a&l[&f잠금&a&l] &f해당 줄은 설정할수 없습니다. /잠금 이전 &7(이름) &f으로 잠금의 소유를 이전하십시오");
                }

            } else {
                sendlockhelp(p);
            }

        } else if(label.equalsIgnoreCase("스폰")) {
            Player p = (Player) Sender;
            p.performCommand("spawn");
        } else if(label.equalsIgnoreCase("창고")) {
            Player p = (Player) Sender;
            if(!p.hasPermission("faxcore.chestrank")) {
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다. 핫플레이스 스폰장소에서 &6Chest &f랭크로 전직해야 합니다");
                return true;
            }
            p.performCommand("bp");
        } else if(label.equalsIgnoreCase("뮤트")) {
            if(!Sender.hasPermission("faxcore.mute")) {
                Player p = (Player) Sender;
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다");
                return true;
            }
            Player p = (Player) Sender;
            if(args.length >= 1) {
                if(args.length == 1) {
                    utils.executecommandasop(p, "mute " + args[0]);
                    //p.performCommand("mute " + args[0]);
                } else if(args.length == 1) {
                    utils.executecommandasop(p, "mute " + args[0]);
                    //p.performCommand("tempmute " + args[0] + " " + args[1]);
                } else if(args.length == 2) {
                    //p.performCommand("tempmute " + args[0] + " " + args[1] + " " + msg);
                    utils.executecommandasop(p, "tempmute " + args[0] + " " + args[1]);
                } else if(args.length >= 3) {
                    String msg = main.Args2String(args, 2);
                    //p.performCommand("tempmute " + args[0] + " " + args[1] + " " + msg);
                    utils.executecommandasop(p, "tempmute " + args[0] + " " + args[1] + " " + msg);
                } else if(args.length < 1) {
                    utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
                    utils.sendmsg(p, "&f/뮤트 &7(이름) &7(기간) &7(사유) &8: &f 해당 유저를 특정 사유로 기간동안 뮤트");
                    utils.sendmsg(p, "&f기간 &8: &f숫자 뒤에 m, h, d, mo ");
                    utils.sendmsg(p, "&fm &7=&f 분&8,&f h &7=&f  시간&8,&f  d &7=&f 일&8,&f  mo &7=&f 달");
                    utils.sendmsg(p, "&f예시 &8:&f 6h &(6시간동안 뮤트)");
                    utils.sendmsg(p, "&f/뮤트해제 &7(이름) &8: &f뮤트를 해제");
                    utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
                }
            } else {
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
                utils.sendmsg(p, "&f/뮤트 &7(이름) &7(기간) &7(사유) &8: &f 해당 유저를 특정 사유로 기간동안 뮤트");
                utils.sendmsg(p, "&f기간 &8: &f숫자 뒤에 m, h, d, mo ");
                utils.sendmsg(p, "&fm &7=&f 분&8,&f h &7=&f  시간&8,&f  d &7=&f 일&8,&f  mo &7=&f 달");
                utils.sendmsg(p, "&f예시 &8:&f 6h &(6시간동안 뮤트)");
                utils.sendmsg(p, "&f/뮤트해제 &7(이름) &8: &f뮤트를 해제");
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
            }
            /*if(args.length == 1) {
                utils.executecommandasop(p, "mute " + args[0]);
                //p.performCommand("mute " + args[0]);
            } else if(args.length == 2) {
                utils.executecommandasop(p, "mute " + args[0]);
                //p.performCommand("tempmute " + args[0] + " " + args[1]);
            } else if(args.length >= 3) {
                String msg = main.Args2String(args, 2);
                //p.performCommand("tempmute " + args[0] + " " + args[1] + " " + msg);
                utils.executecommandasop(p, "tempmute " + args[0] + " " + args[1] + " " + msg);
            } else if(args.length < 1) {
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
                utils.sendmsg(p, "&f/뮤트 &7(이름) &7(기간) &7(사유) &8: &f 해당 유저를 특정 사유로 기간동안 뮤트");
                utils.sendmsg(p, "&f기간 &8: &f숫자 뒤에 m, h, d, mo ");
                utils.sendmsg(p, "&fm &7=&f 분&8,&f h &7=&f  시간&8,&f  d &7=&f 일&8,&f  mo &7=&f 달");
                utils.sendmsg(p, "&f예시 &8:&f 6h &(6시간동안 뮤트)");
                utils.sendmsg(p, "&f/뮤트해제 &7(이름) &8: &f뮤트를 해제");
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
            }*/
        } else if(label.equalsIgnoreCase("뮤트해제")) {
            if(!Sender.hasPermission("faxcore.mute")) {
                Player p = (Player) Sender;
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다");
                return true;
            }
            Player p = (Player) Sender;
            if(args.length >= 1) {
                utils.executecommandasop(p, "unmute " + args[0]);
            } else {
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
                utils.sendmsg(p, "&f/뮤트 &7(이름) &7(기간) &7(사유) &8: &f 해당 유저를 특정 사유로 기간동안 뮤트");
                utils.sendmsg(p, "&f기간 &8: &f숫자 뒤에 m, h, d, mo ");
                utils.sendmsg(p, "&fm &7=&f 분&8,&f h &7=&f  시간&8,&f  d &7=&f 일&8,&f  mo &7=&f 달");
                utils.sendmsg(p, "&f예시 &8:&f 6h &(6시간동안 뮤트)");
                utils.sendmsg(p, "&f/뮤트해제 &7(이름) &8: &f뮤트를 해제");
                utils.sendmsg(p, "&7&m------&3&l[&f/뮤트&3&l]&7&m------");
            }
        } else if(label.equalsIgnoreCase("핫타임")) {
            if(!Sender.hasPermission("faxcore.eventstart")) {
                Player p = (Player) Sender;
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다");
                return true;
            }
            Player p = (Player) Sender;
            if(args.length >= 1 && args[0].equalsIgnoreCase("e")) {
                utils.sendmsg(p, "&c&lSUCESS &7이벤트를 시작합니다");
                utils.eventitem();
            }
        } else if(label.equalsIgnoreCase("추첨시작")) {
            if(!Sender.hasPermission("faxcore.pull")) {
                Player p = (Player) Sender;
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다");
                return true;
            }
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            if(args.length >= 1) {
                utils.randomselfromplayerlist(players, args[0]);
            } else {
                utils.randomselfromplayerlist(players, null);
            }

        } else if(label.equalsIgnoreCase("기본템")) {
            if (all.containsKey(Sender.getName())) {
                int cooldownTime = 3600;
                long secondsLeft = ((Long) all.get(Sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                if (secondsLeft > 0L) {
                    Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l[ &r&f기본템 &3&l] &r&f해당 명령어는 &c" + secondsLeft + "&7초 후에 다시 사용 가능합니다"));
                    return true;
                }
            }
            all.put(Sender.getName(), Long.valueOf(System.currentTimeMillis()));
            Player p = (Player) Sender;
            utils.executecommandasop(p, "kit default");
            utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f기본템이 지급되었습니다");
        } else if(label.equalsIgnoreCase("돈")) {
            Player p = (Player) Sender;
            if (moneycmd.containsKey(Sender.getName())) {
                int cooldownTime = 3;
                long secondsLeft = ((Long) moneycmd.get(Sender.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                if (secondsLeft > 0L) {
                    Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l[ &r&f돈 &3&l] &r&f해당 명령어는 &c" + secondsLeft + "&7초 후에 다시 사용 가능합니다"));
                    return true;
                }
            }
            moneycmd.put(Sender.getName(), Long.valueOf(System.currentTimeMillis()));
            if(args.length == 0) {
                double money = main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId()));
                utils.sendmsg(p, "&3&l[ &f돈 &3&l] &r&7소유금액 &8:&7 $" + String.valueOf(Math.round(money)));
            } else if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("보내기")) {
                    if(args.length >= 3) {
                        p.performCommand("pay " + args[1] + " " + args[2]);
                        /* OfflinePlayer playerman = Bukkit.getOfflinePlayer(p.getUniqueId());
                        OfflinePlayer playerman2 = Bukkit.getOfflinePlayer(args[1]);
                        if(playerman2.hasPlayedBefore()) {
                            double money = main.econ.getBalance(playerman);
                            if(isDouble(args[2])) {
                                double money2send = Double.valueOf(args[2]);
                                if(money - money2send >= 1) {
                                    main.econ.withdrawPlayer(playerman, money2send);
                                    Player finalplayer = (Player) playerman;
                                    utils.sendmsg(finalplayer, "&3&l[ &f돈 &3&l] &r&7" + );
                                    main.econ.depositPlayer(playerman2, money2send);
                                } else {
                                    utils.sendmsg(p, "&3&l[ &f돈 &3&l] &r&7오류! 소유금액이 보내려는 금액보다 $1은 많아야 합니다");
                                }


                            }

                        } else {
                            utils.sendmsg(p, "&3&l[ &f돈 &3&l] &r&7해당 유저는 서버에 접속한적이 없습니다");
                        } */
                    } else {
                        utils.sendmsg(p, "&3&l[ &f돈 &3&l] &r&7올바른 사용법 &8:&7 /돈 보내기 (이름) (금액)");
                    }
                } else if(args[0].equalsIgnoreCase("순위")) {
                    if(args.length == 1) {
                        p.performCommand("Baltop");
                    } else if(args.length >= 2) {
                        p.performCommand("baltop " + args[1]);

                    }

                }
            }
        } else if(label.equalsIgnoreCase("candestroy")) {
            Player p = (Player) Sender;
            if(p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                Set<Material> ddd = new HashSet<>();
                ddd.add(Material.LAPIS_ORE);
                ddd.add(Material.GOLD_ORE);
                ddd.add(Material.OBSIDIAN);
                ddd.add(Material.SNOW_BLOCK);
                ddd.add(Material.DIAMOND_BLOCK);
                ddd.add(Material.IRON_ORE);
                ddd.add(Material.COAL_ORE);
                ddd.add(Material.DIAMOND_ORE);
                ddd.add(Material.REDSTONE_ORE);
                ddd.add(Material.EMERALD_ORE);
                ItemStack mainitem = p.getInventory().getItemInMainHand();
                ItemMeta mainmeta = mainitem.getItemMeta();
                mainmeta.setCanDestroy(ddd);
                mainitem.setItemMeta(mainmeta);
                utils.sendmsg(p, "&c&lSUCESS &7성공적으로 적용 완료");
            }
        } else if(label.equalsIgnoreCase("exchange")) {
            Player p = (Player) Sender;
            if(p.getInventory().getItemInMainHand().getType().equals(Material.LAPIS_LAZULI) && p.getInventory().getItemInMainHand().getAmount() == 64) {
                p.getInventory().getItemInMainHand().setAmount(0);
                utils.executecommandasop(p, "kit lapisblock");
            }
        } else if(label.equalsIgnoreCase("chattoggle")) {
            Player p = (Player) Sender;
            if(!p.hasPermission("faxcore.admin.chattoggle")) {
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f권한이 없습니다");
                return true;
            }
            if(main.getInstance().chattoggle.equals(true)) {
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f채팅얼림을 멈췄습니다");
                main.getInstance().chattoggle = false;
            } else if(main.getInstance().chattoggle.equals(false)){
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f채팅을 얼렸습니다");
                main.getInstance().chattoggle = true;
            }

        } else if(label.equalsIgnoreCase("test111")) {
            Player p = (Player) Sender;
            if(!p.getGameMode().equals(GameMode.ADVENTURE)) {
                p.setGameMode(GameMode.ADVENTURE);
            }
        } else if(label.equalsIgnoreCase("faxcore")) {
            Player p = (Player) Sender;
            faxcommands.faxcommandexecute(p, args);
        }

        return false;
    }
    public static boolean isDouble( String input ) {
        try {
            Double.parseDouble( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }


    public static void sendlockhelp(Player p) {
        utils.sendmsg(p, "&7&m--------&a&l[&f잠금 도움말&a&l]&7&m--------");
        utils.sendmsg(p, "&f/잠금 도움말 &8: &f잠금 명령어를 알려준다");
        utils.sendmsg(p, "&f/잠금 이전 &7(이름) &8: &f잠금 명령어를 알려준다");
        utils.sendmsg(p, "&f/잠금 [줄번호] &7(이름) &8: &f잠금 명령어를 알려준다");
        utils.sendmsg(p, "&c&l(&4&l!&c&l) &f잠금 줄번호 설정은 &c2&f에서 &c3&f까지만 설정이 가능하고 첫번째 줄을 바꾸려면 /잠금 이전 &7(이름) &f을 해야 합니다");
        utils.sendmsg(p, "&7&m--------&a&l[&f잠금 도움말&a&l]&7&m--------");

    }

    public static String loc2string(Location l) {
        if (l == null)
            return "";
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    public static Location string2loc(String s) {
        if (s == null || s.trim() == "")
            return null;
        String[] parts = s.split(":");
        if (parts.length == 4) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }
}
