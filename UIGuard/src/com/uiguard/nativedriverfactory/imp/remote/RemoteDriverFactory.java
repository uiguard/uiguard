package com.uiguard.nativedriverfactory.imp.remote;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.uiguard.exception.UiGuardException;
import com.uiguard.nativedriverfactory.INativeDriverFactory;
import com.uiguard.utils.setting.UiGuardSettings;

abstract class RemoteDriverFactory implements INativeDriverFactory{
	
	private static final String RemoteURLPath;
	
	static {
		RemoteURLPath = UiGuardSettings.HubIpAddr + "/wd/hub";
	}
	
	protected URL getRemoteURL() throws UiGuardException{
		try {
			return new URL(RemoteURLPath);
		} catch (MalformedURLException e) {
			throw new UiGuardException("[Error][Create URL]" + RemoteURLPath, e);
		}
	}
	
	protected RemoteWebDriver getNativeWebDriverByCapabilities(DesiredCapabilities capabilities) 
		throws UiGuardException {		
		return new RemoteWebDriver(getRemoteURL(), capabilities);		
	}
	
}
