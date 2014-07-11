package com.uiguard.nativedriverfactory.imp.remote;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.uiguard.exception.UiGuardException;
import com.uiguard.nativedriverfactory.INativeDriverFactory;

public class RemoteFirefoxDriverFactory extends RemoteDriverFactory implements INativeDriverFactory {

	@Override
	public RemoteWebDriver getNativeWebDriver() throws UiGuardException {
		return getNativeWebDriverByCapabilities(DesiredCapabilities.firefox());
	}

}
