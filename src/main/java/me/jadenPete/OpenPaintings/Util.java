package me.jadenPete.OpenPaintings;

import java.util.HashMap;

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
 *	- Providing useful functions to other classes.
 * 
 * It does not interact with the MySQL database in any way.
 * It's sole purpose is to be called upon by onCommand.
 */
public class Util {
	// Variable to access the plugin's config.yml.
	private static FileConfiguration config;
	
	// List of paintings
	private static HashMap<String, Art> availableArt = null;
	
	// Two dimensional ArrayList which contains players' usernames and selections.
	public static HashMap<String, ArtQueue> selections = new HashMap<String, ArtQueue>();
		
	// The class's constructor set's the config variable
	// to getConfig(), referenced from the Main class,
	// since we cannot directly access non-static objects.
	public Util(Main instance){
		config = instance.getConfig();
		
		availableArt = new HashMap<String, Art>() {
		  /**
       * 
       */
      private static final long serialVersionUID = 1852925137377857446L; //"double-brace" notation

    {
		
		  put("ALBAN", Art.ALBAN);
		  put("AZTEC", Art.AZTEC);
		  put("AZTEC2", Art.AZTEC2);
		  put("BOMB", Art.BOMB);
		  put("BURNINGSKULL", Art.BURNINGSKULL);
		  put("BUST", Art.BUST);
		  put("COURBET", Art.COURBET);
		  put("CREEBET", Art.CREEBET);
		  put("DONKEYKONG", Art.DONKEYKONG);
		  put("FIGHTERS", Art.FIGHTERS);
		  put("GRAHAM", Art.GRAHAM);
		  put("KEBAB", Art.KEBAB);
		  put("MATCH", Art.MATCH);
		  put("PIGSCENE", Art.PIGSCENE);
		  put("PLANT", Art.PLANT);
		  put("POINTER", Art.POINTER);
		  put("POOL", Art.POOL);
		  put("SEA", Art.SEA);
		  put("SKELETON", Art.SKELETON);
		  put("SKULL_AND_ROSES", Art.SKULL_AND_ROSES);
		  put("STAGE", Art.STAGE);
		  put("SUNSET", Art.SUNSET);
		  put("VOID", Art.VOID);
		  put("WANDERER", Art.WANDERER);
		  put("WASTELAND", Art.WASTELAND);
		  put("WITHER", Art.WITHER);
		
		}};
		
	}
	
	// Determine whether or not the player in question has made a selection at all
	public static boolean playerHasSelection(Player player) {
	  return selections.containsKey(player.getName());
	}
	
	// Select a painting.
	public static void selectPainting(Player sender, String args[]){
	  
	  if(availableArt.containsKey(args[1].toUpperCase())) {
	    if(!selections.containsKey(sender.getName())) selections.put(sender.getName(), new ArtQueue());
	    selections.get(sender.getName()).add(availableArt.get(args[1].toUpperCase()));
	    sender.sendMessage(config.getString("messages.select"));
	  } else {
	    sender.sendMessage(config.getString("messages.invalid-painting").replace("%p", args[1]));
	  }
	  
	}
	
	// Remove a player's selection.
	public static void cancelSelection(Player player) {
	  
	  String playerName = player.getName();
	  
	  if(selections.containsKey(playerName) && selections.get(playerName).hasNext()) {
	    selections.get(playerName).discardNewest();
	    player.sendMessage(config.getString("messages.cancel"));
	  } else {
	    player.sendMessage(config.getString("messages.cancel-error"));
	  }
	  
	}
	
	// Remove the player from the database.
	public static void cancelAllSelections(Player player) {
	  selections.remove(player.getName());
	}
	
	// Provide a player with a list of valid paintings.
	public static void listPaintings(Player sender){
		sender.sendMessage(config.getString("messages.list"));
	}
	
	// Get the index for a player's selection.
	public static Art getSelection(Player player) {
	  String playerName = player.getName();
	  
	  if(selections.containsKey(playerName) && selections.get(playerName).hasNext()) return selections.get(playerName).getNext();
	  return null;
	}
	
	// Discard the oldest selection
	public static void discardOldestSelection(Player player) {
	  String playerName = player.getName();
	  if(selections.containsKey(playerName)) {
	    selections.get(playerName).discardOldest();
      if(!selections.get(playerName).hasNext()) selections.remove(playerName);
	  }
	}
	
	// Discard the newest selection
	public static void discardNewestSelection(Player player) {
	  String playerName = player.getName();
	  if(selections.containsKey(playerName)) {
	    selections.get(playerName).discardNewest();
	    if(!selections.get(playerName).hasNext()) selections.remove(playerName);
	  }
	}
	
}
