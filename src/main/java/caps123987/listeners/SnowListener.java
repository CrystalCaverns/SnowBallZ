package caps123987.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SnowListener implements Listener {
    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        Bukkit.broadcastMessage("Snowball hit!");
    }
}
