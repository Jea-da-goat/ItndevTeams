package com.itndev.teams;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class faxcommands {

    public static Boolean MuteChat = false;

    public static Boolean PVPtoggle = true;

    @Deprecated
    public static void faxcommandexecute(Player p, String[] args) {
        if(!p.hasPermission("faxcore.usefaxcommand")) {
            utils.sendmsg(p, "&c&l(!) &r&f권한이 없습니다");
            return;
        }
        if(args.length < 1) {
            return;
        }
        if(args[0].equalsIgnoreCase("convertitems")) {
            utils.sendmsg(p, "&c&l(!) &r&f베타기능) 아이템 수동 오류 해결 시도 완료");
            for(ItemStack item : p.getInventory().getContents()) {
                if(item != null) {
                    if(item.hasItemMeta()) {
                        ItemMeta tempmeta = item.getItemMeta();
                        tempmeta.setDisplayName(tempmeta.getDisplayName().toString());
                        tempmeta.setLore(tempmeta.getLore());
                        item.setItemMeta(tempmeta);
                    }
                }
            }
        } else if(args[0].equalsIgnoreCase("checkissame")) {

            ItemStack item1 = p.getInventory().getItemInMainHand();
            ItemStack item2 = p.getInventory().getItemInOffHand();
            if(item1.isSimilar(item2));
            utils.sendmsg(p, "&c&lCHECK &7같은 아이템인지 여부 : &f" + String.valueOf(item1.isSimilar(item2)));

        } else if(args[0].equalsIgnoreCase("chatmutetoggle")) {
            if(main.getInstance().chattoggle.equals(true)) {
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f채팅얼림을 멈췄습니다");
                main.getInstance().chattoggle = false;
            } else if(main.getInstance().chattoggle.equals(false)){
                utils.sendmsg(p, "&3&l[ &f&l시스템 &3&l] &f채팅을 얼렸습니다");
                main.getInstance().chattoggle = true;
            }
        } else if(args[0].equalsIgnoreCase("pvptoggle")) {
            PVPtoggle = !PVPtoggle;
            utils.sendmsg(p, "&c&lTOGGLE &7PVP가능 여부 : &f" + String.valueOf(PVPtoggle));
        }
    }
}
