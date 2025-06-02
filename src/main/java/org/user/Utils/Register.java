package org.user.Utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.user.main;

public class Register {
    public static void loadListeners() {
        registerListeners(

        );

    }

    private static void registerListeners(Listener... listeners) {
        for (Listener listenerGen : listeners) {
            Bukkit.getPluginManager().registerEvents(listenerGen, main.getInstance());
        }
    }
}
