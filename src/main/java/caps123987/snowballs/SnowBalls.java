package caps123987.snowballs;

import caps123987.listeners.SnowListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class SnowBalls extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new SnowListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
