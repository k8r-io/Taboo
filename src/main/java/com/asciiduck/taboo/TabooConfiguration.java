package com.asciiduck.taboo;
import org.bukkit.util.config.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TabooConfiguration {
	ArrayList <Taboo> taboos;
	public TabooConfiguration(TabooPlugin parent) {
		Configuration config = parent.getConfiguration();
		taboos = new ArrayList();
		List <String> taboo_names = config.getKeys();
		for(String name:taboo_names) {
			Taboo t = new Taboo();
			t.name = name;
			String pattern_str = new String();
			for(String s:config.getStringList(name+".patterns",null)) {
				if(pattern_str.length()>0) {
					pattern_str+="|";
				}
				pattern_str+=s;
			}
			parent.log.info(name+": "+pattern_str);
			t.pattern = Pattern.compile(pattern_str,Pattern.CASE_INSENSITIVE);
			t.actions = (ArrayList) config.getStringList(name+".actions",null);

			t.permission = config.getString(name+".permission",null);
			if(t.permission!=null) {
				t.permission = "taboo."+t.permission;
			}
			t.censor = config.getBoolean(name+".censor",false);

			taboos.add(t);
		}
	}

	public ArrayList<Taboo> getTaboos() {
		return taboos;
	}
}
