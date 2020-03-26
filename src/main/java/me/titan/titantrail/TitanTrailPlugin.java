package me.titan.titantrail;

import me.titan.titantrail.commands.MuteCommand;
import me.titan.titantrail.commands.ReloadCommand;
import me.titan.titantrail.commands.UnmuteCommand;
import me.titan.titantrail.listeners.PlayerListener;
import me.titan.titantrail.player.PlayerCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.fo.plugin.SimplePlugin;

public class TitanTrailPlugin extends SimplePlugin {
	@Override
	protected void onPluginStart() {

		registerCommand(new MuteCommand());
		registerCommand(new UnmuteCommand());
		registerCommand(new ReloadCommand());

		registerEvents(new PlayerListener());
	}

	@Override
	protected void onPluginStop() {
		for(PlayerCache pc : PlayerCache.players.values()){
			pc.saveData();
		}
	}

	@Override
	protected void onPluginPreReload() {

		for(PlayerCache pc : PlayerCache.players.values()){
			if(pc.isMuted()) pc.setTimerPaused(true);
			pc.saveData();
		}
		PlayerCache.players.clear();
		for(Player p : Bukkit.getOnlinePlayers()){

			PlayerCache pc = PlayerCache.getCache(p);


		}

	}
}
