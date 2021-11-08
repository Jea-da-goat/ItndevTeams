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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class teamcommands implements CommandExecutor {
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
        if(label.equalsIgnoreCase("대전")) {
            Player p = (Player) Sender;
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("신청")) {
                    if(Bukkit.getPlayer(args[1].toString()) != null) {
                        Player p2 = Bukkit.getPlayer(args[1].toString());
                        if(!storage.getteamname(p).equals(storage.getteamname(p2))) {
                            if(!duels.isEmpty()) {
                                main.sendmsg(p, p2.getName() + "님에게 대전신청을 보냈습니다");
                                main.sendmsg(p2, p.getName() + "님이 대전신청을 보냈습니다. /대전 수락 " + p.getName() + " ");
                            }

                        } else {
                            main.sendmsg(p, "같은 팀에 소속된 멤버에게는 대천 신청을 보낼수 없습니다");
                        }
                    } else {
                        main.sendmsg(p, "해당 유저는 오프라인입니다");
                    }

                } else if(args[0].equalsIgnoreCase("수락")) {

                } else if(args[0].equalsIgnoreCase("관전")) {

                }
            }
        } else if(label.equalsIgnoreCase("정보")) {
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
        }

        return false;
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
