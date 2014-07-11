package com.uiguard.nativedriverfactory;

import org.openqa.selenium.WebDriver;

import com.uiguard.exception.UiGuardException;

public interface INativeDriverFactory {
	
	WebDriver getNativeWebDriver() throws UiGuardException;
	
}
