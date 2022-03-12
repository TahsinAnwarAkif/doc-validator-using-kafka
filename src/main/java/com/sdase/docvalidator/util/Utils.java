package com.sdase.docvalidator.util;

import java.util.UUID;

/**
 * @author akif
 * @since 3/11/22
 */
public class Utils {

	public static String getUniqueId() {
		return UUID.randomUUID().toString();
	}
}
