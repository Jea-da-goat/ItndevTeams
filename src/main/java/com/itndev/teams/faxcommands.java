package com.itndev.teams;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class faxcommands {
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
        }

    }
}
