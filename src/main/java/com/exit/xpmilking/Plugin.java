package com.exit.xpmilking;

import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

/*
 * xp-milking java plugin
 */
public class Plugin extends JavaPlugin implements Listener {
	private static final Logger LOGGER=Logger.getLogger("xp-milking");

	public void onEnable() {
		LOGGER.info("xp-milking enabled");
		
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		LOGGER.info("xp-milking disabled");
	}

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.getItem() == null) return;

		boolean isHoldingGlassBottle = event.getItem().getType().toString().equals("GLASS_BOTTLE");

		if (!isHoldingGlassBottle || (!event.getAction().toString().equals("RIGHT_CLICK_AIR") && !event.getAction().toString().equals("RIGHT_CLICK_BLOCK"))) return;

		LOGGER.info(player.getDisplayName() + " right clicked");
		player.sendMessage("You right-clicked");

		RayTraceResult res = player.rayTraceBlocks(10);

		// If another player was not right-clicked on, return
		if (res == null || res.getHitEntity() == null || !(res.getHitEntity() instanceof Player)) return;
		
		Player tgt = (Player) res.getHitEntity();
		player.sendMessage("You right-clicked " + tgt.getDisplayName());
		LOGGER.info(player.getDisplayName() + " right clicked " + tgt.getDisplayName());

		LOGGER.info(player.getDisplayName() + " has milked " + tgt.getDisplayName());
		int targetXP = tgt.getTotalExperience();

		if (targetXP < 20) return;

		tgt.setTotalExperience(targetXP - 20);
		event.getItem().setAmount(event.getItem().getAmount() - 1);
		ItemStack xpBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
		player.getInventory().addItem(xpBottle);
		// Any other actions upon successful milking
	}
}


// Consumes 20 XP and produces 1 Bottle O' Enchanting
