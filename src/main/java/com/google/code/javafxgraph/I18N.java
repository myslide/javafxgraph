/*
 * (C) Copyright 2016 mysli.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package com.google.code.javafxgraph;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class supports the access to internationalized properties.
 * 
 * @author mysli
 * @version 20161021
 */
public class I18N {
	/**
	 * Points to the file 'fxgraph.properties' as default. In case of given
	 * locale xx, a name like 'fxgraph_xx.properties' is expected there.
	 */
	private final String DEFAULT_PROPERTIES = "com.google.code.javafxgraph.fxgraph";

	private ResourceBundle bdl;
	private static final I18N INSTANCE = new I18N("");

	public static I18N getDefaultInstance() {
		return INSTANCE;
	}

	I18N(String locale) {
		bdl = getLabelBundle(locale);
	}

	public String getString(String key) {
		return getString(key, "", new Object[] {});
	}

	public String getString(String key, String dflt) {
		return getString(key, dflt, new Object[] {});
	}

	/**
	 * Use it to retrieve a String (for dialogs, labels, etc.)
	 * 
	 * @param key
	 *            the key for the requested label as it is in the resource
	 *            bundle
	 * @param dflt
	 *            this string is returned if anything fails
	 * @param inserts
	 *            this values will be inserted into place holders like "I am
	 *            {0}, age {1}."
	 * @return the string as found in the properties file to the given key or
	 *         the default string.
	 */
	public String getString(String key, String dflt, Object... inserts) {
		String result;
		if (bdl == null)
			return dflt;
		try {
			result = (inserts == null || inserts.length == 0) ? bdl.getString(key)
					: MessageFormat.format(bdl.getString(key), inserts);
		} catch (MissingResourceException e) {
			result = dflt;
		}
		return result;
	}

	private ResourceBundle getLabelBundle(String countryCode) {
		try {
			return ResourceBundle.getBundle(DEFAULT_PROPERTIES, new Locale(countryCode));
		} catch (NullPointerException exNP) {
			System.err.println("ERROR : No resources available for " + DEFAULT_PROPERTIES + " and " + countryCode);
		} catch (MissingResourceException exMRB) {
			System.err.println("ERROR : No file " + DEFAULT_PROPERTIES + (countryCode.isEmpty() ? "" : "_")
					+ countryCode + ".properties" + " found! \n" + exMRB.getMessage());
		}
		return null;
	}
}
