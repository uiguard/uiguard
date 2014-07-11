package com.uiguard.nativedriverfactory;

import org.openqa.selenium.WebDriver;

import com.uiguard.exception.UiGuardException;

public class NativeDriverFactoryControler {
	
	public static WebDriver getNativeWebDriver(final String factoryClassName) throws UiGuardException {
		Class<?> factoryClass;
		try {
			factoryClass = Class.forName(factoryClassName);
		} catch (ClassNotFoundException e) {
			throw new UiGuardException("[Cannot find native driver factory]" + factoryClassName, e);
		}
		if(INativeDriverFactory.class.isAssignableFrom(factoryClass)){
			try {
				return ((INativeDriverFactory)factoryClass.newInstance()).getNativeWebDriver();
			} catch (InstantiationException e) {
				throw new UiGuardException("[Native driver create instance error][InstantiationException]", e);
			} catch (IllegalAccessException e) {
				throw new UiGuardException("[Native driver create instance error][IllegalAccessException]", e);
			}
		}
		throw new UiGuardException("[Class is not implements INativeDriverFactory]" + factoryClass.getName());
		
	}
	
}
