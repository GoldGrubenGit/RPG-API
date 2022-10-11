package net.haxzee.rpgapi;

import org.bukkit.plugin.java.JavaPlugin;

public class APIService {
    private static APIService instance;

    public APIService() {
        instance = this;

        sendLogMessage("Version 1.0.0-ALPHA");
        sendLogMessage("coded by haxzee");
    }

    public static void sendLogMessage(String message) {
        System.out.println("[RPGAPI] - " + message);
    }

    public static APIService getInstance() {
        return instance;
    }
}
