package com.asciiduck.taboo.data;
import java.util.regex.Pattern;
import java.util.ArrayList;
public class Taboo {
	public String name;
	public Pattern pattern;
    public ArrayList<String> patternStrings;
	public ArrayList<String> actions;
    public ArrayList<TabooSubstitution> substitutions;
	public String includePermission;
    public String excludePermission;
}
