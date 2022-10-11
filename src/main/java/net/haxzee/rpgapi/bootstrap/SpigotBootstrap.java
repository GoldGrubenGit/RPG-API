package net.haxzee.rpgapi.bootstrap;

import net.haxzee.rpgapi.APIService;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotBootstrap extends JavaPlugin {
    @Override
    public void onEnable() {
        new APIService();
    }
}
