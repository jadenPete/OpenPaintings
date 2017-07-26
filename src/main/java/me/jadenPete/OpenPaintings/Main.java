package me.jadenPete.OpenPaintings;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * This class is responsible for:
 *	- Starting and stopping the plugin.
 *		
 * It does not do anything on its own.
 * It's sole purpose is to manage the plugin
 * and call the Commands class to operate.
 */
public class Main extends JavaPlugin {
	// Fired when the plugin is first enabled.
	@Override
	public void onEnable(){
		saveDefaultConfig();
		
		// Run the Util class constructor, which uses an
		// instance of the main class to access non-static methods.
		new Util(this);
		
		// Handle events in the Events class
		getServer().getPluginManager().registerEvents(new Events(this), this);
		getCommand("painting").setExecutor(new Commands(this));
	}
	
	// Fired when the plugin is disabled.
	// Empty because there really isn't anything to do.
	@Override
	public void onDisable(){
		
	}
}
