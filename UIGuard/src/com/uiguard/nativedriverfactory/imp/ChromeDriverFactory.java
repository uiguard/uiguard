package com.uiguard.nativedriverfactory.imp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.uiguard.nativedriverfactory.INativeDriverFactory;
import com.uiguard.utils.setting.UiGuardSettings;

public class ChromeDriverFactory implements INativeDriverFactory {

	@Override
	public WebDriver getNativeWebDriver() {
		System.setProperty("webdriver.chrome.driver", UiGuardSettings.ChromeDriverPath);
		return new ChromeDriver();
	}

}
