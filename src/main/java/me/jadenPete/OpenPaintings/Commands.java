package me.jadenPete.OpenPaintings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/*
 * This class is responsible for:
 *	- Parsing commands and only checking
 *		that arguments contain the right characters
 *		and are provided in the right amount.
 * 
 * It does not do anything on it's own.
 * It does not interact with the MySQL database in any way.
 * It's sole purpose is to be called upon by Main
 * and call the Util class to operate.
 */
public class Commands implements CommandExecutor {
	// Variable to access the plugin's config.yml.
	private static FileConfiguration config;
	
	// The class's constructor set's the config variable
	// to getConfig(), referenced from the Main class,
	// since we cannot directly access non-static objects.
	public Commands(Main instance){
		config = instance.getConfig();
	}
	
	// Parses the plugin command.
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		// Check that the command is being executed by a player,
		// and not the console, another plugin, or a command block.
		// Otherwise display an error-message specified in config.yml.
		if(sender instanceof Player){
			// Variable for the player who executed the command,
			Player player = (Player) sender;
			
			// If an argument is specified, carry on with the command.
			// Otherwise return false and show the command's usage.
			if(args.length > 0){
				// Parse the sub-command.
				switch(args[0]){
					case "select":{
						// Carry on with the sub-command if a painting is specified.
						// Otherwise return false and show the command's usage.
						if(args.length == 2){
							if(!args[1].matches("[a-zA-Z]+")){
								return false;
							}
						} else {
							return false;
						}
						
						Util.selectPainting(player, args);
						
						break;
					}
					
					case "cancel":{
						// Carry on with the sub-command if no arguments are specified.
						// Otherwise return false and show the command's usage.
						if(args.length != 1){
							return false;
						}
						
						Util.cancelSelection(player);
						
						break;
					}
					
					case "list":{
						// Carry on with the sub-command if no arguments are specified.
						// Otherwise return false and show the command's usage.
						if(args.length != 1){
							return false;
						}
						
						Util.listPaintings(player);
						
						break;
					}
					
					default:{
						return false;
					}
				}
			} else {
				return false;
			}	
		} else {
			sender.sendMessage(config.getString("messages.non-player"));
		}
		
		return true;
	}
}

