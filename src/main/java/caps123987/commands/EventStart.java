package caps123987.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventStart implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!sender.hasPermission("snowevent.admin")) {
            sender.sendMessage("You don't have permissions to use this command");
            return true;
        }

        List<Player> players = (List<Player>) Bukkit.getServer().getOnlinePlayers().stream().toList();

        players.forEach(player -> {
            Inventory inv = player.getInventory();

            inv.clear();

            inv.setItem(8, new ItemStack(Material.SHIELD, 1));

            inv.setItem(40, new ItemStack(Material.TOTEM_OF_UNDYING, 1));

            player.sendTitle("",ChatColor.YELLOW+"Get snowballs from "+ ChatColor.RED + "Powder Snow"+ChatColor.YELLOW+"!");

            player.getScoreboardTags().remove("dead");

            player.setGameMode(GameMode.ADVENTURE);

            player.teleport(new Location(player.getWorld(), 245, 124, -6));

            player.sendMessage("Event started!");
        });
        return true;
    }
}