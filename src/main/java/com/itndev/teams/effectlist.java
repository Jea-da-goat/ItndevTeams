package com.itndev.teams;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class effectlist {

    //public static HashMap<String, PotionEffectType> potioneffect = new HashMap<>();
    public static ConcurrentHashMap<Integer, ArrayList<PotionEffect>> effectlist222 = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Player, Double> AtkLore = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Player, Double> DefLore = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ArrayList<PotionEffect>> stringbasedeffectlist = new ConcurrentHashMap<>();
    public static ArrayList<String> effectnames = new ArrayList<>();
    //public static HashMap<Integer, ArrayList<PotionEffectType>> effectlist333 = new HashMap<>();

    public static void effectlistsetup() {
        /*potioneffect.put("speed", PotionEffectType.SPEED);
        potioneffect.put("haste", PotionEffectType.FAST_DIGGING);
        potioneffect.put("resist", PotionEffectType.DAMAGE_RESISTANCE);
        potioneffect.put("regen", PotionEffectType.REGENERATION);
        potioneffect.put("strength", PotionEffectType.INCREASE_DAMAGE);
        potioneffect.put("jump", PotionEffectType.JUMP);
        potioneffect.put("fireres", PotionEffectType.FIRE_RESISTANCE);*/
        //potioneffect.put("", PotionEffectType.);
        //effectlist222.put(1020202, )
        /*setupXD(1029232, "speed-2");
        setupXD(1022452, "speed-1=regen-3");
        setupXD(2393943, "haste-1=resist-2=speed-2=jump-1");*/


        //regen
        PotionEffect regen1 = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0);
        PotionEffect regen2 = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1);
        PotionEffect regen3 = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2);

        //speed
        PotionEffect speed1 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0);
        PotionEffect speed2 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
        PotionEffect speed3 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2);

        //res
        PotionEffect res1 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0);
        PotionEffect res2 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1);
        PotionEffect res3 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2);

        //fireres
        PotionEffect fireres1 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0);
        PotionEffect fireres2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1);
        PotionEffect fireres3 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 2);

        //strength
        PotionEffect strength1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0);
        PotionEffect strength2 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
        PotionEffect strength3 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2);

        //jump
        PotionEffect jump1 = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0);
        PotionEffect jump2 = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1);
        PotionEffect jump3 = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2);

        //haste
        PotionEffect haste1 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0);
        PotionEffect haste2 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
        PotionEffect haste3 = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2);


        ArrayList<PotionEffect> temp1 = new ArrayList<>();
        temp1.add(speed1);
        //temp1.add(regen2);
        //temp1.add(jump1);
        effectlist.effectlist222.put(1105010, temp1);
        ArrayList<PotionEffect> temp11 = new ArrayList<>();
        temp11.add(speed2);
        //temp1.add(regen2);
        //temp1.add(jump1);
        effectlist.effectlist222.put(1105011, temp11);
        ArrayList<PotionEffect> temp12 = new ArrayList<>();
        temp12.add(speed3);
        //temp1.add(regen2);
        //temp1.add(jump1);
        effectlist.effectlist222.put(1105012, temp12);


        ArrayList<PotionEffect> temp2 = new ArrayList<>();
        temp2.add(regen2);
        //temp2.add(haste2);
        //temp2.add(jump1);
        effectlist.effectlist222.put(1105020, temp2);
        ArrayList<PotionEffect> temp21 = new ArrayList<>();
        temp21.add(regen2);
        //temp2.add(haste2);
        //temp2.add(jump1);
        effectlist.effectlist222.put(1105021, temp21);

        ArrayList<PotionEffect> temp3 = new ArrayList<>();
        //temp3.add(regen1);
        temp3.add(strength1);
        //temp3.add(jump1);
        effectlist.effectlist222.put(1105030, temp3);
        ArrayList<PotionEffect> temp31 = new ArrayList<>();
        //temp3.add(regen1);
        temp31.add(strength2);
        //temp3.add(jump1);
        effectlist.effectlist222.put(1105031, temp31);




        String health = "재생";
        String speed = "신속";
        String res = "저항*";
        String fireres = "화염저항";
        String haste = "성급함";
        String strngth = "힘";
        String jump = "점프강화";

        effectnames.add(health);
        effectnames.add(speed);
        effectnames.add(res);
        effectnames.add(fireres);
        effectnames.add(haste);
        effectnames.add(strngth);
        effectnames.add(jump);


    }
    public static PotionEffectType getpotioneffecttypebyname(String potioneffecttypename) {
        if(potioneffecttypename != null) {
            if(potioneffecttypename.equalsIgnoreCase("재생")) {
                return PotionEffectType.REGENERATION;
            } else if(potioneffecttypename.equalsIgnoreCase("신속")) {
                return PotionEffectType.SPEED;
            } else if(potioneffecttypename.equalsIgnoreCase("저항*")) {
                return PotionEffectType.DAMAGE_RESISTANCE;
            } else if(potioneffecttypename.equalsIgnoreCase("화염저항")) {
                return PotionEffectType.FIRE_RESISTANCE;
            } else if(potioneffecttypename.equalsIgnoreCase("성급함")) {
                return PotionEffectType.FAST_DIGGING;
            } else if(potioneffecttypename.equalsIgnoreCase("점프강화")) {
                return PotionEffectType.JUMP;
            } else if(potioneffecttypename.equalsIgnoreCase("힘")) {
                return PotionEffectType.INCREASE_DAMAGE;
           }
        }
        return null;

    }
    /*public static void setupXD(Integer custommodeldata, String po1) {
        ArrayList<String> po = new ArrayList<>();
        if(po1.contains("=")) {
            String[] poparts = po1.split("=");
            for (String k : poparts) {
                po.add(k);

            }
        } else {
            po.add(po1);
        }
        effectlist222.put(custommodeldata, po);
    }*/
    /*
    public static void givepotioneffect(Player p, String potionname, Integer potionstrength, Integer duration) {
        //if duration is -1 set it to max
        PotionEffectType potioneffect2give = getpotioneffect(potionname);
        if(duration.equals(-1)) {
            if(potioneffect2give != null) {
                PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, potionstrength - 1);
                p.addPotionEffect(effect);
            }
        } else {
            if(potioneffect2give != null) {
                PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, duration, potionstrength - 1);
                p.addPotionEffect(effect);
            }
        }


    }
    public static PotionEffectType getpotioneffect(String potionname) {
        if(potioneffect.containsKey(potionname)) {
            return potioneffect.get(potionname);
        } else {
            return null;
        }

    }*/
    public static ArrayList<PotionEffect> getPotionEffect(List<String> potioneffectnamelist) {
        ArrayList<PotionEffect> finaleffects = new ArrayList<>();
        for(String effectname : effectnames) {
            for(String potioneffectname : potioneffectnamelist) {
                if(potioneffectname.contains("착용시")) {
                    if (potioneffectname.contains(effectname)) {
                        if (getpotioneffecttypebyname(effectname) != null) {
                            PotionEffectType effecttype = getpotioneffecttypebyname(effectname);
                            for(int x = 1; x <= 255; x++) {
                                int newx = 256 - x;
                                if (potioneffectname.contains(effectname + " " + newx)) {
                                    finaleffects.add(new PotionEffect(effecttype, Integer.MAX_VALUE, newx - 1));
                                    break;
                                }
                            }
                            /*if (potioneffectname.contains(effectname + " 1")) {
                                finaleffects.add(new PotionEffect(effecttype, Integer.MAX_VALUE, 0));
                            } else if (potioneffectname.contains(effectname + " 2")) {
                                finaleffects.add(new PotionEffect(effecttype, Integer.MAX_VALUE, 1));
                            } else if (potioneffectname.contains(effectname + " 3")) {
                                finaleffects.add(new PotionEffect(effecttype, Integer.MAX_VALUE, 2));
                            } else if (potioneffectname.contains(effectname + " 4")) {
                                finaleffects.add(new PotionEffect(effecttype, Integer.MAX_VALUE, 3));
                            }*/
                        }
                    }
                }
            }
        }
        return finaleffects;
    }
    public static double getDefamount(List<String> lores) {
        double finaldef = 0;
        for(String lore : lores) {
            if (lore.contains("착용시") && lore.contains("방어력 +")) {
                for (int x = 1; x <= 100; x++) {
                    if (lore.contains("방어력 +" + String.valueOf(100 - x))) {
                        finaldef = 100 - x;
                        break;
                    }
                }
            }
        }
        return finaldef;
    }
    public static double getAtkamount(List<String> lores) {
        double finalatk = 0;
        for(String lore : lores) {

            if (lore.contains("착용시") && lore.contains("공격력 +")) {
                for (int x = 0; x <= 100; x++) {
                    if (lore.contains("공격력 +" + String.valueOf(100 - x))) {
                        finalatk = 100 - x;
                        break;
                    }
                }
            }
        }
        return finalatk;
    }
    public static double getHealthamount(List<String> lores) {
        double finalhealth = 0;
        for(String lore : lores) {

            if (lore.contains("착용시") && lore.contains("생명력 +")) {
                for (int x = 0; x <= 100; x++) {
                    if (lore.contains("생명력 +" + String.valueOf(100 - x))) {
                        finalhealth = 100 - x;
                        break;
                    }
                }
            }
        }
        return finalhealth;
    }
    public static double getPermissionDef(Player p) {
        double permDef = 0;
        if(main.getInstance().ShouldUsePermLore.equals(false)) {
            return 0;
        }
        for (int x = 0; x <= 100; x++) {
            if (p.hasPermission("faxcore.permDef." + String.valueOf(100 - x))) {
                permDef = 100 - x;
                break;
            }
        }
        return permDef;
    }
    public static double getPermissionAtk(Player p) {
        double permAtk = 0;
        if(main.getInstance().ShouldUsePermLore.equals(false)) {
            return 0;
        }
        for (int x = 0; x <= 100; x++) {
            if (p.hasPermission("faxcore.permatk." + String.valueOf(100 - x))) {
                permAtk = 100 - x;
                break;
            }
        }
        return permAtk;
    }
    public static double getPermissionHealth(Player p) {
        double permHealth = 0;
        if(main.getInstance().ShouldUsePermLore.equals(false)) {
            return 0;
        }
        for (int x = 0; x <= 100; x++) {
            if (p.hasPermission("faxcore.permhealth." + String.valueOf(100 - x))) {
                permHealth = 100 - x;
                break;
            }
        }
        return permHealth;
    }
    public static void ApplyEffectsLoreBased(Player p, List<String> wepdata, List<String> helmdata, List<String> chesdata, List<String> legdata, List<String> bootdata) {
        ArrayList<PotionEffect> effect2apply = new ArrayList<>();
        ArrayList<PotionEffect> finaleffects2apply = new ArrayList<>();
        HashMap<PotionEffectType, Integer> finaltypes = new HashMap<>();


        new BukkitRunnable() {

            @Override
            public void run() {
                double wepatk = 0; //공방채 -
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

                if(wepdata != null && !wepdata.isEmpty()) {
                    if(!getPotionEffect(wepdata).isEmpty()) {
                        effect2apply.addAll(getPotionEffect(wepdata));
                    }
                    wepatk = getAtkamount(wepdata);
                    wepdef = getDefamount(wepdata);
                    wephealth = getHealthamount(wepdata);
                }
                //effecttypes.addAll(effectlist333.get(wepdata));
                if(helmdata != null && !helmdata.isEmpty()) {
                    if(!getPotionEffect(helmdata).isEmpty()) {
                        effect2apply.addAll(getPotionEffect(helmdata));
                    }
                    helmatk = getAtkamount(helmdata);
                    helmdef = getDefamount(helmdata);
                    helmhealth = getHealthamount(helmdata);
                }
                //effecttypes.addAll(effectlist333.get(helmdata));
                if(chesdata != null && !chesdata.isEmpty()) {
                    if(!getPotionEffect(chesdata).isEmpty()) {
                        effect2apply.addAll(getPotionEffect(chesdata));
                    }
                    chesatk = getAtkamount(chesdata);
                    chesdef = getDefamount(chesdata);
                    cheshealth = getHealthamount(chesdata);
                }
                //effecttypes.addAll(effectlist333.get(chesdata));
                if(legdata != null && !legdata.isEmpty()) {
                    if(!getPotionEffect(legdata).isEmpty()) {
                        effect2apply.addAll(getPotionEffect(legdata));

                    }
                    legatk = getAtkamount(legdata);
                    legdef = getDefamount(legdata);
                    leghealth = getHealthamount(legdata);
                }
                //effecttypes.addAll(effectlist333.get(legdata));
                if(bootdata != null && !bootdata.isEmpty()) {
                    if(!getPotionEffect(bootdata).isEmpty()) {
                        effect2apply.addAll(getPotionEffect(bootdata));
                    }
                    bootatk = getAtkamount(bootdata);
                    bootdef = getDefamount(bootdata);
                    boothealth = getHealthamount(bootdata);
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
                double permatk = getPermissionAtk(p);
                double permdef = getPermissionDef(p);
                double permhealth = getPermissionHealth(p);
                AtkLore.put(p, wepatk + helmatk + chesatk + legatk + bootatk + buffatk + permatk);

                DefLore.put(p, wepdef + helmdef + chesdef + legdef + bootdef + buffdef + permdef);
                double finalHealthmax = wephealth + helmhealth + cheshealth + leghealth + boothealth + buffhealth + permhealth;
                //effecttypes.addAll(effectlist333.get(bootdata));
                for(PotionEffect cacu : effect2apply) {
                    if(finaltypes.containsKey(cacu.getType())) {
                        if(cacu.getAmplifier() > finaltypes.get(cacu.getType())) {
                            finaltypes.put(cacu.getType(), cacu.getAmplifier());
                            finaleffects2apply.add(cacu);
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
                        for(PotionEffect po2 : p.getActivePotionEffects()) {
                            if(po2.getType().equals(po.getType())) {
                                if(po2.getDuration() > 36000) {
                                    if(po2.getAmplifier() != po.getAmplifier()) {
                                        smartregisterlist.add(po);
                                    }
                                }
                            }
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
                    } else {
                        if(finaltypes.get(po.getType()) < po.getAmplifier() && po.getDuration() > 36000) {
                            smartremovelist.add(po.getType());
                        }
                        /*for(PotionEffect po2 : smartregisterlist) {
                            if(po.getType() == po2.getType()) {
                                if(po.getAmplifier() > po2.getAmplifier() && po.getDuration() > 36000) {
                                    smartremovelist.add(po.getType());
                                }
                            }
                        }*/
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


                /*new BukkitRunnable() {
                    @Override
                    public void run() {
                        effectlist.RemovePotionEffects(p);
                        p.sendMessage("12-1");
                        if(effectlist222.isEmpty()) {
                            p.sendMessage("Effeclist222 is empty");
                        }
                        for(int d : effectlist222.keySet()) {
                            for(PotionEffect po : effectlist222.get(d)) {
                                p.sendMessage(String.valueOf(d) + " : " + po.getType().getName());
                            }

                        }
                        for(PotionEffect d1 : effect2apply) {
                            p.sendMessage("(effect2apply) : " + d1.getType().getName());
                        }
                        for(PotionEffect d1 : finaleffects2apply) {
                            p.sendMessage("(finaleffects2apply) : " + d1.getType().getName());
                        }
                        //p.sendMessage("(finaleffects2apply) : " + finaleffects2apply.toString());
                        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D + finalHealthmax);
                        for(PotionEffect po : finaleffects2apply) {
                            givepotioneffects(p, po);
                        }
                    }
                }.runTask(main.getInstance());*/







                this.cancel();
            }
        }.runTaskAsynchronously(main.getInstance());
    }
    public static void ApplyEffects(Player p, Integer wepdata, Integer helmdata, Integer chesdata, Integer legdata, Integer bootdata) {
        //p.sendMessage("12-0");
        ArrayList<PotionEffect> effect2apply = new ArrayList<>();
        ArrayList<PotionEffect> finaleffects2apply = new ArrayList<>();
        HashMap<PotionEffectType, Integer> finaltypes = new HashMap<>();
        new BukkitRunnable() {

            @Override
            public void run() {
                //Collection<PotionEffect> po = p.getActivePotionEffects();

                //ArrayList<PotionEffectType> effecttypes = new ArrayList<>();
                if(wepdata != 0 && wepdata != null) {
                    effect2apply.addAll(effectlist222.get(wepdata));
                }
                //effecttypes.addAll(effectlist333.get(wepdata));
                if(helmdata != 0 && helmdata != null) {
                    effect2apply.addAll(effectlist222.get(helmdata));
                }
                //effecttypes.addAll(effectlist333.get(helmdata));
                if(chesdata != 0 && chesdata != null) {
                    effect2apply.addAll(effectlist222.get(chesdata));
                }
                //effecttypes.addAll(effectlist333.get(chesdata));
                if(legdata != 0 && legdata != null) {
                    effect2apply.addAll(effectlist222.get(legdata));
                }
                //effecttypes.addAll(effectlist333.get(legdata));
                if(bootdata != 0 && bootdata != null) {
                    effect2apply.addAll(effectlist222.get(bootdata));
                }
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
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        //effectlist.RemovePotionEffects(p);
                        /*p.sendMessage("12-1");
                        if(effectlist222.isEmpty()) {
                            p.sendMessage("Effeclist222 is empty");
                        }
                        for(int d : effectlist222.keySet()) {
                            for(PotionEffect po : effectlist222.get(d)) {
                                p.sendMessage(String.valueOf(d) + " : " + po.getType().getName());
                            }

                        }
                        for(PotionEffect d1 : effect2apply) {
                            p.sendMessage("(effect2apply) : " + d1.getType().getName());
                        }
                        for(PotionEffect d1 : finaleffects2apply) {
                            p.sendMessage("(finaleffects2apply) : " + d1.getType().getName());
                        }*/
                        //p.sendMessage("(finaleffects2apply) : " + finaleffects2apply.toString());
                        for(PotionEffect po : finaleffects2apply) {
                            givepotioneffects(p, po);
                        }
                    }
                }.runTask(main.getInstance());

                this.cancel();

            }
        }.runTaskAsynchronously(main.getInstance());


    }

    public static void RemovePotionEffects(Player p) {
        //p.sendMessage("repoeff");
        for(PotionEffect po : p.getActivePotionEffects()) {
            if(po.getDuration() > 36000) {
                p.removePotionEffect(po.getType());
                //p.sendMessage(po.getType().getName() + " removed");
            }
        }
    }
    public static ArrayList getpotioneffects(Integer custommodeldata) {
        //ArrayList<String> list2 = null;
        if(effectlist222.containsKey(custommodeldata)) {
            return effectlist222.get(custommodeldata);
        }
        return null;
    }
    public static void givepotioneffects(Player p, PotionEffect effect) {
        p.addPotionEffect(effect);
    }
}
