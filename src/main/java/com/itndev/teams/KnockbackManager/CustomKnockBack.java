package com.itndev.teams.KnockbackManager;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class CustomKnockBack {

    private static HashMap<Player, Boolean> IfFirstSprintHit = new HashMap<>();

    private static double horizontalKB = 0.0;
    private static double verticalKB = 0.0;

    private static double sprinthorizontalKB = 0.0;
    private static double sprintverticalKB = 0.0;

    private static double AirMultiplerhorizontal = 0.0;
    private static double AirMultiplervertical = 0.0;




    public static void CustomKnockBack(Player Damaged, Player Attacked) {
        if(CustomKnockBack.IfFirstSprintHit.containsKey(Attacked) && CustomKnockBack.IfFirstSprintHit.get(Attacked).equals(true)) {
            CustomKnockBack.IfFirstSprintHit.put(Attacked, false);
            Vector direction = new Vector(Damaged.getLocation().getX() - Attacked.getLocation().getX(), 0.0D, Damaged.getLocation().getZ() - Attacked.getLocation().getZ());
            //Damaged.setVelocity(new Vector(direction.multiply()));
        } else {

        }
    }

    public static void SprintEvent(PlayerToggleSprintEvent e) {
        //CustomKnockBack.IfFirstSprintHit.put(e.getPlayer(), true);
    }

}
