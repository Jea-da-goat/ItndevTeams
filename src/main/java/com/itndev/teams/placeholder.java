package com.itndev.teams;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class placeholder extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "itndevteams";
    }

    @Override
    public String getAuthor() {
        return "Itndev";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if(p == null) {
            return "";
        }
        if(params.equals("teamname")) {

            return storage.getformattedteamname(p);


        } else if(params.equals("rank")) {
            return storage.getrankformattedteamname(p);
        } else if(params.equals("rawname")) {
            if(storage.getrank(p).equals("nomad")) {
                return "&7무소속";
            } else {
                return "&a" + storage.getteamname(p);
            }
        }
        return null;
    }
}
