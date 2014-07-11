package com.uiguard.nativedriverfactory.imp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.uiguard.nativedriverfactory.INativeDriverFactory;
import com.uiguard.utils.setting.UiGuardSettings;

public class InternetExplorerDriverFactory implements INativeDriverFactory {

	@Override
	public WebDriver getNativeWebDriver() {
		System.setProperty("webdriver.ie.driver", UiGuardSettings.IEDriverPath);
		return new InternetExplorerDriver();
	}

}
