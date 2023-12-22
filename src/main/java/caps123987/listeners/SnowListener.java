package caps123987.listeners;

import caps123987.snowballs.SnowBalls;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SnowListener implements Listener {
    public List<Player> players = new ArrayList<Player>();
    public List<Player> winers = new ArrayList<Player>();

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        if(!e.getCause().equals(DamageCause.PROJECTILE)){
            e.setCancelled(true);
            return;
        }

        if(!(e.getDamager() instanceof Snowball snow)){
            e.setCancelled(true);
            return;
        }

        if(!(e.getEntity() instanceof Player plKill)){
            e.setCancelled(true);
            return;
        }


        if(plKill.isBlocking()){
            if(plKill.getCooldown(Material.SHIELD)==0) {
                plKill.setCooldown(Material.SHIELD, 40);
            }

            plKill.playSound(plKill.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(SnowBalls.getInstance(), plKill::clearActiveItem, 20L);

            return;
        }

        if(snow.getShooter() instanceof Player damager){
            damager.sendActionBar("You hit " + plKill.getName() + " with a snowball!");
            damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            plKill.sendActionBar("You were hit by " + damager.getName() + " with a snowball!");
        }

        plKill.damage(2);
        plKill.setVelocity(snow.getVelocity().multiply(1.25));
        plKill.setSaturation(0);
        plKill.setFoodLevel(18);

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
    @EventHandler
    public void onSnowClick(PlayerInteractEvent e) {
        if(!e.getHand().equals(EquipmentSlot.HAND)){
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getClickedBlock().getType() != Material.POWDER_SNOW) {
            return;
        }
        if(e.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.SNOWBALL, 1), 32)) {
            return;
        }

        Item item = e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation().add(0,1,0), new ItemStack(Material.SNOWBALL, 1));
        item.setVelocity(new Vector(0,0.25,0));
        item.setPickupDelay(200000);
        e.getPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 4));

        Bukkit.getScheduler().scheduleSyncDelayedTask(SnowBalls.getInstance(), ()->{
            item.remove();
        }, 40L);
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void playerKill(PlayerDeathEvent e){
        Player p = e.getEntity();
        p.getScoreboardTags().add("dead");
        p.setGameMode(GameMode.SPECTATOR);

        e.setDeathMessage("");

        List<Player> alivePlayers = new ArrayList<Player>();
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(!(player.getScoreboardTags().contains("dead")||player.getGameMode().equals(GameMode.SPECTATOR))){
                alivePlayers.add(player);
            }
        }

        if(alivePlayers.size() == 2){
            winers.add(p);
        }

        if(alivePlayers.size() == 1){
            winers.add(p);

            Bukkit.broadcastMessage(ChatColor.YELLOW+""+ ChatColor.BOLD+"------------------------");
            Bukkit.broadcastMessage(ChatColor.YELLOW+""+ ChatColor.BOLD+alivePlayers.get(0).getName()+ ChatColor.RESET + " has won the game!");
            Bukkit.broadcastMessage(ChatColor.YELLOW+""+ ChatColor.BOLD+"------------------------");

            Bukkit.broadcastMessage("");
            try {
                Bukkit.broadcastMessage(ChatColor.YELLOW+""+ ChatColor.BOLD+winers.get(1).getName()+ ChatColor.RESET + " placed second!");
            }catch (Exception ignored){}
            try {
                Bukkit.broadcastMessage(ChatColor.YELLOW+""+ ChatColor.BOLD+winers.get(0).getName()+ ChatColor.RESET + " placed third!");
            }catch (Exception ignored){}

            winers.clear();
        }
    }
}