package com.itndev.teams;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.itndev.teams.ItemUtils.CustomItemManager;
import com.itndev.teams.KnockbackManager.CustomKnockBack;
import io.papermc.paper.event.player.AsyncChatEvent;
import jdk.random.Xoroshiro128PlusPlus;
import me.leoko.advancedban.Universal;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class listener implements Listener {

    public static ConcurrentHashMap<String, String> uuidname = new ConcurrentHashMap<>(); // uuid -> lowercase name
    public static ConcurrentHashMap<String, String> nameuuid = new ConcurrentHashMap<>(); // lowercase name -> uuid
    public static ConcurrentHashMap<String, String> namename = new ConcurrentHashMap<>(); // lowercase name -> origin name
    //public static HashMap<String, String> originhouseinfo = new HashMap<>(); // houseinfo / uuid
    //public static HashMap<String, ArrayList<String>> originreversehouseinfo = new HashMap<>(); // uuid / listofhouseinfo
    //public static HashMap<String, String> houseinfo = new HashMap<>(); // houseinfo / uuid
    //public static HashMap<String, ArrayList<String>> reversehouseinfo = new HashMap<>(); // uuid / listofhouseinfo
    //public static HashMap<String, Long> hitcooldown = new HashMap<>(); //테스트중 렌덤 넉백용

    public static DecimalFormat df = new DecimalFormat("0.0");
    public static HashMap<String, Long> chatevent = new HashMap<>();
    public static HashMap<String, Long> kbstick = new HashMap<>();


    public static File file2;

    public static FileConfiguration customlocalstorage;
    public static void createlocalstorage() {
        file2 = new File(main.getInstance().getDataFolder(), "PlayerUUID.yml");
        if(!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        }
        customlocalstorage = YamlConfiguration.loadConfiguration(file2);
    }

    public static void resetlocalstorage() throws IOException {
        //file = new File(main.getInstance().getDataFolder(), "LocalStorage.yml");
        /*customlocalstorage.set("uuidname.", null);
        customlocalstorage.set("namename.", null);
        customlocalstorage.set("nameuuid.", null);*/
        //customlocalstorage.set("teammember", null);
        //5uh&^IrR%$T^&E%$%$tgr
        file2.delete();
        file2.createNewFile();
        customlocalstorage.getKeys(false).forEach(key ->{
            customlocalstorage.set(key, null);
        });
        saveStorage();

    }


    public static void saveStorage() {

        try {
            customlocalstorage.save(file2);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static FileConfiguration getStorage() {
        return customlocalstorage;
    }
    public static void reloadStorage() {
        customlocalstorage = YamlConfiguration.loadConfiguration(file2);
    }






    //이름 업데이트
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        //p.sendMessage("반갑습니다");
        String uuid = p.getUniqueId().toString();
        if(!p.hasPlayedBefore()) {
            p.performCommand("auto");
        }
        //p.setGameMode(GameMode.ADVENTURE);
        /*if(storage.proxyonline.containsKey(uuid)) {
            String cmd = "update the player location";
        } else {
            String cmd = "update the player location";
            jedis.RedisChatSyncQ.put("", "");
            String cmd2 = "notifiy team members that the player has joined the network";
            jedis.RedisChatSyncQ.put("notify:=:" + p.getUniqueId().toString() + ":=:" + p.getUniqueId().toString() + ":=:" + p.getName() + "님이 서버에 접속하셨습니다" + ":=:" + "true", "notify:=:" + p.getUniqueId().toString() + ":=:" + p.getUniqueId().toString() + ":=:" + p.getName() + "님이 서버에 접속하셨습니다" + ":=:" + "true");

        }*/
        if(true) { //main.getInstance().clientname.equalsIgnoreCase("client1")
            if (!storage.teampvp.containsKey(uuid)) {
                //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad");
            }
            if(storage.teamrank.containsKey(uuid) && storage.teamrank.get(uuid).equalsIgnoreCase("nomad")) {
                if(storage.teampvp.containsKey(uuid)) {
                    String teamname = storage.teampvp.get(uuid);
                    jedis.RedisUpdateQ.put("update:=:teampvp:=:remove:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teampvp:=:remove:=:" + uuid + ":=:add:=:" + "nomad");
                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad");
                    //jedis.RedisUpdateQ.put("update:=:teammember:=:remove:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teammember:=:remove:=:" + uuid + ":=:add:=:" + "nomad");

                }

            }
            if (uuidname.containsKey(uuid) /*e.getPlayer().hasPlayedBefore()*/) {
                if (!storage.teamrank.containsKey(uuid)) {
                    //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                    jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad");
                }
                if (uuidname.containsKey(uuid)) {
                    String originname = uuidname.get(uuid);
                    if (!originname.equals(e.getPlayer().getName().toLowerCase(Locale.ROOT))) {


                        //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                        jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                        //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                        jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid, "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid);

                        //nameuuid.remove(originname);
                        jedis.RedisUpdateQ.put("update:=:nameuuid:=:remove:=:" + originname + ":=:add:=:" + "nomad", "update:=:nameuuid:=:remove:=:" + originname + ":=:add:=:" + "nomad");

                        //namename.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getName());
                        jedis.RedisUpdateQ.put("update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName(), "update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName());

                    }
                } else {
                    //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                    jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                    //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                    jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid, "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid);

                    //namename.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getName());
                    jedis.RedisUpdateQ.put("update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName(), "update:=:namename:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + e.getPlayer().getName());
                }
            } else {
                //storage.teamrank.put(e.getPlayer().getUniqueId().toString(), "nomad");
                jedis.RedisUpdateQ.put("update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad", "update:=:teamrank:=:add:=:" + uuid + ":=:add:=:" + "nomad");

                //uuidname.put(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName().toLowerCase(Locale.ROOT));
                jedis.RedisUpdateQ.put("update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT), "update:=:uuidname:=:add:=:" + uuid + ":=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT));

                //nameuuid.put(e.getPlayer().getName().toLowerCase(Locale.ROOT), e.getPlayer().getUniqueId().toString());
                jedis.RedisUpdateQ.put("update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid, "update:=:nameuuid:=:add:=:" + e.getPlayer().getName().toLowerCase(Locale.ROOT) + ":=:add:=:" + uuid);

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

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if(e.getInventory().getType() == InventoryType.ANVIL) {
            e.setCancelled(true);
        }
    }

    @Deprecated
    @EventHandler(ignoreCancelled = true)
    public void onDamagePvP(EntityDamageByEntityEvent e) {
        double damage = e.getDamage();
        if (!e.getEntity().getLocation().getWorld().equals(main.getInstance().duelmapworld) && e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if(!faxcommands.PVPtoggle) {
                utils.sendmsg(damager, "&3&l[ &f&l시스템 &3&l] &rPVP가 현재 비활성화 되있습니다");
                e.setCancelled(true);
                return;
            }
            //double damage = e.getDamage();
            if(storage.teampvp.containsKey(damager.getUniqueId().toString()) && storage.teampvp.containsKey(player.getUniqueId().toString())) {
                if (storage.teampvp.get(player.getUniqueId().toString()).equals(storage.teampvp.get(damager.getUniqueId().toString()))) {
                    if(!duelcommand.isduelinghm.containsKey(player)) {
                        e.setCancelled(true);
                    }

                    //main.sendmsg((Player) e.getDamager(), "&c&l같은 팀원에게 피해를 입힐수 없습니다");
                } else {
                    if(duelcommand.pendingdueltag.containsKey(player)) {
                        Player p = player;
                        Player duel = duelcommand.pendingdueltag.get(p);
                        utils.sendmsg(p, "&4&l(!) &r&f대전장으로 이동되는중에 공격을 받아 대전이 취소되었습니다");
                        utils.sendmsg(duel, "&4&l(!) &r&f상대방이 대전장으로 이동되는중에 공격을 받아 대전이 취소되었습니다");
                        duelcommand.pendingdueltag.remove(p);
                        duelcommand.pendingdueltag.remove(duel);

                    }
                    if(duelcommand.pendingdueltag.containsKey(damager)) {
                        Player duel = damager;
                        Player duelduel = duelcommand.pendingdueltag.get(duel);
                        utils.sendmsg(duel, "&4&l(!) &r&f대전장으로 이동되는중에 공격을 하여 취소되었습니다");
                        utils.sendmsg(duelduel, "&4&l(!) &r&f상대방이 대전장으로 이동되는중에 공격을 하여 대전이 취소되었습니다");
                        duelcommand.pendingdueltag.remove(duel);
                        duelcommand.pendingdueltag.remove(duelduel);

                    }
                    ItemStack mainitem = damager.getInventory().getItemInMainHand();
                    double finaldamage = utils.finaldmg(damager, player, damage);
                    if(mainitem.containsEnchantment(Enchantment.KNOCKBACK)) {
                        if (!(kbstick.containsKey(damager.getName()) && ((Long) kbstick.get(damager.getName())).longValue() / 1000L + 1 - System.currentTimeMillis() / 1000L > 0L)) {

                            kbevent(player, damager, mainitem);
                        }
                        e.setCancelled(true);
                        return;

                    }
                    if(mainitem.hasItemMeta() && mainitem.getItemMeta().hasCustomModelData()) {
                        utils.limiteditems(damager, player, mainitem.getItemMeta().getCustomModelData());
                    }
                    onhitdamagelog(finaldamage, damager, player);
                }
            } else {
                if(duelcommand.pendingdueltag.containsKey(player)) {
                    Player p = player;
                    Player duel = duelcommand.pendingdueltag.get(p);
                    utils.sendmsg(p, "&4&l(!) &r&f대전장으로 이동되는중에 공격을 받아 대전이 취소되었습니다");
                    utils.sendmsg(duel, "&4&l(!) &r&f상대방이 대전장으로 이동되는중에 공격을 받아 대전이 취소되었습니다");
                    duelcommand.pendingdueltag.remove(p);
                    duelcommand.pendingdueltag.remove(duel);

                }
                if(duelcommand.pendingdueltag.containsKey(damager)) {
                    Player duel = damager;
                    Player duelduel = duelcommand.pendingdueltag.get(duel);
                    utils.sendmsg(duel, "&4&l(!) &r&f대전장으로 이동되는중에 공격을 하여 대전이 취소되었습니다");
                    utils.sendmsg(duelduel, "&4&l(!) &r&f상대방이 대전장으로 이동되는중에 공격을 하여 대전이 취소되었습니다");
                    duelcommand.pendingdueltag.remove(duel);
                    duelcommand.pendingdueltag.remove(duelduel);

                }
                ItemStack mainitem = damager.getInventory().getItemInMainHand();
                double finaldamage = utils.finaldmg(damager, player, damage);
                if(mainitem.containsEnchantment(Enchantment.KNOCKBACK)) {
                    if (!(kbstick.containsKey(damager.getName()) && ((Long) kbstick.get(damager.getName())).longValue() / 1000L + 1 - System.currentTimeMillis() / 1000L > 0L)) {

                        kbevent(player, damager, mainitem);
                    }
                    e.setCancelled(true);
                    return;

                }
                if(mainitem.hasItemMeta() && mainitem.getItemMeta().hasCustomModelData()) {
                    utils.limiteditems(damager, player, mainitem.getItemMeta().getCustomModelData());
                }
                onhitdamagelog(finaldamage, damager, player);

            }


        }
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Player p2 = (Player) e.getEntity();
            if((p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("$")) || (p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1002)) {
                //e.setCancelled(true);
                p2.setVelocity(p.getLocation().getDirection().multiply(-1.0D));
                //p2.setHealth(p2.getHealth() - damage);
            }
        } else if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
            if(e.getDamager().getType() == EntityType.ARROW) {
                if(((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    Player Shooter = (Player) ((Projectile) e.getDamager()).getShooter();
                    Player p2 = (Player) e.getEntity();
                    if(Shooter.getInventory().getItemInMainHand().hasItemMeta()) {
                        if(Shooter.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                            for(String st : Shooter.getInventory().getItemInMainHand().getItemMeta().getLore()) {
                                if(st.contains("그래플러")) {
                                    Vector d = e.getDamager().getLocation().getDirection().multiply(-1.0D);
                                    Vector a = Shooter.getLocation().getDirection().multiply(-1.0D);
                                    p2.setVelocity(a);
                                    p2.setVelocity(p2.getVelocity().multiply(10.0D));
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onAttackDamageSet(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.setDamage(utils.finaldmg((Player)e.getDamager(), (Player)e.getEntity(), e.getDamage()));
        }
    }
    public static void kbevent(Player player, Player damager, ItemStack mainitem) {
        Player p = player;
        kbstick.put(damager.getName(), System.currentTimeMillis());


        //e.setCancelled(true);
        int kblvl = mainitem.getEnchantmentLevel(Enchantment.KNOCKBACK);

        knockbackdmg(player);
        KnockbackControl(damager, player, kblvl);
        onhitdamagelog(1, damager, player);
    }
    public static void knockbackdmg(Player Attacked) {
        Attacked.playEffect(EntityEffect.HURT);
    }
    public static void KnockbackControl(Player Damager, Player Attacked, int kbvalue) {
        Vector d = Damager.getLocation().getDirection();
        int finalkbvalue = kbvalue;
        if(kbvalue < 0) {
            d.multiply(-1);
            finalkbvalue = kbvalue * -1;
        }

        d = d.setY(d.getY() + 1.0);
        Attacked.setVelocity(new Vector(d.multiply(kbvaluecacu(finalkbvalue)).getX() + finalkbvalue, 0.3, d.multiply(kbvaluecacu(finalkbvalue)).getZ() + finalkbvalue));
    }

    public static double kbvaluecacu(int kbvalue) {
        double finalkb;
        return 1.5 + (0.4 * kbvalue)*1.1;
        /*if(kbvalue == 1) {
            finalkb = 0.9;
        } else if(kbvalue == 2) {
            finalkb = 1.05;
        } else if(kbvalue == 3) {
            finalkb = 1.2;
        } else if(kbvalue == 4) {
            finalkb = 1.35;
        } else if(kbvalue == 5) {
            finalkb = 1.5;
        } else {
            finalkb = 2.1;
        }
        return finalkb; */
    }
    public static void onhitdamagelog(double damage, Player damager, Player player) {
        double health = player.getHealth();
        double maxhealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        utils.sendmsg(damager, "&c&o&l[ &f&o&l데미지 &c&o&l] &r&f" + player.getName() + " : " + String.valueOf(df.format(health)) + "&7/&r&f" + String.valueOf(maxhealth) + "  &c&o&l( &r&f-" + String.valueOf(df.format(damage)) + " &c&o&l)");


    }
    @Deprecated
    @EventHandler(priority = EventPriority.MONITOR)
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(p.getKiller() instanceof Player) {
            net.md_5.bungee.api.chat.TextComponent message = new TextComponent("      [클릭하여 대전수락]");
            message.setBold(true);
            message.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/대전 수락 " + p.getName()));
            //e.deathMessage(message);
            ItemStack deathitem = e.getEntity().getKiller().getInventory().getItemInMainHand();
            String deathmessage = "&c&o&l[ &f&o&l킬 로그 &c&o&l] &r&c" + p.getKiller().getName() + "&r&f이가 &7" + p.getName() + "&r&f을 &r" + deathitem.getItemMeta().getDisplayName() + "&r&f(으)로 숙청했습니다";
            e.setDeathMessage(colorize(deathmessage));
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onArmortakeon(PlayerArmorChangeEvent e) {
        //effectlist.
        if(e.getNewItem().equals(e.getOldItem())) {
            return;
        }
        Player p = e.getPlayer();
        //effectlist.RemovePotionEffects(p);
        //if(e.getOldItem() != e.getNewItem()) {
        //e.getNewItem();

        ItemStack wep = p.getInventory().getItemInMainHand();
        ItemStack helm = null;
        ItemStack ches = null;
        ItemStack leg = null;
        ItemStack boot = null;
        if (e.getSlotType().equals(PlayerArmorChangeEvent.SlotType.HEAD)) {
            helm = e.getNewItem();
            ches = p.getInventory().getChestplate();
            leg = p.getInventory().getLeggings();
            boot = p.getInventory().getBoots();
        } else if (e.getSlotType().equals(PlayerArmorChangeEvent.SlotType.CHEST)) {
            helm = p.getInventory().getHelmet();
            ches = e.getNewItem();
            leg = p.getInventory().getLeggings();
            boot = p.getInventory().getBoots();
        } else if (e.getSlotType().equals(PlayerArmorChangeEvent.SlotType.LEGS)) {
            helm = p.getInventory().getHelmet();
            ches = p.getInventory().getChestplate();
            leg = e.getNewItem();
            boot = p.getInventory().getBoots();
        } else if (e.getSlotType().equals(PlayerArmorChangeEvent.SlotType.FEET)) {
            helm = p.getInventory().getHelmet();
            ches = p.getInventory().getChestplate();
            leg = p.getInventory().getLeggings();
            boot = e.getNewItem();
        }
        itemmetacheckeffectregister(p, wep, helm, ches, leg, boot);





        //EffectHandler(e.getOldItem(), p);
        //itemmetacheckeffectregister(p, p.getInventory().getItemInMainHand());


    }
    @EventHandler(ignoreCancelled = true)
    public void PotionEffectEnd(EntityPotionEffectEvent e) {
        if(e.getCause().equals(EntityPotionEffectEvent.Cause.EXPIRATION) && e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack wep = p.getInventory().getItemInMainHand();
            ItemStack helm = p.getInventory().getHelmet();
            ItemStack ches = p.getInventory().getChestplate();
            ItemStack leg = p.getInventory().getLeggings();
            ItemStack boot = p.getInventory().getBoots();
            itemmetacheckeffectregister(p, wep, helm, ches, leg, boot);
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        ItemStack wep = p.getInventory().getItemInMainHand();
        ItemStack helm = p.getInventory().getHelmet();
        ItemStack ches = p.getInventory().getChestplate();
        ItemStack leg = p.getInventory().getLeggings();
        ItemStack boot = p.getInventory().getBoots();
        itemmetacheckeffectregister(p, wep, helm, ches, leg, boot);
    }
    @EventHandler(ignoreCancelled = true)
    public void InventoryClose(InventoryCloseEvent e) {
        //Inventory inv = e.getPlayer().getInventory();
        Player p = (Player) e.getPlayer();
        //p.sendMessage("D");
        //effectlist.RemovePotionEffects(p);
        // if(p.getInventory().getItem(e.getPreviousSlot()) != p.getInventory().getItem(e.getNewSlot())) {


        ItemStack wep = p.getInventory().getItemInMainHand();
        ItemStack helm = p.getInventory().getHelmet();
        ItemStack ches = p.getInventory().getChestplate();
        ItemStack leg = p.getInventory().getLeggings();
        ItemStack boot = p.getInventory().getBoots();
        //effectlist.RemovePotionEffects(p, getcustomdata(p.getInventory().getItem(e.getPreviousSlot())), getcustomdata(wep), getcustomdata(helm), getcustomdata(ches), getcustomdata(leg), getcustomdata(boot));
        itemmetacheckeffectregister(p, wep, helm, ches, leg, boot);
        //EffectHandler(inv.getItem(e.getPreviousSlot()), p);


    }
    @EventHandler(ignoreCancelled = true)
    public void mainhandchange(PlayerItemHeldEvent e) {
        if(!e.isCancelled()) {
            //Inventory inv = e.getPlayer().getInventory();
            Player p = e.getPlayer();
            //p.sendMessage("D");
            //effectlist.RemovePotionEffects(p);
            // if(p.getInventory().getItem(e.getPreviousSlot()) != p.getInventory().getItem(e.getNewSlot())) {


            ItemStack wep = p.getInventory().getItem(e.getNewSlot());
            ItemStack helm = p.getInventory().getHelmet();
            ItemStack ches = p.getInventory().getChestplate();
            ItemStack leg = p.getInventory().getLeggings();
            ItemStack boot = p.getInventory().getBoots();
            //effectlist.RemovePotionEffects(p, getcustomdata(p.getInventory().getItem(e.getPreviousSlot())), getcustomdata(wep), getcustomdata(helm), getcustomdata(ches), getcustomdata(leg), getcustomdata(boot));
            itemmetacheckeffectregister(p, wep, helm, ches, leg, boot);
            //EffectHandler(inv.getItem(e.getPreviousSlot()), p);
        }



    }
    @EventHandler(ignoreCancelled = true)
    public void onItemPickup(PlayerAttemptPickupItemEvent e) {
        if(!e.isCancelled()) {
            Player p = e.getPlayer();
            ItemStack Holding = p.getInventory().getItemInMainHand();
            if(Holding.getType() == Material.AIR) {
                Holding = e.getItem().getItemStack();
            }
            ItemStack helm = p.getInventory().getHelmet();
            ItemStack ches = p.getInventory().getChestplate();
            ItemStack leg = p.getInventory().getLeggings();
            ItemStack boot = p.getInventory().getBoots();
            itemmetacheckeffectregister(p, Holding, helm, ches, leg, boot);
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        if(!e.isCancelled()) {
            Player p = e.getPlayer();
            ItemStack Holding = p.getInventory().getItemInMainHand();
            if(Holding.isSimilar(e.getItemDrop().getItemStack())) {
                Holding = new ItemStack(Material.AIR);
            }
            ItemStack helm = p.getInventory().getHelmet();
            ItemStack ches = p.getInventory().getChestplate();
            ItemStack leg = p.getInventory().getLeggings();
            ItemStack boot = p.getInventory().getBoots();
            itemmetacheckeffectregister(p, Holding, helm, ches, leg, boot);
        }
    }
    /*public void EffectHandler(ItemStack olditem, Player p) {
        itemmetacheckeffectregister(p, olditem, false);
        ItemStack item1 = p.getInventory().getItemInMainHand();
        itemmetacheckeffectregister(p, item1, true);
        ItemStack item2 = p.getInventory().getHelmet();
        itemmetacheckeffectregister(p, item2, true);
        ItemStack item3 = p.getInventory().getChestplate();
        itemmetacheckeffectregister(p, item3, true);
        ItemStack item4 = p.getInventory().getLeggings();
        itemmetacheckeffectregister(p, item4, true);
        ItemStack item5 = p.getInventory().getBoots();
        itemmetacheckeffectregister(p, item5, true);
    }*/
    public void itemmetacheckeffectregister(Player p, ItemStack wep, ItemStack helm, ItemStack ches, ItemStack leg, ItemStack boot) {
        //List<ItemStack> fax = Arrays.asList(wep, helm, ches, leg, boot);
        new BukkitRunnable() {
            @Override
            public void run() {
                //int wepdata = getcustomdata(wep);
                //int helmdata = getcustomdata(helm);
                //int chesdata = getcustomdata(ches);
                //int legdata = getcustomdata(leg);
                //int bootdata = getcustomdata(boot);
                List<String> wepdata1 = new ArrayList<>();
                if(wep != null && wep.getType() != null && !wep.getType().equals(Material.AIR)) {
                    String weptype = wep.getType().toString().toLowerCase();

                    if (weptype.contains("_helmet") || weptype.contains("_chestplate") || weptype.contains("_leggings") || weptype.contains("_boots")) {
                        wepdata1 = new ArrayList<>();
                    } else {
                        wepdata1 = getLore(wep);
                    }
                } else {
                    wepdata1 = new ArrayList<>();
                }

                List<String> helmdata1 = getLore(helm);
                List<String> chesdata1 = getLore(ches);
                List<String> legdata1 = getLore(leg);
                List<String> bootdata1 = getLore(boot);
                effectlist.ApplyEffectsLoreBased(p, wepdata1, helmdata1, chesdata1, legdata1, bootdata1);
                //effectlist.ApplyEffects(p, wepdata, helmdata, chesdata, legdata, bootdata);
            }
        }.runTaskAsynchronously(main.getInstance());
    }
    @Deprecated
    public static List<String> getLore(ItemStack item) {
        if(item != null && !item.getType().equals(Material.AIR) && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            /*String val = "";
            for(String loreline : item.getLore()) {
                val = val + loreline;
            }*/
            return item.getLore();
        }
        return null;
    }
    public static Integer getcustomdata(ItemStack item) {
        if (item != null && !item.getType().equals(Material.AIR) && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
            int c = item.getItemMeta().getCustomModelData();
            if (effectlist.effectlist222.containsKey(c)) {
                return c;

            }
        }
        return 0;
    }
    /*public void itemmetacheckeffectregister2(Player p, ItemStack item) {
        if(item != null && !item.getType().equals(Material.AIR) && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
            int c = item.getItemMeta().getCustomModelData();
            if(effectlist.effectlist222.containsKey(c)) {
                effectlist.RemovePotionEffect(p, c);
            }
        }
    }*/
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if(main.getInstance().clientname.equalsIgnoreCase("client1") || main.getInstance().clientname.equalsIgnoreCase("client2")) {
            //if(!e.getPlayer().hasPermission("itndevteams.bypassbuild")) {
                if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {

                    Material d = e.getBlock().getType();
                    Location loc = e.getBlock().getLocation();
                    if (d.equals(Material.LAPIS_ORE)) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                loc.getBlock().setType(Material.LAPIS_ORE);
                            }
                        }.runTaskLater(main.getInstance(), 20);
                    } else if (d.equals(Material.GOLD_ORE)) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                loc.getBlock().setType(Material.GOLD_ORE);

                            }
                        }.runTaskLater(main.getInstance(), 20);
                    } else {
                        e.setCancelled(true);
                        if(!e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
                            e.getPlayer().setGameMode(GameMode.ADVENTURE);
                        }
                    }
                }
            //}
        }

    }
    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if(main.getInstance().clientname.equalsIgnoreCase("client1") || main.getInstance().clientname.equalsIgnoreCase("client2")) {
            //if(!e.getPlayer().hasPermission("itndevteams.bypassbuild")) {
                if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                    e.setCancelled(true);
                }
            //}
        }
    }
    @Deprecated
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String k = e.getMessage();
        Player p = e.getPlayer();
        if(!p.hasPermission("faxcore.chatbypass")) {
            if (chatevent.containsKey(p.getName())) {
                int cooldownTime = 2;
                long secondsLeft = ((Long) chatevent.get(p.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                if (secondsLeft > 0L) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!) &r&f채팅은 2초마다 한번 칠수 있습니다."));
                    e.setCancelled(true);
                    return;
                }
            }
            chatevent.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
        }
        if(k.startsWith("@")) {
            String msg = k.replace("@", "");
            if(storage.teampvp.containsKey(p.getUniqueId().toString())) {
                e.setCancelled(true);
                jedis.RedisChatSyncQ.put("chat" + ":=:" + p.getUniqueId().toString() + ":=:" + msg, "chat" + ":=:" + p.getUniqueId().toString() + ":=:" + msg);
                return;
                //utils.teamchat(e.getPlayer().getUniqueId().toString(), k);

            } else {
                main.sendmsg(p, "&c&l(!) &7소속된 팀이 없습니다");
            }
        }
        if(commands.teamchat.containsKey(p)) {
            if(storage.teampvp.containsKey(p.getUniqueId().toString())) {
                e.setCancelled(true);

                //utils.teamchat(e.getPlayer().getUniqueId().toString(), k);
                jedis.RedisChatSyncQ.put("chat" + ":=:" + p.getUniqueId().toString() + ":=:" + k, "chat" + ":=:" + p.getUniqueId().toString() + ":=:" + k);
            } else {

                main.sendmsg(p, "&c&l(!) &7소속된 팀이 없어서 팀 채팅에서 강제적으로 퇴장당했습니다");
                commands.teamchat.remove(p);

            }
        } else if(!p.hasPermission("faxcore.chatbypass") && k.length() > 64){
            e.setCancelled(true);
            main.sendmsg(p, "&c&l(!) &f&l메시지가 너무 깁니다. 64자 미만으로 줄여주세요");

        } else {
            try {
                if (Universal.get().getMethods().callChat(e.getPlayer())) {
                    e.setCancelled(true);
                    return;
                }
            } catch (NullPointerException nulle) {
                System.out.println(nulle.getMessage());
            }
            /*try {
                if (main.getInstance().ess.getUser(p.getUniqueId()).isMuted()) {
                    utils.sendmsg(p, "&4&l(!) &f&l당신은 뮤트됬습니다");
                    e.setCancelled(true);
                    return;
                }
            } catch (NullPointerException nulle) {
                System.out.println(nulle.getMessage());
            }*/


            new BukkitRunnable() {
                @Override
                public void run() {
                    if(main.getInstance().chattoggle.equals(true) && !p.hasPermission("faxcore.chatmutebypass")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l(!) &f현재 채팅이 잠겨있습니다"));

                        e.setCancelled(true);
                        return;
                    }
                    Player player = e.getPlayer();
                    String group = loadUser(player).getPrimaryGroup();
                    String message = e.getMessage();
                    String format = (String)Objects.<String>requireNonNull(" %itndevteams_teamname%%itndevteams_rank%{prefix}{name} {suffix}&8:: &r{message}".replace("{world}", player.getWorld().getName()).replace("{prefix}", getPrefix(player)).replace("{prefixes}", getPrefixes(player)).replace("{name}", player.getName()).replace("{suffix}", getSuffix(player)).replace("{suffixes}", getSuffixes(player)).replace("{username-color}", (playerMeta(player).getMetaValue("username-color") != null) ? playerMeta(player).getMetaValue("username-color") : ((groupMeta(group).getMetaValue("username-color") != null) ? groupMeta(group).getMetaValue("username-color") : "")).replace("{message-color}", (playerMeta(player).getMetaValue("message-color") != null) ?
                            playerMeta(player).getMetaValue("message-color") : ((groupMeta(group).getMetaValue("message-color") != null) ?
                            groupMeta(group).getMetaValue("message-color") : "")));
                    ArrayList<Player> closeplayer = new ArrayList<>();
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        closeplayer.add(online);
                        /*if(online.getLocation().getWorld().getName().equals(p.getLocation().getWorld().getName()) && online.getLocation().distance(p.getLocation()) <= 30) {
                            closeplayer.add(online);
                        }*/
                    }
                    String tagonfront = ""; //"&7[ " + String.valueOf(closeplayer.size()) + "명 ]&r";

                    String Dformat = colorize(tagonfront) + colorize(format.replace("{message}", (player.hasPermission("lpc.colorcodes") && player.hasPermission("lpc.rgbcodes")) ?
                            translateHexColorCodes(colorize(message)) : (player.hasPermission("lpc.colorcodes") ? colorize(message) : (player.hasPermission("lpc.rgbcodes") ?
                            translateHexColorCodes(message) : message))).replace("%itndevteams_teamname%", storage.getformattedteamname(p)).replace("%itndevteams_rank%", " " + storage.getrankformattedteamname(p)));
                    for(Player finalonlinecloseplayers : closeplayer) {
                        finalonlinecloseplayers.sendMessage(Dformat);
                    }
                    this.cancel();
                }
            }.runTaskAsynchronously(main.getInstance());
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onleave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(duelcommand.pendingdueltag.containsKey(p)) {
            duelcommand.pendingdueltag.remove(duelcommand.pendingdueltag.get(p));
            duelcommand.pendingdueltag.remove(p);
        }
        if(effectlist.AtkLore.containsKey(p)) {
            effectlist.AtkLore.remove(p);
        }
        if(effectlist.DefLore.containsKey(p)) {
            effectlist.DefLore.remove(p);
        }
        if(duelcommand.isduelinghm.containsKey(e.getPlayer())) {
            if(duelcommand.isduelinghm.get(p).equals(true)) {
                p.setHealth(0D);
            }
        }
        if(duelcommand.duelq1.containsKey(e.getPlayer())) {
            duelcommand.duelq1.remove(e.getPlayer());
            if(duelcommand.duelqtake1.containsKey(duelcommand.duelq1.get(e.getPlayer()))) {
                duelcommand.duelqtake1.get(duelcommand.duelq1.get(e.getPlayer())).remove(e.getPlayer());
            }
            commands.teamchat.remove(e.getPlayer());
        }


    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(duelcommand.isduelinghm.containsKey(p)) {
            Player k = duelcommand.dueler.get(p);
            duelcommand.endduel(p, k, duelcommand.nameofArena.get(p), p.getName(), k.getName());
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent e) {
        if(duelcommand.pendingdueltag.containsKey(e.getPlayer())) {
            Location from = e.getFrom();
            Location to = e.getTo();
            if(from.getZ() != to.getZ()  ||  from.getX() != to.getX()  ||  from.getY() != to.getY()) {
                Player p = e.getPlayer();
                Player duel = duelcommand.pendingdueltag.get(p);
                utils.sendmsg(p, "&4&l(!) &r&f대전장으로 이동되는중에 움직여서 대전이 취소되었습니다");
                utils.sendmsg(duel, "&4&l(!) &r&f상대방이 대전으로 이동되는중에 움직여서 대전이 취소되었습니다");
                duelcommand.pendingdueltag.remove(p);
                duelcommand.pendingdueltag.remove(duel);
            }
        }
    }
    @Deprecated
    public static void setthedoor(Location loc, BlockFace eFace) {



        if(eFace.equals(BlockFace.SOUTH)) {
            BlockData bdata11 = Bukkit.getWorld("build1").getBlockAt(109, 70, 4).getBlockData();
            BlockData bdata12 = Bukkit.getWorld("build1").getBlockAt(109, 71, 4).getBlockData();
            BlockData bdata21 = Bukkit.getWorld("build1").getBlockAt(110, 70, 4).getBlockData();
            BlockData bdata22 = Bukkit.getWorld("build1").getBlockAt(110, 71, 4).getBlockData();
            loc.getBlock().setBlockData(bdata11);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata12);
            loc.add(1, -1, 0).getBlock().setBlockData(bdata21);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata22);
        } else if(eFace.equals(BlockFace.NORTH)) {
            BlockData bdata11 = Bukkit.getWorld("build1").getBlockAt(110, 70, 7).getBlockData();
            BlockData bdata12 = Bukkit.getWorld("build1").getBlockAt(110, 71, 7).getBlockData();
            BlockData bdata21 = Bukkit.getWorld("build1").getBlockAt(109, 70, 7).getBlockData();
            BlockData bdata22 = Bukkit.getWorld("build1").getBlockAt(109, 71, 7).getBlockData();
            loc.getBlock().setBlockData(bdata11);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata12);
            loc.add(-1, -1, 0).getBlock().setBlockData(bdata21);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata22);
        } else if(eFace.equals(BlockFace.WEST)) {
            BlockData bdata11 = Bukkit.getWorld("build1").getBlockAt(111, 70, 5).getBlockData();
            BlockData bdata12 = Bukkit.getWorld("build1").getBlockAt(111, 71, 5).getBlockData();
            BlockData bdata21 = Bukkit.getWorld("build1").getBlockAt(111, 70, 6).getBlockData();
            BlockData bdata22 = Bukkit.getWorld("build1").getBlockAt(111, 71, 6).getBlockData();
            loc.getBlock().setBlockData(bdata11);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata12);
            loc.add(0, -1, 1).getBlock().setBlockData(bdata21);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata22);

        } else if(eFace.equals(BlockFace.EAST)) {
            BlockData bdata11 = Bukkit.getWorld("build1").getBlockAt(108, 70, 6).getBlockData();
            BlockData bdata12 = Bukkit.getWorld("build1").getBlockAt(108, 71, 6).getBlockData();
            BlockData bdata21 = Bukkit.getWorld("build1").getBlockAt(108, 70, 5).getBlockData();
            BlockData bdata22 = Bukkit.getWorld("build1").getBlockAt(108, 71, 5).getBlockData();
            loc.getBlock().setBlockData(bdata11);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata12);
            loc.add(0, -1, -1).getBlock().setBlockData(bdata21);
            loc.add(0, 1, 0).getBlock().setBlockData(bdata22);
        }


    }
    @Deprecated
    public static void doormaker(BlockFace face, Location loc1, Location loc2) {

        if(face.equals(BlockFace.EAST)) {
            Location temploc = loc2;
            temploc.add(-1, -2, 0).getBlock().setType(Material.AIR);
            temploc.add(0, 1, 0).getBlock().setType(Material.AIR);
            setthedoor(loc1.add(-1, -2, 0), face);

            //setthedoor(loc2.add(0, -1, 0), Material.IRON_DOOR, BlockFace.EAST, true, true, false, true, true);

        } else if(face.equals(BlockFace.WEST)) {
            Location temploc = loc2;
            temploc.add(1, -2, 0).getBlock().setType(Material.AIR);
            temploc.add(0, 1, 0).getBlock().setType(Material.AIR);
            setthedoor(loc1.add(1, -2, 0), face);


            //setthedoor(loc2.add(0, -1, 0), Material.IRON_DOOR, BlockFace.WEST, true, true, false, true, true);

        } else if(face.equals(BlockFace.NORTH)) {
            Location temploc = loc2;
            temploc.add(0, -2, 1).getBlock().setType(Material.AIR);
            temploc.add(0, 1, 0).getBlock().setType(Material.AIR);
            setthedoor(loc1.add(0, -2, 1), face);

            //setthedoor(loc2.add(0, -1, 0), Material.IRON_DOOR, BlockFace.NORTH, true, true, false, true, true);

        } else if(face.equals(BlockFace.SOUTH)) {
            Location temploc = loc2;
            temploc.add(0, -2, -1).getBlock().setType(Material.AIR);
            temploc.add(0, 1, 0).getBlock().setType(Material.AIR);
            setthedoor(loc1.add(0, -2, -1), face);

            //setthedoor(loc2.add(0, -1, 0), Material.IRON_DOOR, BlockFace.SOUTH, true, true, false, true, true);



        }


    }
    @Deprecated
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        OfflinePlayer op = (Player) Bukkit.getOfflinePlayer(p.getUniqueId());
        Block block = e.getClickedBlock();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack kitemreal = p.getInventory().getItemInMainHand();
            if(kitemreal.getType() != Material.AIR && kitemreal.hasItemMeta()) {
                NamespacedKey namekey = new NamespacedKey(main.getInstance(), "CLICKTOUSEITEM");
                if(kitemreal.getItemMeta().getDisplayName().contains("새해맞이 황금검")) {
                    if(CustomItemManager.CheckCooldown(p, "newyeargoldensword2022", kitemreal.getItemMeta().getDisplayName(), 120)) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                PotionEffect po1 = new PotionEffect(PotionEffectType.REGENERATION, 400, 2);
                                //PotionEffect po2 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 2);
                                utils.giveTeamEffect(p, 30, po1 ,true);
                                //utils.giveTeamEffect(p, 30, po2 ,true);
                                this.cancel();
                            }
                        }.runTaskAsynchronously(main.getInstance());
                    }
                } else if(kitemreal.getItemMeta().getPersistentDataContainer().has(namekey, PersistentDataType.STRING)) {
                    CustomItemManager.ItemManager(p, kitemreal);
                }
            }


        }
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.OAK_WALL_SIGN){
            Sign sign = (Sign) block.getState();
            Location clicked = e.getClickedBlock().getLocation();
            Location clicked2 = e.getClickedBlock().getLocation();
            if(e.getPlayer() instanceof Player){
                    if(sign.getLine(0).toLowerCase(Locale.ROOT).equals(ChatColor.translateAlternateColorCodes('&', "&4&l[ &8구매 &4&l]"))){
                        if(!p.isSneaking()) {
                            return;
                        }
                        String signinfo = sign.getLine(1);
                        String v = sign.getLine(2);
                        String[] parts = v.split(" ");
                        if(parts.length == 3) {
                            double k = Integer.valueOf(parts[2]);
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) >= k) {
                                main.econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()), k);
                                main.sendmsg(p, "&a&l[&f&l구매&a&l] &r&f성공적으로 잠금을 구매하셨습니다");
                                sign.setLine(0, "[잠금]");
                                sign.setLine(1, p.getName());
                                sign.setLine(2, "/잠금");
                                sign.setLine(3, "");
                                sign.update();
                                if(e.getClickedBlock().getLocation().add(-1, 0, 0).getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                                    Block block1 = e.getClickedBlock().getLocation().add(-1, 0, 0).getBlock();
                                    Sign sign1 = (Sign) block1.getState();
                                    sign1.setLine(0, "[추가 사용자]");
                                    //sign1.setLine(1, "=====");
                                    sign1.update();
                                    doormaker(BlockFace.NORTH, block.getLocation(), block1.getLocation());
                                } else if(e.getClickedBlock().getLocation().add(1, 0, 0).getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                                    Block block2 = e.getClickedBlock().getLocation().add(1, 0, 0).getBlock();
                                    Sign sign2 = (Sign) block2.getState();
                                    sign2.setLine(0, "[추가 사용자]");
                                    //sign2.setLine(1, "=====");
                                    sign2.update();
                                    doormaker(BlockFace.SOUTH, block.getLocation(), block2.getLocation());
                                } else if(e.getClickedBlock().getLocation().add(0, 0, -1).getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                                    Block block3 = e.getClickedBlock().getLocation().add(0, 0, -1).getBlock();
                                    Sign sign3 = (Sign) block3.getState();
                                    sign3.setLine(0, "[추가 사용자]");
                                    //sign3.setLine(1, "=====");
                                    sign3.update();
                                    doormaker(BlockFace.EAST, block.getLocation(), block3.getLocation());
                                } else if(e.getClickedBlock().getLocation().add(0, 0, 1).getBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                                    Block block4 = e.getClickedBlock().getLocation().add(0, 0, 1).getBlock();
                                    Sign sign4 = (Sign) block4.getState();
                                    sign4.setLine(0, "[추가 사용자]");
                                    //sign4.setLine(1, "=====");
                                    sign4.update();
                                    doormaker(BlockFace.WEST, block.getLocation(), block4.getLocation());
                                }
                            } else {
                                main.sendmsg(p, "&a&l[&f&l구매&a&l] &r&f돈이 부족합니다!");
                            }
                        }
                } else if(sign.getLine(0).toLowerCase(Locale.ROOT).equals(ChatColor.translateAlternateColorCodes('&', "&c&l전직"))) {
                        if(sign.getLine(1).contains("Chest")) {
                            if(p.hasPermission("faxcore.chestrank")) {
                                if (!p.hasPermission("faxcore.admin")) {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직을 구매하셨습니다");
                                    return;
                                } else {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직의 권한을 소유하고 있으나 어드민인 관계로 테스트를 감안하여 구매가 진행됩니다");
                                }
                            }
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) <= 25000) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직을 구매하기에는 돈이 부족합니다");
                                return;
                            }
                            main.econ.withdrawPlayer(op, 25000);
                            utils.runcommandfromconsole("lp user " + p.getName() + " parent add chest");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f성공! &6Chest &f랭크로 전직하였습니다.");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f앞으로 /창고 (을)를 사용할 권한이 생깁니다");
                        } else if(sign.getLine(1).contains("Healer")) {
                            if(p.hasPermission("faxcore.healerrank")) {
                                if(!p.hasPermission("faxcore.admin")) {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직을 구매하셨습니다");
                                    return;
                                } else {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직의 권한을 소유하고 있으나 어드민인 관계로 테스트를 감안하여 구매가 진행됩니다");
                                }
                            }
                            if(!p.hasPermission("faxcore.chestrank")) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직 &5Healer &r&f(을)를 구매하려면 먼저 &6Chest &f랭크를 구매해야 합니다");
                                return;
                            }
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) <= 57000) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직을 구매하기에는 돈이 부족합니다");
                                return;
                            }
                            main.econ.withdrawPlayer(op, 57000);
                            utils.runcommandfromconsole("lp user " + p.getName() + " parent add healer");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f성공! &5Healer &f랭크로 전직하였습니다.");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f앞으로 /힐,/회복 (을)를 사용할 권한이 생깁니다");
                        } else if(sign.getLine(1).contains("Legend")) {
                            if(p.hasPermission("faxcore.legendrank")) {
                                if (!p.hasPermission("faxcore.admin")) {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직을 구매하셨습니다");
                                    return;
                                } else {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직의 권한을 소유하고 있으나 어드민인 관계로 테스트를 감안하여 구매가 진행됩니다");
                                }
                            }
                            if(!p.hasPermission("faxcore.healerrank")) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직 &e&LLegend &r&f(을)를 구매하려면 먼저 &5Healer &f랭크를 구매해야 합니다");
                                return;
                            }
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) <= 200000000) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직을 구매하기에는 돈이 부족합니다");
                                return;
                            }
                            main.econ.withdrawPlayer(op, 200000000);
                            utils.runcommandfromconsole("lp user " + p.getName() + " parent add legend");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f성공! &e&lLegend &f랭크로 전직하였습니다.");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&c+ 공격력 &f패시브로 &7+2");
                        } else if(sign.getLine(1).contains("Fax")) {
                            if(p.hasPermission("faxcore.faxrank")) {
                                if (!p.hasPermission("faxcore.admin")) {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직을 구매하셨습니다");
                                    return;
                                } else {
                                    utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f당신은 이미 해당 전직의 권한을 소유하고 있으나 어드민인 관계로 테스트를 감안하여 구매가 진행됩니다");
                                }
                            }
                            if(!p.hasPermission("faxcore.legendrank")) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직 &3&LFax &r&f(을)를 구매하려면 먼저 &e&lLegend &f랭크를 구매해야 합니다");
                                return;
                            }
                            if(main.econ.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId())) <= 700000000) {
                                utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f해당 전직을 구매하기에는 돈이 부족합니다");
                                return;
                            }
                            main.econ.withdrawPlayer(op, 700000000);
                            utils.runcommandfromconsole("lp user " + p.getName() + " parent add fax");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&f성공! &3&lFax &f랭크로 전직하였습니다.");
                            utils.sendmsg(p, "&f&l[ &c&l전직 &f&l] &r&c+ 공격력 &f패시브로 &7+3 (누적됨)");
                        }
                    }
            }
        } else if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK  && block.getType() == Material.OAK_WALL_SIGN) {
                return;
            }
            ItemStack item = p.getInventory().getItemInMainHand();
            if(item.getType().equals(Material.LAPIS_LAZULI) && item.getAmount() == 64) {
                item.setAmount(0);
                utils.executecommandasop(p, "kit lapisblock");
                ItemStack item1 = new ItemStack(Material.LAPIS_BLOCK);
                ItemMeta meta = item1.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b[ &f결정 &b] &3청금석 뭉치"));
                item1.setItemMeta(meta);
                item1.addUnsafeEnchantment(Enchantment.LUCK, 1);
                p.getInventory().addItem(item1);
            } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().toLowerCase(Locale.ROOT).contains("복권")) {
                item.setAmount(item.getAmount() - 1);
                utils.lottery(p);
            }
        }
    }
    @Deprecated
    @EventHandler(ignoreCancelled = true)
    public void onclick(InventoryClickEvent e) {
        if(e.getView().getTitle().contains("님의 인벤토리")) {
            main.sendmsg((Player) e.getWhoClicked(), "&c&l(!) &7상대방의 정보를 보고 있는 도중에는 해당 엑션을 취하실수 없습니다");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }

    //uuidname nameuuid
    public static void onSaveuuidnameData() {
        for (Map.Entry<String, String> entry : listener.uuidname.entrySet())
            listener.getStorage().set("uuidname." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestoreuuidnameData() {
        listener.getStorage().getConfigurationSection("uuidname.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("uuidname." + key).toString();
            listener.uuidname.put(key, v);
        });
    }
    public static void onSavenameuuidData() {
        for (Map.Entry<String, String> entry : listener.nameuuid.entrySet())
            listener.getStorage().set("nameuuid." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestorenameuuidData() {
        listener.getStorage().getConfigurationSection("nameuuid.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("nameuuid." + key).toString();
            listener.nameuuid.put(key, v);
        });
    }
    public static void onSavenamenameData() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            listener.getStorage().set("namename." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestorenamenameData() {
        listener.getStorage().getConfigurationSection("namename.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("namename." + key).toString();
            listener.namename.put(key, v);
        });
    }


    //house info ================================
    /*
    public static void onSavehouseinfo() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            listener.getStorage().set("houseinfo." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestorehouseinfo() {
        listener.getStorage().getConfigurationSection("houseinfo.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("houseinfo." + key).toString();
            listener.namename.put(key, v);
        });
    }
    public static void onSavereversehouseinfo() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            listener.getStorage().set("namename." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestorereversehouseinfo() {
        listener.getStorage().getConfigurationSection("namename.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("namename." + key).toString();
            listener.namename.put(key, v);
        });
    }
    public static void onSaveoriginhouseinfo() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            listener.getStorage().set("namename." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestoreoriginhouseinfo() {
        listener.getStorage().getConfigurationSection("namename.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("namename." + key).toString();
            listener.namename.put(key, v);
        });
    }
    public static void onSaveoriginreversehouseinfo() {
        for (Map.Entry<String, String> entry : listener.namename.entrySet())
            listener.getStorage().set("namename." + (String)entry.getKey(), entry.getValue());
        //listener.saveStorage();
    }
    public static void onRestoreoriginreversehouseinfo() {
        listener.getStorage().getConfigurationSection("namename.").getKeys(false).forEach(key -> {
            String v = listener.getStorage().get("namename." + key).toString();
            listener.namename.put(key, v);
        });
    }
    */

    //house info ================================

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        char colorChar = '§';
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "§x§" + group
                    .charAt(0) + '§' + group.charAt(1) + '§' + group
                    .charAt(2) + '§' + group.charAt(3) + '§' + group
                    .charAt(4) + '§' + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }

    private String getPrefix(Player player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    private String getSuffix(Player player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    private String getPrefixes(Player player) {
        SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        StringBuilder prefixes = new StringBuilder();
        for (String prefix : map.values())
            prefixes.append(prefix);
        return prefixes.toString();
    }

    private String getSuffixes(Player player) {
        SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        StringBuilder suffixes = new StringBuilder();
        for (String prefix : map.values())
            suffixes.append(prefix);
        return suffixes.toString();
    }

    private CachedMetaData playerMeta(Player player) {
        return loadUser(player).getCachedData().getMetaData(getApi().getContextManager().getQueryOptions(player));
    }

    private CachedMetaData groupMeta(String group) {
        return loadGroup(group).getCachedData().getMetaData(getApi().getContextManager().getStaticQueryOptions());
    }

    private User loadUser(Player player) {
        if (!player.isOnline())
            throw new IllegalStateException("Player is offline!");
        return getApi().getUserManager().getUser(player.getUniqueId());
    }

    private Group loadGroup(String group) {
        return getApi().getGroupManager().getGroup(group);
    }

    private LuckPerms getApi() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServer().getServicesManager().getRegistration(LuckPerms.class);
        Validate.notNull(provider);
        return (LuckPerms)provider.getProvider();
    }

    private boolean isPlaceholderAPIEnabled() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }
    @EventHandler(ignoreCancelled = true)
    public void onCommandPreProscess(PlayerCommandPreprocessEvent e) {
        packetadapt.onPlayerCommandExecute(e);
    }


}
