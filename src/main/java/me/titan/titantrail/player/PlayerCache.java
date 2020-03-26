package me.titan.titantrail.player;

import lombok.Getter;
import lombok.Setter;
import me.titan.titantrail.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompSound;
import org.mineacademy.fo.settings.YamlSectionConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class PlayerCache extends YamlSectionConfig {

	boolean muted;

	int muteRemainingTime;
	boolean timerPaused;


	TimeUnit timeUnit;

	Player p;

	UUID id;
	public static Map<UUID, PlayerCache> players = new HashMap<>();

	public PlayerCache(UUID uuid) {
		super(uuid.toString());
		this.id = uuid;
		p = Bukkit.getPlayer(uuid);

		loadConfiguration(null,"data/players.data");

	}
	public static PlayerCache getCache(UUID id){
		PlayerCache pc = players.get(id);

		if(pc == null){
			pc = new PlayerCache(id);
			players.put(id,pc);
		}
		return pc;

	}
	public static PlayerCache getCache(Player p){
		return getCache(p.getUniqueId());
	}
	public Player getPlayer(){
		if(p == null){
			p = Bukkit.getPlayer(getId());
		}
		return p;
	}

	@Override
	protected void onLoadFinish() {

		if(!isSet("Muted")) return;
		System.out.print("gg");
		muted = getBoolean("Muted");
		muteRemainingTime = getInteger("Mute_Remaining_Time");
		timeUnit = Util.getEnum(getString("Time_Unit"), TimeUnit.class);


		if( getBoolean("Timer_Paused")){

			System.out.print("timer pa");
			timerPaused = false;
			startMuting();


		}

	}

	public void saveData(){
		setNoSave("Muted",muted);
		setNoSave("Mute_Remaining_Time",muteRemainingTime);
		setNoSave("Timer_Paused",timerPaused);
		setNoSave("Time_Unit",timeUnit);

		save();
	}

	public void startMuting(){

		//AtomicInteger seconds = new AtomicInteger(muteRemainingTime);
		Common.runTimerAsync(20, new BukkitRunnable() {
			@Override
			public void run() {
//				if(SimplePlugin.isReloading() || !SimplePlugin.getInstance().isEnabled()){
//					timerPaused = true;
//					cancel();
//					return;
//				}
				if(!muted){

					stopMute();
					cancel();
					return;
				}
				muteRemainingTime--;//.getAndDecrement();

				System.out.print(muteRemainingTime);
				if(muteRemainingTime<= 0){
					stopMute();
					cancel();
					return;
				}

			}
		});


	}
	public void stopMute(){

		muted = false;
		timeUnit = null;
		muteRemainingTime = 0;

		Player p = getPlayer();
		if(p != null && p.isOnline()){

			Common.tell(p,"&aYou are no longer muted!");
			CompSound.NOTE_PLING.play(p);
		}


	}
	public int getSecondsAmount(TimeUnit unit){
		switch (unit){
			case SECONDS:
				return 20;
			case MINUTES:
				return 20 * 60;
			case HOURS:
				return 20 * 60 * 60;
			case DAYS:
				return 20 * 60 * 60 * 24;
		}
		return 1;
	}
}
