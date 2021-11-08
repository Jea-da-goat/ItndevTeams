package com.itndev.teams;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class listener implements Listener {

    public static HashMap<String, String> uuidname = new HashMap<>();
    public static HashMap<String, String> nameuuid = new HashMap<>();
    public static HashMap<String, String> namename = new HashMap<>();

    //이름 업데이트
    @EventHandler(priority = EventPriority.LOWEST)
    public void onjoin(PlayerJoinEvent e) {
        if(main.getInstance().clientname.equalsIgnoreCase("client1")) {
            if (!storage.teampvp.containsKey(e.getPlayer().getUniqueId().toString())) {
                //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad");
            }
            if (uuidname.containsKey(e.getPlayer().getUniqueId().toString()) /*e.getPlayer().hasPlayedBefore()*/) {
                if (!storage.teamrank.containsKey(e.getPlayer().getUniqueId().toString())) {
                    //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad");
                }
                if (uuidname.containsKey(e.getPlayer().getUniqueId().toString())) {
                    String originname = uuidname.get(e.getPlayer().getUniqueId().toString());
                    if (!originname.equals(e.getPlayer().getName().toLowerCase(Locale.ROOT))) {


                        //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                        jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                        //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                        jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString(), "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString());

                        //nameuuid.remove(originname);
                        jedis.RedisUpdateQ.put("update:=:nameuuid:=:remove:=:" + originname + ":=:add:=:" + "nomad", "update:=:nameuuid:=:remove:=:" + originname + ":=:add:=:" + "nomad");

                        //namename.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getName());
                        jedis.RedisUpdateQ.put("update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName(), "update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName());

                    }
                } else {
                    //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                    jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                    //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                    jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString(), "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString());

                    //namename.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getName());
                    jedis.RedisUpdateQ.put("update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName(), "update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName());
                }
            } else {
                //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + "nomad");

                //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + e.getPlayer().getUniqueId().toString() + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString(), "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getUniqueId().toString());

                //namename.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getName());
                jedis.RedisUpdateQ.put("update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName(), "update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName());
            }
        }


    }
    public static String uuid2name(String uuid) {
        String v = listener.uuidname.get(uuid);
        return v;
    }
    public static String name2uuid(String name) {
        String v = listener.nameuuid.get(name.toLowerCase(Locale.ROOT));
        return v;
    }
    public static String name2name(String name) {
        String v = listener.namename.get(name.toLowerCase(Locale.ROOT));
        return v;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamagePvP(EntityDamageByEntityEvent e) {
        if (!e.getEntity().getLocation().getWorld().equals(main.getInstance().duelmapworld) && e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if(storage.teampvp.containsKey(damager.getUniqueId().toString()) && storage.teampvp.containsKey(player.getUniqueId().toString())) {
                if (storage.teampvp.get(player.getUniqueId().toString()).equals(storage.teampvp.get(damager.getUniqueId().toString()))) {
                    e.setCancelled(true);
                    main.sendmsg((Player) e.getDamager(), "&c&l같은 팀에 소속된 멤버를 때릴수 없습니다");
                }
            }


        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if(p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002) {
                p.setVelocity(p.getLocation().getDirection().multiply(-1));
            }
        }
    }
    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String k = e.getMessage();
        if(commands.teamchat.containsKey(e.getPlayer())) {
            if(storage.teampvp.containsKey(e.getPlayer().getUniqueId().toString())) {
                e.setCancelled(true);

                //utils.teamchat(e.getPlayer().getUniqueId().toString(), k);
                jedis.RedisChatSyncQ.put("chat" + ":=:" + e.getPlayer().getUniqueId().toString() + ":=:" + k, "chat" + ":=:" + e.getPlayer().getUniqueId().toString() + ":=:" + k);
            } else {
                main.sendmsg(e.getPlayer(), "&c&l(!) &7소속된 팀이 없어서 팀 채팅에서 강제적으로 퇴장당했습니다");
                commands.teamchat.remove(e.getPlayer());
            }
        } else if(k.length() > 64){
            e.setCancelled(true);
            main.sendmsg(e.getPlayer(), "&c&l(!) &f&l메시지가 너무 깁니다. 64자 미만으로 줄여주세요");

        }
    }
    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST)
    public  void onClick(PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        Block block = e.getClickedBlock();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.OAK_WALL_SIGN){
        Sign sign = (Sign) block.getState();
            if(e.getPlayer() instanceof Player){
                    if(sign.getLine(0).toLowerCase(Locale.ROOT).equals(ChatColor.translateAlternateColorCodes('&', "&4&l[ &8구매 &4&l]"))){
                        String v = sign.getLine(2);
                        String[] parts = v.split(" ");
                        if(parts.length == 3) {
                            double k = Integer.valueOf(parts[2]);
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= k) {
                                main.econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), k);
                                main.sendmsg(p, "&a&l[&f&l구매&a&l] &r&f성공적으로 잠금을 구매하셨습니다");
                                sign.setLine(0, "[Private]");
                                sign.setLine(1, p.getName());
                                sign.setLine(2, "");
                                sign.setLine(3, "");
                                sign.update();
                            } else {
                                main.sendmsg(p, "&a&l[&f&l구매&a&l] &r&f돈이 부족합니다!");
                            }
                        }
                }
            }
        }
    }
    @Deprecated
    @EventHandler
    public void onclick(InventoryClickEvent e) {
        if(e.getView().getTitle().contains("님의 인벤토리")) {
            main.sendmsg((Player) e.getWhoClicked(), "&c&l(!) &7상대방의 정보를 보고 있는 도중에는 해당 엑션을 취하실수 없습니다");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();



        }
    }


}
