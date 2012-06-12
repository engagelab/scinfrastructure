/*
 * 
 * This is still a fairly naive process, though. It isn't going to do anything for s-sharp ( - used in German), 
 * or any non-Latin-based alphabet (Greek, Cyrillic, CJK, etc).

 * Be careful when changing the case of a string. Upper and lower case forms are dependent on alphabets. 
 * In Turkish, the capitalization of U+0069 (i) is U+0130 (), not U+0049 () so you risk introducing a 
 * non-latin1 character back into your string if you use String.toLowerCase() under a Turkish locale.
 * 
 * */

package utils;

/*
 * Java code/library for generating slugs
 * */

/**
 * @author Muhammad Fahied
 */


import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class JSlug {
	
	  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	  public static String toSlug(String input) {
	    String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
	    String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
	    String slug = NONLATIN.matcher(normalized).replaceAll("");
	    return slug.toLowerCase(Locale.ENGLISH);
	  }

}
