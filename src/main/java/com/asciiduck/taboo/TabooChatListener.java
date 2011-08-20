package com.asciiduck.taboo;
import com.asciiduck.taboo.data.Taboo;
import com.asciiduck.taboo.data.TabooSubstitution;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import java.util.regex.*;


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
			if(tabooApplies(t,event.getPlayer()) && m.find())
			{
				plugin.log.info(event.getPlayer().getName()+" has spoken taboo.");
				takeAction(t,event.getPlayer());
				for(TabooSubstitution ts:t.substitutions) {
                    Matcher mts = ts.replacePattern.matcher(event.getMessage());
                    if(mts.find()) {
                        event.setMessage(mts.replaceAll(ts.replaceWith));

                    }
                }
			}
		}
	}

	private void takeAction(Taboo t, Player player)	{
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
            else if(act.equals("announce")) {
                player.getServer().broadcastMessage("Player "+player.getName()+" broke the taboo '"+t.name);
            }
            else if(act.equals("explode")) {
                player.getWorld().createExplosion(player.getLocation(),2);
            }
            else if(act.equals("day")) {
                player.getWorld().setTime(8000);
            }
		}
	}

    private boolean tabooApplies(Taboo t, Player p) {
        return ((t.includePermission == null || p.hasPermission(t.includePermission)) &&
                (t.excludePermission == null || !p.hasPermission(t.excludePermission)));
    }
}
