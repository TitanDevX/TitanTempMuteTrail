package me.titan.titantrail.commands;

import me.titan.titantrail.player.PlayerCache;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.concurrent.TimeUnit;

public class MuteCommand extends SimpleCommand {
	public MuteCommand() {
		super("tempmute");

		setPermission("titantrail.tempmute");

		setMinArguments(2);
		setUsage("<Player> <Time:TimeUnit>");
	}

	@Override
	protected void onCommand() {

		Player p = findPlayer(args[0]);
		String timeStr = args[1];
		int seconds = 0;
		TimeUnit timeUnit = null;
		if(timeStr.contains(":")){
			String[] parts = timeStr.split(":");
			if(parts.length > 1){
				seconds = Integer.parseInt(parts[0]);

				timeUnit = findEnum(TimeUnit.class,parts[1],"&cInvalid time unit.");
			}
		}
		if(timeUnit == null){
			timeUnit = TimeUnit.SECONDS;
		}
		if(seconds == 0){
			seconds =  Integer.parseInt(timeStr);
		}

		PlayerCache pc = PlayerCache.getCache(p);
		if(pc.isMuted()){
			returnTell("&cPlayer is already muted.");
		}
		pc.setMuted(true);
		pc.setMuteRemainingTime((int) TimeUnit.SECONDS.convert(seconds,timeUnit));
		pc.setTimeUnit(timeUnit);
		pc.startMuting();
		tell("&aYou have successfully muted &c" + p.getName() + " &afor &c" + seconds + " " + timeUnit.toString().replace("_","").toLowerCase() + "&a.");
		Common.tell(p,"&cYou've been muted.");

	}
}
