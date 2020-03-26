package me.titan.titantrail.listeners;

import me.titan.titantrail.player.PlayerCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.fo.Common;

public class PlayerListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		PlayerCache pc = PlayerCache.getCache(p);

		if(pc.isMuted()){

			Common.tell(p,"&cYou are currently muted.");
			e.setCancelled(true);
			return;
		}
	}

}
