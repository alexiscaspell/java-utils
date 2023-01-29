package io.blacktoast.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigurationVars {

	private static final Logger LOG = Logger.getLogger(ConfigurationVars.class.getName());
	private static ConfigurationVars INSTANCE = new ConfigurationVars();

	private Map<String, String> propertiesMap;

	private ConfigurationVars() {
		propertiesMap = new HashMap<String, String>();
	}

	private static ConfigurationVars getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConfigurationVars();
		}
		return INSTANCE;
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public static void addProperty(String key, String value) {
		getInstance().getPropertiesMap().put(key, value);
	}

	public static <T> T get(String key, Class<T> typeProperty) {

		String value = getInstance().getPropertiesMap().get(key);

		try {
			return typeProperty.getConstructor(String.class).newInstance(value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String get(String key) {
		return getInstance().getPropertiesMap().get(key);
	}

	public static void addPropertyWithSecondaryValue(String key, String primaryValue, String secondaryValue) {

		if (primaryValue == null) {
			LOG.warning("Error in " + key + "-> using value from file");
			addProperty(key, secondaryValue);
			return;
		}
		addProperty(key, primaryValue);
	}

}
