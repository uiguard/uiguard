
package com.uiguard;

import java.io.FileInputStream;
import java.util.Properties;

public class UiGuardSettings {
	
	private static Properties prop = getProperties();
	
	public static String AssistPath = prop.getProperty("AssistPath", "assist");
	
	public static String getProperty(String Property) {
		return prop.getProperty(Property);
	}
	
	private static Properties getProperties() {
		Properties prop = new Properties();
		try {
			FileInputStream file = new FileInputStream("env.properties");
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
}