/*
 * Copyright (c) 2012-2013 NetEase, Inc. and other contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.uiguard.utils.setting;

import java.io.FileInputStream;
import java.util.Properties;

public class UiGuardSettings {

	private static Properties prop = getProperties();
	
	public static String AssistPath = prop.getProperty("AssistPath", "assist");
	
	//TODO
	public static final String NativeDriverFactory = prop.getProperty("NativeDriverFactory", "com.uiguard.nativedriverfactory.imp.InternetExplorerDriverFactory");
	
	public static final String UiGuardLoggerClassName = prop.getProperty("UiGuardLoggerClassName", "com.uiguard.logger.imp.UiGuardLogger");
	
	
	
	public static boolean OmitErrorContinueRun = getBoolPropertyValue(prop, "OmitErrorContinueRun", "false");
	
	public static String HubIpAddr = prop.getProperty("HubIpAddr", "http://localhost:4444");
	
	public static String KillProcesses = prop.getProperty("KillProcesses", "");

	public static String ChromeDriverPath = prop.getProperty("ChromeDriverPath", "res/chromedriver_for_win.exe");
	
	public static String IEDriverPath = prop.getProperty("IEDriverPath", "res/iedriver_32.exe");

	public static String FirefoxPath = prop.getProperty("FirefoxPath", "");
	
	public static int StepInterval = Integer.parseInt(prop.getProperty("StepInterval", "500"));

	public static int Timeout = Integer.parseInt(prop.getProperty("Timeout", "30000"));
	
	public static String WebUrl = prop.getProperty("WebUrl", "http://www.google.com");
	
	public static String PngPath = prop.getProperty("PngPath", "screenshot");
	
	public static boolean MaxsizeWindowWhenTest = getBoolPropertyValue(prop, "MaxsizeWindowWhenTest","TRUE");

	public static boolean QuitAfterMethodDone = getBoolPropertyValue(prop, "QuitAfterMethodDone","TRUE");
	
	public static String Language = prop.getProperty("Language", "");//TODO
	
	public static String LogPath = prop.getProperty("LogPath", "log");
		
	public static int ChromeToolHeight = Integer.parseInt(prop.getProperty("ChromeToolHeight", "63"));
	
	public static int FireFoxToolHeight = Integer.parseInt(prop.getProperty("FireFoxToolHeight", "63"));
	
	private static boolean IsIECore = getBoolPropertyValue(prop, "IsIECore","TRUE");
	
	private static boolean IsChromeCore = getBoolPropertyValue(prop, "IsChromeCore","TRUE");
	
	private static boolean IsFirefoxCore = getBoolPropertyValue(prop, "IsFirefoxCore","TRUE");
	
	public static boolean isIECore(){
		return IsIECore;
	}
	
	public static boolean isChromeCore(){
		return IsChromeCore;
	}
	
	public static boolean isFirefoxCore(){
		return IsFirefoxCore;
	}

	public static String getProperty(String Property) {
		return prop.getProperty(Property);
	}
	
	private static boolean getBoolPropertyValue(final Properties prop, final String propertyKey, final String defaultValue){
		String propertyValue = prop.getProperty(propertyKey, defaultValue).toUpperCase();
		return "TRUE".equals(propertyValue) || "1".equals(propertyValue);
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