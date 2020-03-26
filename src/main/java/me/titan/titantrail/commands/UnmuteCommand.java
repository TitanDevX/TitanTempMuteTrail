package me.titan.titantrail.commands;

import me.titan.titantrail.player.PlayerCache;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

public class UnmuteCommand extends SimpleCommand {
	public UnmuteCommand() {
		super("unmute");

		setPermission("titantrail.unmute");

		setMinArguments(1);
		setUsage("<Player>");
	}

	@Override
	protected void onCommand() {

		Player p = findPlayer(args[0]);


		PlayerCache pc = PlayerCache.getCache(p);
		if(!pc.isMuted()){
			returnTell("&cThis player is not muted.");
		}

		pc.setMuted(false);
		pc.setMuteRemainingTime(0);
		pc.setTimeUnit(null);
		pc.setTimerPaused(false);
		//pc.stopMute();


	}
}
