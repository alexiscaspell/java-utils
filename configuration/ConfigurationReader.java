package io.blacktoast.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationReader {

	private static final Logger LOGGER = Logger.getLogger(ConfigurationReader.class.getName());

	private static final String PROJECT_NAME = Vars.PROJECT_NAME;

	private final String propFileName = PROJECT_NAME + ".properties";

	public void loadConfiguration() throws Exception {

		try {
			LOGGER.log(Level.INFO, "Start reading configuration for " + PROJECT_NAME);

			for (String key : Vars.getConfigurationKeys()) {

				ConfigurationEntry ce = getConfigurationEntry(key);
				ConfigurationVars.addPropertyWithSecondaryValue(key, ce.getParameterConfiguration(),
						ce.getFileConfiguration());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Failed reading configuration");
			throw e;
		}
	}

	private ConfigurationEntry getConfigurationEntry(String propertyName) {
		ConfigurationEntry result = null;
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			}
			String parameterConfiguration = System.getProperty(propertyName);
			String fileConfiguration = prop.getProperty(propertyName);
			result = new ConfigurationEntry(parameterConfiguration, fileConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
