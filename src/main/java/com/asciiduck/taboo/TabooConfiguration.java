package com.asciiduck.taboo;
import com.asciiduck.taboo.data.Taboo;
import com.asciiduck.taboo.data.TabooSubstitution;
import org.bukkit.util.config.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabooConfiguration {
	ArrayList <Taboo> taboos;
	public TabooConfiguration(TabooPlugin parent) {
        Pattern substitutionPattern = Pattern.compile("s/(.*)/(.*)/");
		Configuration config = parent.getConfiguration();
		taboos = new ArrayList<Taboo>();
		List <String> taboo_names = config.getKeys();
		for(String name:taboo_names) {
			Taboo t = new Taboo();
			t.name = name;
            t.substitutions = new ArrayList<TabooSubstitution>();
			String pattern_str = "";
			for(String s:config.getStringList(name+".patterns",null)) {
                Matcher substitutionMatcher = substitutionPattern.matcher(s);
                if(substitutionMatcher.matches()) {
                    t.substitutions.add(new TabooSubstitution(substitutionMatcher.group(1),substitutionMatcher.group(2)));
                    s = substitutionMatcher.group(1);
                }
				if(pattern_str.length()>0) {
					pattern_str+="|";
				}
				pattern_str+=s;
			}
			parent.log.info(name+": "+pattern_str);
			t.pattern = Pattern.compile(pattern_str,Pattern.CASE_INSENSITIVE);
			t.actions = (ArrayList<String>) config.getStringList(name+".actions",null);

			t.includePermission = config.getString(name+".includePermission",null);
			if(t.includePermission!=null) {
				t.includePermission = "taboo."+t.includePermission;
			}
			t.excludePermission = config.getString(name+".excludePermission",null);
			if(t.excludePermission!=null) {
				t.excludePermission = "taboo."+t.excludePermission;
			}

			taboos.add(t);
		}
	}

	public ArrayList<Taboo> getTaboos() {
		return taboos;
	}
}
