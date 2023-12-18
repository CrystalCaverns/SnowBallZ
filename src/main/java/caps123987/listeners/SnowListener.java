package caps123987.listeners;

import caps123987.snowballs.SnowBalls;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SnowListener implements Listener {
    public List<Player> players = new ArrayList<Player>();

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Snowball)){
            return;
        }

        if(!(e.getEntity() instanceof Player plKill)){
            return;
        }

        if(!e.getCause().equals(DamageCause.PROJECTILE)){
            return;
        }

        Snowball snow = (Snowball) e.getDamager();

        if(!(snow.getShooter() instanceof Player damager)){
            plKill.damage(2);
            plKill.setVelocity(snow.getVelocity().multiply(2));
            return;
        }

        damager.sendMessage("You hit " + plKill.getName() + " with a snowball!");
        plKill.sendMessage("You were hit by " + damager.getName() + " with a snowball!");
        plKill.damage(2);
        plKill.setVelocity(snow.getVelocity().multiply(2));

    }

    @EventHandler
    public void freeze(PotionSplashEvent e){
        List<Player> playersSplash = e.getAffectedEntities().stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).toList();



        playersSplash.forEach(player -> {

            if(!players.contains(player)){
                players.add(player);

                player.sendMessage("You have been frozen!");

                Bukkit.getScheduler().scheduleSyncDelayedTask(SnowBalls.getInstance(),()->{
                    try {
                        players.remove(player);
                    }catch (Exception ignored){}
                },60L);
            }
        });

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(players.contains(e.getPlayer())){
            e.getPlayer().setVelocity(e.getPlayer().getVelocity().multiply(0));
        }
    }

    @EventHandler
    public void throwProj(ProjectileLaunchEvent e){
        if(e.getEntity() instanceof Snowball snow){
            snow.setVelocity(snow.getVelocity().multiply(1.25));
        }
        if(e.getEntity() instanceof ThrownPotion pot){
            pot.setVelocity(pot.getVelocity().multiply(1.5));
        }

    }
}