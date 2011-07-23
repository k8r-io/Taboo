package com.asciiduck.taboo;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class TabooChatListener extends PlayerListener {
	public static Taboo plugin;
	private boolean enabled;
	private String[] punishments = {"lightning","zombies","giant","kick"};
	private HashMap<String,Pattern> taboos;

	public TabooChatListener(Taboo instance) {
		plugin = instance;
		enabled = false;
		taboos = new HashMap();

		for(String p : punishments)
		{
			Pattern tabooPattern;
			String taboos_str ="";
			List <String> taboo_arr = plugin.config.getStringList(p,new ArrayList<String>());

			for (int i = 0; i<taboo_arr.size(); i++) {
				if(taboos_str.length()>0) {
					taboos_str += "|";
				}
				taboos_str += taboo_arr.get(i);
			}

			if(taboos_str.length()>0) {
				tabooPattern = Pattern.compile(".*("+taboos_str+").*",Pattern.CASE_INSENSITIVE);
				taboos.put(p,tabooPattern);
				enabled = true;
			}
		}
		if(!enabled) {
			plugin.log.info("No taboo words found, disabling Taboo.");
		}
	}

	public void onPlayerChat(PlayerChatEvent event) {
		if(enabled)
		{
			for(String k : taboos.keySet())
			{
				if( taboos.get(k).matcher(event.getMessage()).matches())
				{
					plugin.log.info(event.getPlayer().getName()+" has spoken taboo.");
					punish(k,event.getPlayer());
				}
			}
		}
	}

	private void punish(String punishment, Player player)
	{
		if(punishment.equals("lightning")) {
			player.getWorld().strikeLightning(player.getLocation());
		}
		else if(punishment.equals("zombies")) {
			player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
			player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
			player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
		}
		else if(punishment.equals("giant")) {
			player.getWorld().spawnCreature(player.getLocation(),CreatureType.GIANT);
		}
		else if(punishment.equals("kick")) {
			player.kickPlayer("You spoke taboo.");
		}
	}


}
