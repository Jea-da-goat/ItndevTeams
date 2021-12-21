package com.itndev.teams.EffectManager;

import com.itndev.teams.effectlist;
import com.itndev.teams.listener;
import com.itndev.teams.main;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlterniveLoreRegister {


    @Deprecated
    public static void LoopTask() {

        new BukkitRunnable() {
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()) {
                    List<String> wepdata = listener.getLore(p.getInventory().getItemInMainHand());
                    List<String> helmdata = listener.getLore(p.getInventory().getHelmet());
                    List<String> chesdata = listener.getLore(p.getInventory().getChestplate());
                    List<String> legdata = listener.getLore(p.getInventory().getLeggings());
                    List<String> bootdata = listener.getLore(p.getInventory().getBoots());
                    AlterRegisterTask(p, wepdata, helmdata, chesdata, legdata, bootdata);
                }

            }
        }.runTaskTimerAsynchronously(main.getInstance(), 2L, 2L);

    }

    public static void AlterRegisterTask(Player p, List<String> wepdata, List<String> helmdata, List<String> chesdata, List<String> legdata, List<String> bootdata) {


        ArrayList<PotionEffect> effect2apply = new ArrayList<>();
        ArrayList<PotionEffect> finaleffects2apply = new ArrayList<>();
        HashMap<PotionEffectType, Integer> finaltypes = new HashMap<>();

        double wepatk = 0;
        double wepdef = 0;
        double wephealth = 0;

        double helmatk = 0;
        double helmdef = 0;
        double helmhealth = 0;

        double chesatk = 0;
        double chesdef = 0;
        double cheshealth = 0;

        double legatk = 0;
        double legdef = 0;
        double leghealth = 0;

        double bootatk = 0;
        double bootdef = 0;
        double boothealth = 0;

        //effectlist.getPotionEffect()
        if(wepdata != null && !wepdata.isEmpty()) {
            if(!effectlist.getPotionEffect(wepdata).isEmpty()) {
                effect2apply.addAll(effectlist.getPotionEffect(wepdata));
            }
            wepatk = effectlist.getAtkamount(wepdata);
            wepdef = effectlist.getDefamount(wepdata);
            wephealth = effectlist.getHealthamount(wepdata);
        }
        //effecttypes.addAll(effectlist333.get(wepdata));
        if(helmdata != null && !helmdata.isEmpty()) {
            if(!effectlist.getPotionEffect(helmdata).isEmpty()) {
                effect2apply.addAll(effectlist.getPotionEffect(helmdata));
            }
            helmatk = effectlist.getAtkamount(helmdata);
            helmdef = effectlist.getDefamount(helmdata);
            helmhealth = effectlist.getHealthamount(helmdata);
        }
        //effecttypes.addAll(effectlist333.get(helmdata));
        if(chesdata != null && !chesdata.isEmpty()) {
            if(!effectlist.getPotionEffect(chesdata).isEmpty()) {
                effect2apply.addAll(effectlist.getPotionEffect(chesdata));
            }
            chesatk = effectlist.getAtkamount(chesdata);
            chesdef = effectlist.getDefamount(chesdata);
            cheshealth = effectlist.getHealthamount(chesdata);
        }
        //effecttypes.addAll(effectlist333.get(chesdata));
        if(legdata != null && !legdata.isEmpty()) {
            if(!effectlist.getPotionEffect(legdata).isEmpty()) {
                effect2apply.addAll(effectlist.getPotionEffect(legdata));

            }
            legatk = effectlist.getAtkamount(legdata);
            legdef = effectlist.getDefamount(legdata);
            leghealth = effectlist.getHealthamount(legdata);
        }
        //effecttypes.addAll(effectlist333.get(legdata));
        if(bootdata != null && !bootdata.isEmpty()) {
            if(!effectlist.getPotionEffect(bootdata).isEmpty()) {
                effect2apply.addAll(effectlist.getPotionEffect(bootdata));
            }
            bootatk = effectlist.getAtkamount(bootdata);
            bootdef = effectlist.getDefamount(bootdata);
            boothealth = effectlist.getHealthamount(bootdata);
        }
        double buffatk = 0;
        double buffdef = 0;
        double buffhealth = 0;

        //soon changing to a better perm system... rn its shit asf
        if(p.hasPermission("faxcore.legendrank")) {
            buffatk = buffatk + 2;
        }
        if(p.hasPermission("faxcore.faxrank")) {
            buffdef = buffdef + 2;
        }
        double permatk = effectlist.getPermissionAtk(p);
        double permdef = effectlist.getPermissionDef(p);
        double permhealth = effectlist.getPermissionHealth(p);
        effectlist.AtkLore.put(p, wepatk + helmatk + chesatk + legatk + bootatk + buffatk + permatk);

        effectlist.DefLore.put(p, wepdef + helmdef + chesdef + legdef + bootdef + buffdef + permdef);
        double finalHealthmax = wephealth + helmhealth + cheshealth + leghealth + boothealth + buffhealth + permhealth;
        //effecttypes.addAll(effectlist333.get(bootdata));
        for(PotionEffect cacu : effect2apply) {
            if(finaltypes.containsKey(cacu.getType())) {
                if(cacu.getAmplifier() > finaltypes.get(cacu.getType())) {
                    finaltypes.put(cacu.getType(), cacu.getAmplifier());
                    finaleffects2apply.add(cacu);
                            /*for(PotionEffect po : finaleffects2apply) {
                                if(po.getType().equals(cacu.getType())) {
                                    finaleffects2apply.remove(po);
                                }

                            }*/

                }
            } else {
                finaleffects2apply.add(cacu);
                finaltypes.put(cacu.getType(), cacu.getAmplifier());
            }
        }


        ArrayList<PotionEffect> smartregisterlist = new ArrayList<>();
        ArrayList<PotionEffectType> smartremovelist = new ArrayList<>();
        for(PotionEffect po : finaleffects2apply) {
            if(p.hasPotionEffect(po.getType())) {
                if(p.getPotionEffect(po.getType()).getAmplifier() < po.getAmplifier()) {
                    smartregisterlist.add(po);
                }
            } else {
                smartregisterlist.add(po);
            }
        }
        for(PotionEffect po : p.getActivePotionEffects()) {
            if(!finaltypes.containsKey(po.getType())) {
                if(po.getDuration() > 36000) {
                    smartremovelist.add(po.getType());
                }
            }
        }
        new BukkitRunnable() {
            public void run() {
                for(PotionEffectType potype : smartremovelist) {
                    p.removePotionEffect(potype);
                }
                p.addPotionEffects(smartregisterlist);
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D + finalHealthmax);
            }
        }.runTask(main.getInstance());
    }
}
