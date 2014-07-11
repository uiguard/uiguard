package com.uiguard.nativedriverfactory.imp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uiguard.nativedriverfactory.INativeDriverFactory;
import com.uiguard.utils.setting.UiGuardSettings;

public class FirefoxDriverFactory implements INativeDriverFactory {

	@Override
	public WebDriver getNativeWebDriver() {
		System.setProperty("webdriver.firefox.bin", UiGuardSettings.FirefoxPath);
		return new FirefoxDriver();
	}

}
