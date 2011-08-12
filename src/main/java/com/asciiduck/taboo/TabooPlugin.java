package com.asciiduck.taboo;
import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event;

public class TabooPlugin extends JavaPlugin {
	public Logger log = Logger.getLogger("Minecraft");
	public TabooChatListener chatListener;
	public TabooConfiguration tConfig;

	public void onEnable() {
		log.info("Enabling taboo.");

		tConfig = new TabooConfiguration(this);
		chatListener = new TabooChatListener(this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_CHAT, chatListener, Event.Priority.Normal, this);
	}

	public void onDisable() {
		log.info("Disabling taboo.");
	}
}
