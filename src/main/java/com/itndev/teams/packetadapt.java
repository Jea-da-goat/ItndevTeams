package com.itndev.teams;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Locale;

public class packetadapt extends PacketAdapter {
    public static HashMap<String, Boolean> allowedcommands = new HashMap<>();
    public packetadapt(Plugin plugin, PacketType type) {
        super(plugin, type);
    }

    public static void Setupallowedcommands() {
        allowedcommands.put("팀", true);
        allowedcommands.put("대전", true);
        allowedcommands.put("메뉴", true);
        allowedcommands.put("명령어", true);
        allowedcommands.put("워프", true);
        allowedcommands.put("잠금", true);
        allowedcommands.put("장사글", true);
        allowedcommands.put("gwarp", true);
        allowedcommands.put("힐", true);
        allowedcommands.put("glf", true);
        allowedcommands.put("heal", true);
        allowedcommands.put("수리", true);
        allowedcommands.put("fix", true);
        allowedcommands.put("tnfl", true);
        allowedcommands.put("밥", true);
        allowedcommands.put("qkq", true);
        allowedcommands.put("feed", true);
        allowedcommands.put("배고픔", true);
        allowedcommands.put("qorhvma", true);
        allowedcommands.put("정보", true);
        allowedcommands.put("도움말", true);
        allowedcommands.put("스폰", true);
        allowedcommands.put("spawn", true);
        allowedcommands.put("test111", true);
        allowedcommands.put("auto", true);
        //allowedcommands.put("잠금", true);
        allowedcommands.put("창고", true);
        allowedcommands.put("bp", true);
        allowedcommands.put("힐러", true);
        allowedcommands.put("기본템", true);
        //관리자 명령어 일부
        allowedcommands.put("핫타임", true);
        allowedcommands.put("뮤트", true);
        allowedcommands.put("추첨시작", true);
        allowedcommands.put("exchange", true);

        //돈 관련 명령어
        allowedcommands.put("돈", true);
        allowedcommands.put("pay", true);
        allowedcommands.put("baltop", true);
        allowedcommands.put("balancetop", true);
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        PacketType packetType = e.getPacketType();
        if (packetType.equals(PacketType.Play.Client.TAB_COMPLETE)) {
            if(e.getPlayer().hasPermission("faxcore.tabblocker.bypass")) {
                return;
            }
            //e.setCancelled(true);
            PacketContainer pack = e.getPacket();
            //e.setCancelled(true);
            String message = ((String)pack.getSpecificModifier(String.class).read(0));
            if(message.equals("/") || message.contains(":")) {
                e.setCancelled(true);
                return;
                //e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l병신새끼"));
            }
            if(message.startsWith("/")) {
                if(!allowedcommands.keySet().stream().anyMatch(s -> message.toLowerCase(Locale.ROOT).startsWith("/" + s.toLowerCase()))) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

    }
    public static void onPlayerCommandExecute(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        if(p.hasPermission("faxcore.commandblocker.bypass")) {
            return;
        }
        if(message.startsWith("/")) {
            if(duelcommand.isduelinghm.containsKey(e.getPlayer()) && duelcommand.isduelinghm.get(e.getPlayer())) {
                if(!(message.equalsIgnoreCase("/glf") || message.equalsIgnoreCase("/heal") || message.equalsIgnoreCase("/힐") || message.equalsIgnoreCase("/fix")
                        || message.equalsIgnoreCase("/수리") || message.equalsIgnoreCase("/tnfl"))) {
                    e.setCancelled(true);
                }
            }
            if(message.contains(":")) {
                utils.sendmsg(p, "&c&l(!) &f해당 명령어는 사용이 불가능하거나 존재하지 않는 명령어입니다");
                e.setCancelled(true);
                return;
            }
            if(!allowedcommands.keySet().stream().anyMatch(s -> message.toLowerCase().startsWith("/" + s.toLowerCase()))) {
                utils.sendmsg(p, "&c&l(!) &f해당 명령어는 사용이 불가능하거나 존재하지 않는 명령어입니다");
                e.setCancelled(true);
                return;

            }
        }
        if(message.equalsIgnoreCase("/spawn") || message.toLowerCase(Locale.ROOT).startsWith("/spawn")) {
            utils.sendmsg(p, "&3&l[ &f시스템 &3&l] &c5초&f후 스폰으로 이동됩니다");
            return;
        }


        if(message.equals("/bp") || message.toLowerCase(Locale.ROOT).startsWith("/bp")) {
            if(!p.hasPermission("faxcore.chestrank")) {
                utils.sendmsg(p, "&f[ &c&l전직 &f] &6Chest &f랭크를 사야 해당 기능을 이용할수 있습니다 . 핫플레이스의 스폰지역에서 전직을 하실수 있습니다");
                e.setCancelled(true);
                return;
            }
        }



    }
}

