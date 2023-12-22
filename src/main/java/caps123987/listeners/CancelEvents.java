package caps123987.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CancelEvents implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }

        if(e.getClickedBlock()==null){
            return;
        }


        if(e.getItem()==null){
            e.setCancelled(true);
            return;
        }
        if(e.getItem().getType().equals(Material.SNOWBALL)){
            if(!e.getClickedBlock().getType().isInteractable()){
                return;
            }
            e.setCancelled(true);
        }
        if(e.getItem().getType().equals(Material.SHIELD)){
            if(!e.getClickedBlock().getType().isInteractable()){
                return;
            }
            e.setCancelled(true);
        }
        e.setCancelled(true);

    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e){
        e.setCancelled(true);
    }
}
