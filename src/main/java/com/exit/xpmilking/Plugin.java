package com.exit.xpmilking;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * xp-milking java plugin
 */
public class Plugin extends JavaPlugin implements Listener {
	private static final Logger LOGGER=Logger.getLogger("xp-milking");
	private static boolean halver = false;

	public void onEnable() {
		LOGGER.info("xp-milking enabled");
		
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		LOGGER.info("xp-milking disabled");
	}

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEntityEvent event) {
		if (halver) {
			halver = false; 
			return;
		}

		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

		boolean isHoldingGlassBottle = item.getType().toString().equals("GLASS_BOTTLE");

		if (!isHoldingGlassBottle || !player.isSneaking()) return;
		
		Player tgt = (Player) event.getRightClicked();
		float targetXP = tgt.getTotalExperience();

		if (targetXP < 11) return;

		player.sendMessage("You milked " + tgt.getDisplayName());
		tgt.sendMessage(player.getDisplayName() + " milked you");
		LOGGER.info(player.getDisplayName() + " has milked " + tgt.getDisplayName());

		tgt.giveExp(-11);
		item.setAmount(item.getAmount() - 1);

		ItemStack xpBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		player.getInventory().addItem(xpBottle);

		halver = true;
		// Any other actions upon successful milking
	}
}
