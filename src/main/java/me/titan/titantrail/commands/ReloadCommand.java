package me.titan.titantrail.commands;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.plugin.SimplePlugin;

public class ReloadCommand extends SimpleCommand {
	public ReloadCommand() {
		super("trailReload");

		setPermission("titantrail.reload");


	}

	@Override
	protected void onCommand() {

		SimplePlugin.getInstance().reload();
		try {
			Common.tell(sender,"&aSuccessfully reloaded.");
		}catch (Throwable t) {
			Common.tell(sender,"&cError while reloading.");
		}

	}
}
