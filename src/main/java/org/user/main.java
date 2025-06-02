package org.user;

import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    private static main instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static main getInstance() {
        return instance;
    }
}