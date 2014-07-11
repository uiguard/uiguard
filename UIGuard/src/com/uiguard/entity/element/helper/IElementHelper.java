package com.uiguard.entity.element.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.uiguard.entity.IUGHelper;
import com.uiguard.entity.element.ElementLocatorType;
import com.uiguard.exception.UiGuardException;

public interface IElementHelper extends IUGHelper {
	
	ElementLocatorType getLocatorType();

	String getLocatorValue();
	
	String getIframePath();
	
	IElementHelper setIframePath(String framePath);
	
	public WebElement findWebElementBeforeAction() throws UiGuardException;

	boolean isElementPresent(final WebElement fatherElement, final By by);

	IElementHelper scrollIntoView() throws UiGuardException;

	By getBy(ElementLocatorType locType, String locValue) throws UiGuardException;

	String getElementJsString() throws UiGuardException;

	WebElement getWebElementByExecuteScript(String jsStr, Object... args);

	WebElement getWebElementUsingBy(WebElement fatherElement, By by);

	Boolean isPresent() throws UiGuardException;

}
