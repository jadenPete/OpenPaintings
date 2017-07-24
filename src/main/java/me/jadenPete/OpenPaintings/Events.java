package me.jadenPete.OpenPaintings;

import org.bukkit.Art;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * This class is responsible for:
 *	- Placing the selected painting through HangingPlaceEvent.
 *	- Removing players' selections through PlayerQuitEvent.
 *	
 *	It requires the Util class to function.
 */
public class Events implements Listener {
	// Variable to access the plugin's config.yml.
	private static FileConfiguration config;
		
	// The class's constructor set's the config variable
	// to getConfig(), referenced from the Main class,
	// since we cannot directly access non-static objects.
	public Events(Main instance){
		config = instance.getConfig();
	}
	
	// When the player places a painting or item frame.
	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event){
		// Make sure that the entity is a painting.
		if(event.getEntity() instanceof Painting){
			int index = Util.getSelection(event.getPlayer().getName());
			
			// If the player that placed it has a selection.
			if(index != -1){
				Painting painting = (Painting) event.getEntity();
				Art selection = (Art) Util.selections.get(index).get(1);
				
				painting.setArt(selection);
				
				// Tell the player if the painting was placed.
				// If the painting wasn't placed successfully,
				// assume that it was too large to be placed.
				if(painting.getArt() == selection){
					event.getPlayer().sendMessage(config.getString("messages.place"));
					
					Util.selections.remove(index);
				} else {
					event.getPlayer().sendMessage(config.getString("messages.place-error"));
				}
			}
		}
	}
	
	// When a player disconnects.
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		int index = Util.getSelection(event.getPlayer().getName());
		
		// If the player has a selection, remove it.
		if(index != -1){
			Util.selections.remove(index);
		}
	}
}
