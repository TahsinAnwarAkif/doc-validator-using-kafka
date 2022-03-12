package com.sdase.docvalidator.util;


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author akif
 * @since 3/11/22
 */
public class StringUtils {

	public static Set<String> getMatchedStringSet(String text, String regex) {
		final Set<String> ibanSet = new LinkedHashSet<>();
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			ibanSet.add(matcher.group());
		}

		return ibanSet;
	}

	public static String nullSafeString(String str) {
		return isNotEmpty(str) ? str : "";
	}

	public static boolean isNotEmpty(String... strs) {
		if (strs.length == 0) {
			throw new IllegalArgumentException("Invalid Param!");
		}

		return Arrays.stream(strs).noneMatch(StringUtils::isEmpty);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
