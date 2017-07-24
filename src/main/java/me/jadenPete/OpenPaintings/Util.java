package me.jadenPete.OpenPaintings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Art;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/*
 * This class is responsible for:
 *	- Interacting with the user.
 *	- Handling and reporting errors.
 *	- Making sure that provided data is valid, but
 *		assuming that it contains the right characters
 *		and is provided in the right amount.
 *	- Providing usefull functions to other classes.
 * 
 * It does not interact with the MySQL database in any way.
 * It's sole purpose is to be called upon by onCommand.
 */
public class Util {
	// Variable to access the plugin's config.yml.
	private static FileConfiguration config;
	
	// Two dimensional ArrayList which contains players' usernames and selections.
	public static List<List<Object>> selections = new ArrayList<List<Object>>();
		
	// The class's constructor set's the config variable
	// to getConfig(), referenced from the Main class,
	// since we cannot directly access non-static objects.
	public Util(Main instance){
		config = instance.getConfig();
	}
	
	// Select a painting.
	public static void selectPainting(Player sender, String args[]){
		Art art = null;
		
		// Parse the painting on a case-insensitive level.
		switch(args[1].toUpperCase()){
			case "ALBAN":{
				art = Art.ALBAN;
				
				break;
			}
	
			case "AZTEC":{
				art = Art.AZTEC;
				
				break;
			}
	
			case "AZTEC2":{
				art = Art.AZTEC2;
				
				break;
			}
	
			case "BOMB":{
				art = Art.BOMB;
				
				break;
			}
	
			case "BURNINGSKULL":{
				art = Art.BURNINGSKULL;
				
				break;
			}
	
			case "BUST":{
				art = Art.BUST;
				
				break;
			}
	
			case "COURBET":{
				art = Art.COURBET;
				
				break;
			}
	
			case "CREEBET":{
				art = Art.CREEBET;
				
				break;
			}
	
			case "DONKEYKONG":{
				art = Art.DONKEYKONG;
				
				break;
			}
	
			case "FIGHTERS":{
				art = Art.FIGHTERS;
				
				break;
			}
	
			case "GRAHAM":{
				art = Art.GRAHAM;
				
				break;
			}
	
			case "KEBAB":{
				art = Art.KEBAB;
				
				break;
			}
	
			case "MATCH":{
				art = Art.MATCH;
				
				break;
			}
	
			case "PIGSCENE":{
				art = Art.PIGSCENE;
				
				break;
			}
	
			case "PLANT":{
				art = Art.PLANT;
				
				break;
			}
	
			case "POINTER":{
				art = Art.POINTER;
				
				break;
			}
	
			case "POOL":{
				art = Art.POOL;
				
				break;
			}
	
			case "SEA":{
				art = Art.SEA;
				
				break;
			}
	
			case "SKELETON":{
				art = Art.SKELETON;
				
				break;
			}
	
			case "SKULL_AND_ROSES":{
				art = Art.SKULL_AND_ROSES;
				
				break;
			}
	
			case "STAGE":{
				art = Art.STAGE;
				
				break;
			}
	
			case "SUNSET":{
				art = Art.SUNSET;
				
				break;
			}
	
			case "VOID":{
				art = Art.VOID;
				
				break;
			}
	
			case "WANDERER":{
				art = Art.WANDERER;
				
				break;
			}
	
			case "WASTELAND":{
				art = Art.WASTELAND;
				
				break;
			}
	
			case "WITHER":{
				art = Art.WITHER;
				
				break;
			}
			
			// If the painting is invalid tell the user.
			default:{
				sender.sendMessage(config.getString("messages.invalid-painting").replace("%p", args[1]));
			}
		}
		
		// If the painting is valid make the selection.
		if(art != null){
			int index = getSelection(sender.getName());
			
			// If the player doesn't have a selection, make one.
			// Otherwise override the current selection.
			if(index == -1){
				List<Object> selection = new ArrayList<Object>();
				
				selection.add(sender.getName());
				selection.add(art);
				
				selections.add(selection);
			} else {
				selections.get(index).set(1, art);
			}
			
			sender.sendMessage(config.getString("messages.select"));
		}
	}
	
	// Remove a player's selection.
	public static void cancelSelection(Player sender){
		int index = getSelection(sender.getName());
		
		// If the player has a selection, remove it.
		// Otherwise tell them that they have no selection.
		if(index != -1){
			selections.remove(index);
			
			sender.sendMessage(config.getString("messages.cancel"));
		} else {
			sender.sendMessage(config.getString("messages.cancel-error"));
		}
	}
	
	// Provide a player with a list of valid paintings.
	public static void listPaintings(Player sender){
		sender.sendMessage(config.getString("messages.list"));
	}
	
	// Get the index for a player's selection.
	public static int getSelection(String player){
		for(int a = 0; a < selections.size(); a++){
			if(selections.get(a).get(0) == player) {
				return a;
			}
		}
		
		return -1;
	}
}
