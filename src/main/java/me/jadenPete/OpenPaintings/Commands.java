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
			
			if(args.length == 2 && args[0].equalsIgnoreCase("select")) { //select a painting if given the proper arguments
			  Util.selectPainting(player, args);
			} else if(args.length == 1 && args[0].equalsIgnoreCase("remove")) { //cancel the placement of a painting when given the proper arguments
			  Util.removeSelection(player);
			} else if(args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
			  Util.cancelSelections(player);
			} else if(args.length == 1 && args[0].equalsIgnoreCase("list")) { //list the painting options when given the proper arguments
			  Util.listPaintings(player);
			} else {
			  return false; //return the command's usage if command arguments are invalid
			}
			
		} else {
			sender.sendMessage(config.getString("messages.non-player"));
		}
		
		return true;
	}
}

