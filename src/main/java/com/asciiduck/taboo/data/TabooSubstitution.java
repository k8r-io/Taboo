package com.asciiduck.taboo.data;


import java.util.ArrayList;
import java.util.regex.Pattern;

public class TabooSubstitution {
    public Pattern replacePattern;
    public ArrayList<String> replaceStrings;
    public String replaceWith;

    public TabooSubstitution(String replace, String replaceWith) {
        this.replaceWith = replaceWith;
        replaceStrings = new ArrayList<String>();
        replaceStrings.add(replace);
        replacePattern = Pattern.compile(replace,Pattern.CASE_INSENSITIVE);
    }
}
