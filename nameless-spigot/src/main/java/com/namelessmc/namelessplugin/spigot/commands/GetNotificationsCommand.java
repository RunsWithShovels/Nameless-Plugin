package com.namelessmc.namelessplugin.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.namelessmc.namelessplugin.spigot.NamelessPlugin;
import com.namelessmc.namelessplugin.spigot.utils.RequestUtil;

/*
 *  Register CMD
 */

public class GetNotificationsCommand implements CommandExecutor {

	NamelessPlugin plugin;
	String permission;

	/*
	 *  Constructer
	 */
	public GetNotificationsCommand(NamelessPlugin pluginInstance) {
		this.plugin = pluginInstance;
		this.permission = plugin.permission;
	}

	/*
	 *  Handle inputted command
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		// check if player has permission Permission & ensure who inputted command is a Player
		if(sender instanceof Player && sender.hasPermission(permission + ".notifications")){

			Player player = (Player) sender;

			// Try to register user
			Bukkit.getScheduler().runTaskAsynchronously(plugin,  new Runnable(){
				@Override
				public void run(){
					// Ensure email is set
					if(args.length < 1 || args.length > 1){
						player.sendMessage(ChatColor.RED + "Incorrect usage: /register email");
						return;
					}

					RequestUtil request = new RequestUtil(plugin);
					try {
						request.getNotifications(player);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		} else if (!sender.hasPermission(permission + ".notifications")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to this command!");
		} else {
			// User must be ingame to use register command
			sender.sendMessage("You must be ingame to use this command.");
		}
		return true;
	}
}