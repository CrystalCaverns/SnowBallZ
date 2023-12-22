package caps123987.snowballs;

import caps123987.commands.EventEnd;
import caps123987.commands.EventStart;
import caps123987.listeners.CancelEvents;
import caps123987.listeners.SnowListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SnowBalls extends JavaPlugin {
    static SnowBalls instance;
    @Override
    public void onEnable() {
        instance = this;

        getCommand("eventstart").setExecutor(new EventStart());
        getCommand("eventstop").setExecutor(new EventEnd());

        Bukkit.getServer().getPluginManager().registerEvents(new SnowListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CancelEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SnowBalls getInstance(){
        return instance;
    }
}