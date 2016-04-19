package it.clustering.kmedoids;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CustomPropertiesReader {
	private static final String BUNDLE_NAME = "it.clustering.kmedoids.KMedoids"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private CustomPropertiesReader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
