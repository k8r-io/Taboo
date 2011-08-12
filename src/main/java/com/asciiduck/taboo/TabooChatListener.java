package com.asciiduck.taboo;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class TabooChatListener extends PlayerListener {
	public static TabooPlugin plugin;

	public TabooChatListener(TabooPlugin instance) {
		plugin = instance;
	}

	public void onPlayerChat(PlayerChatEvent event) {
		plugin.log.info("Doing onPlayerChat in TCL");
		for(Taboo t : plugin.tConfig.getTaboos())
		{
			Matcher m = t.pattern.matcher(event.getMessage());
			if((t.permission==null || event.getPlayer().hasPermission(t.permission)) && m.find())
			{
				plugin.log.info(event.getPlayer().getName()+" has spoken taboo.");
				takeAction(t,event.getPlayer());
				if(t.censor) {
					event.setMessage(m.replaceAll("****"));
				}
			}
		}
	}

	private void takeAction(Taboo t, Player player)
	{
		for(String act:t.actions) {
			if(act.equals("lightning")) {
				player.getWorld().strikeLightning(player.getLocation());
			}
			else if(act.equals("zombies")) {
				player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
				player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
				player.getWorld().spawnCreature(player.getLocation(),CreatureType.ZOMBIE);
			}
			else if(act.equals("giant")) {
				player.getWorld().spawnCreature(player.getLocation(),CreatureType.GIANT);
			}
			else if(act.equals("kick")) {
				player.kickPlayer("You spoke taboo.");
			}
		}
	}


}
